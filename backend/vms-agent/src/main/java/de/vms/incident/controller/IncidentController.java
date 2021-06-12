package de.vms.incident.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.vms.incident.model.Incident;
import de.vms.incident.model.dto.IncidentDto;
import de.vms.incident.model.map.IncidentDtoToIncidentMap;
import de.vms.incident.service.IncidentService;
import de.vms.incident.service.IncidentDispatcherService;

@RestController
@RequestMapping(path = "/api/internal/incident")
public class IncidentController {

    IncidentDispatcherService incidentDispatcherService;

    ModelMapper modelMapper;

    public IncidentController(IncidentDispatcherService IncidentDispatcherService, ModelMapper modelMapper) {
        this.incidentDispatcherService = IncidentDispatcherService;
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(new IncidentDtoToIncidentMap());
    }

    @PostMapping("/")
    public ResponseEntity reportIncident(@RequestHeader("X-VMS-AGENT") String agentId,
            @RequestBody IncidentDto incidentDto) {
        Incident incidentEntity = convertToEntity(incidentDto);
        IncidentService incidentService = incidentDispatcherService.getIncidentService(incidentEntity.getIncidentType());
        boolean newVulnerability =
                incidentService.handleIncident(agentId, incidentEntity, incidentDto.getApplicationMainPackage());
        return (newVulnerability ? ResponseEntity.ok() : ResponseEntity.status(HttpStatus.NOT_MODIFIED)).build();
    }

    private Incident convertToEntity(IncidentDto incidentDto) {
        Incident incident = modelMapper.map(incidentDto, Incident.class);
        return incident;
    }
}
