package de.vms.agent.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.vms.agent.model.Agent;
import de.vms.agent.model.dto.AgentDto;
import de.vms.agent.service.AgentService;

@RestController
@RequestMapping(path = "/api/agents")
public class AgentController {
    private AgentService agentService;

    private ModelMapper modelMapper;

    public AgentController(AgentService agentService, ModelMapper modelMapper) {
        this.agentService = agentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/")
    public ResponseEntity<Map<String, List<AgentDto>>> getAgents() {
        List<Agent> agents = this.agentService.getAgents();
        return ResponseEntity.ok(Map.of("agents",
                agents.stream().map(this::convertToDto).collect(Collectors.toList())));
    }

    @GetMapping("{agentId}")
    public ResponseEntity<AgentDto> getAgentById(@PathVariable UUID agentId) {
        return agentService.getAgentById(agentId)
                .map(agent -> ResponseEntity.ok().body(convertToDto(agent)))          //200 OK
                .orElseGet(() -> ResponseEntity.notFound().build());  //404 Not found
    }

    @GetMapping("{agentId}/download-agent")
    public void getAgentJarByAgentId(@PathVariable UUID agentId, HttpServletResponse response) {
        Optional<Resource> resourceOptional = agentService.generateAgentJar(agentId);

        resourceOptional.ifPresentOrElse(resource -> {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            response.setHeader("Content-disposition", "attachment; filename=" + resource.getFilename());

            OutputStream out = null;
            try {
                out = response.getOutputStream();
                FileInputStream in = new FileInputStream(resource.getFile());
                IOUtils.copy(in,out);

                out.close();
                in.close();
                resource.getFile().delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, () -> response.setStatus(HttpServletResponse.SC_NOT_FOUND));
    }

    @GetMapping("/download-patch")
    public void getJavaBasePatch(HttpServletResponse response) {
        OutputStream out = null;
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            response.setHeader("Content-disposition", "attachment; filename=base-patch.jar");

            out = response.getOutputStream();
            InputStream in = agentService.getBasePatchResource();
            IOUtils.copy(in, out);

            out.close();
            in.close();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<AgentDto> createAgent(@RequestBody Agent newAgent) {
        newAgent.setIdentifier(UUID.randomUUID().toString());
        AgentDto agentDto = convertToDto(agentService.createAgent(newAgent));
        return ResponseEntity.ok(agentDto);
    }

    @DeleteMapping("{agentId}")
    public void deleteAgent(@PathVariable UUID agentId) {
        agentService.deleteAgent(agentId);
    }

    private AgentDto convertToDto(Agent agent) {
        return modelMapper.map(agent, AgentDto.class);
    }
}
