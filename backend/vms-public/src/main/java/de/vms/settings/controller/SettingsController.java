package de.vms.settings.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.vms.settings.model.Settings;
import de.vms.settings.model.dto.SettingsDto;
import de.vms.settings.service.SettingsService;

@RestController
@RequestMapping(path = "/api/settings")
public class SettingsController {

    private SettingsService settingService;

    private ModelMapper modelMapper;

    public SettingsController(SettingsService settingService, ModelMapper modelMapper) {
        this.settingService = settingService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ResponseEntity<SettingsDto> getSettings() {
        SettingsDto settingsDto = settingService.getSetting()
                .map(settings -> convertToDto(settings))
                .orElseGet(() -> convertToDto(settingService.createEmptySettings()));
        return ResponseEntity.ok().body(settingsDto);
    }

    @PutMapping("/")
    public ResponseEntity<SettingsDto> updateSettings(@RequestBody SettingsDto newSettings) {
        Settings updatedSettings = settingService.updateSetting(convertToEntity(newSettings));
        return ResponseEntity.ok(convertToDto(updatedSettings));
    }

    private SettingsDto convertToDto(Settings settings) {
        return modelMapper.map(settings, SettingsDto.class);
    }

    private Settings convertToEntity(SettingsDto settingsDto) {
        return modelMapper.map(settingsDto, Settings.class);
    }
}
