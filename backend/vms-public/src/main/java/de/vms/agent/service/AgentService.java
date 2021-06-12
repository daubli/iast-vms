package de.vms.agent.service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.el.PropertyNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.IOUtils;
import com.google.common.io.Files;

import de.vms.agent.model.Agent;
import de.vms.agent.persistence.AgentRepository;
import de.vms.settings.model.Settings;
import de.vms.settings.persistence.SettingsRepository;
import de.vms.settings.service.SettingsService;
import de.vms.vulnerability.persistence.VulnerabilityRepository;

@Service
public class AgentService {

    private AgentRepository agentRepository;

    private SettingsRepository settingsRepository;

    private VulnerabilityRepository vulnerabilityRepository;

    private SettingsService settingsService;

    private EntityManager em;

    public AgentService(AgentRepository agentRepository, SettingsRepository settingsRepository,
            SettingsService settingsService, EntityManager em) {
        this.agentRepository = agentRepository;
        this.settingsRepository = settingsRepository;
        this.settingsService = settingsService;
        this.em = em;
    }

    public Optional<Agent> getAgentById(UUID agentId) {
        return this.agentRepository.findById(agentId);
    }

    public Agent getAgentByIdentifier(String identifier) {
        return agentRepository.findAgentByIdentifier(identifier);
    }

    public List<Agent> getAgents() {
        return this.agentRepository.findAll();
    }

    public Agent createAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    public void updateLastSeen(UUID identifier) {
        Agent agent = agentRepository.findAgentByIdentifier(identifier.toString());

        if (agent != null) {
            agent.setLastSeen(LocalDateTime.now(ZoneOffset.UTC));
            agentRepository.save(agent);
        }
    }

    @Transactional
    public void deleteAgent(UUID agentId) {
        //this is a rather complicated operation - so it is implemented as native query

        Query incidentIdsReleatedToAgentQuery =
                em.createNativeQuery("SELECT CAST(ai.incident_id as varchar) incident_id FROM agent_incident ai "
                        + "WHERE ai.agent_id = :agentId");
        incidentIdsReleatedToAgentQuery.setParameter("agentId", agentId);
        List incidentIds = incidentIdsReleatedToAgentQuery.getResultList();

        Query relevantVulnerabilitiesQuery = em.createNativeQuery("SELECT CAST(v.id as varchar) id "
                + "FROM vulnerability v JOIN incident i on i.vulnerability_id = v.id "
                + "WHERE CAST(i.id as varchar) IN :ids");
        relevantVulnerabilitiesQuery.setParameter("ids", incidentIds);
        List relevantVulnerabilities = relevantVulnerabilitiesQuery.getResultList();

        Query deleteAgentIncidentRelationQuery = em.createNativeQuery("DELETE FROM agent_incident ai "
                + "WHERE ai.agent_id = ?");
        deleteAgentIncidentRelationQuery.setParameter(1, agentId);
        deleteAgentIncidentRelationQuery.executeUpdate();

        Query incidentDeleteQuery = em.createNativeQuery("DELETE FROM incident WHERE CAST(incident.id as varchar) IN "
                + ":incidentIds");
        incidentDeleteQuery.setParameter("incidentIds", incidentIds);
        incidentDeleteQuery.executeUpdate();

        Query vulnerabilityDeleteQuery = em.createNativeQuery("DELETE FROM vulnerability v "
                + "WHERE CAST(v.id as varchar) IN :vids");
        vulnerabilityDeleteQuery.setParameter("vids", relevantVulnerabilities);
        vulnerabilityDeleteQuery.executeUpdate();

        agentRepository.deleteById(agentId);
    }

    public Long getNumberOfAllAgents() {
        return agentRepository.count();
    }

    public Long getNumberOfActiveAgents() {
        return agentRepository.countActiveAgents(LocalDateTime.now(ZoneOffset.UTC).minusMinutes(5));
    }

    public Optional<Resource> generateAgentJar(UUID agentId) {
        Optional<Agent> agentOptional = this.getAgentById(agentId);
        return agentOptional.map(agent -> {
            try {
                File newConfigPropertiesFile = getPropertiesFileForAgentOrNull(agent);
                File agentZip = new File("temp.zip");
                File newTempZip = new File(getAgentFileName(agent.getName()));

                InputStream agentInputStream = this.getClass().getClassLoader().getResourceAsStream("java-agent.jar");

                byte[] buffer = IOUtils.toByteArray(agentInputStream);
                Files.write(buffer, agentZip);

                ZipUtil.replaceEntry(agentZip,
                        "config.properties", newConfigPropertiesFile,
                        newTempZip);
                agentZip.delete();
                newConfigPropertiesFile.delete();
                return new FileSystemResource(newTempZip);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public InputStream getBasePatchResource() {
        InputStream basePatchStream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream("base-patch.jar");
        return basePatchStream;
    }

    private File getPropertiesFileForAgentOrNull(Agent agent) throws Exception {
        String identifier = agent.getIdentifier();

        try (OutputStream output = new FileOutputStream("config-" + identifier + ".properties")) {
            Properties prop = new Properties();
            prop.put("agent-key", identifier);
            Settings settingsOrNull = settingsRepository.findTopByIdNotNull();
            String instanceURLOrNull;
            if (settingsOrNull == null) {
                instanceURLOrNull = settingsService.createEmptySettings().getInstanceURL();
            } else {
                instanceURLOrNull = settingsOrNull.getInstanceURL();
            }

            if (instanceURLOrNull != null) {
                prop.put("vms-url", instanceURLOrNull);
            } else {
                throw new PropertyNotFoundException("Instance is probably not configured correctly.");
            }

            prop.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new File("config-" + identifier + ".properties");
    }

    private String getAgentFileName(String agentName) {
        return agentName.replaceAll(" ", "").toLowerCase() + "-agent.jar";
    }
}
