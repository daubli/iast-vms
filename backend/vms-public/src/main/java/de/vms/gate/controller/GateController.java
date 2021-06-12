package de.vms.gate.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.vms.gate.model.GateCondition;
import de.vms.gate.model.converter.GateConditionElementToGateConditionConverter;
import de.vms.gate.model.converter.GateConditionToGateConditionElementConverter;
import de.vms.gate.model.dto.GateConditionElement;
import de.vms.gate.model.dto.GateData;
import de.vms.gate.model.dto.GateStatus;
import de.vms.gate.service.GateConditionService;
import de.vms.gate.service.GateStatusService;

@RestController
@RequestMapping("/api/gates")
public class GateController {

    private GateConditionService gateConditionService;

    private GateStatusService gateStatusService;

    private ModelMapper modelMapper;

    public GateController(GateConditionService gateConditionService, GateStatusService gateStatusService,
            ModelMapper modelMapper) {
        this.gateConditionService = gateConditionService;
        this.gateStatusService = gateStatusService;
        this.modelMapper = modelMapper;
        this.modelMapper.addConverter(new GateConditionElementToGateConditionConverter());
        this.modelMapper.addConverter(new GateConditionToGateConditionElementConverter());
    }

    @GetMapping("/")
    public ResponseEntity<GateData> getGateData() {
        GateData gateData = new GateData();
        List<GateConditionElement> gateConditionElements = gateConditionService.getAllGateConditions()
                .stream()
                .map(condition -> modelMapper.map(condition, GateConditionElement.class))
                .collect(
                        Collectors.toList());
        gateData.setConditions(gateConditionElements);
        return ResponseEntity.ok(gateData);
    }

    @PostMapping("/conditions")
    public ResponseEntity<GateConditionElement> createCondition(
            @RequestBody GateConditionElement gateConditionElement) {
        GateCondition condition =
                gateConditionService.createCondition(modelMapper.map(gateConditionElement, GateCondition.class));
        return ResponseEntity.ok(modelMapper.map(condition, GateConditionElement.class));
    }

    @PutMapping("/conditions")
    public ResponseEntity<GateConditionElement> updateCondition(
            @RequestBody GateConditionElement gateConditionElement) {
        GateCondition condition =
                gateConditionService.updateCondition(modelMapper.map(gateConditionElement, GateCondition.class));
        return ResponseEntity.ok(modelMapper.map(condition, GateConditionElement.class));
    }

    @DeleteMapping("/conditions/{conditionId}")
    public void deleteCondition(@PathVariable UUID conditionId) {
        gateConditionService.deleteCondition(conditionId);
    }

    @GetMapping("/status")
    public ResponseEntity<GateStatus> getGateStatus() {
        GateStatus gateStatus = new GateStatus();
        gateStatus.setGateHealth(gateStatusService.getGateHealth().getValue());
        return ResponseEntity.ok(gateStatus);
    }
}
