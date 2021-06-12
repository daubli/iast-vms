package de.vms.settings.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.vms.settings.model.Settings;
import de.vms.settings.persistence.SettingsRepository;

@Service
public class SettingsService {

    private SettingsRepository settingsRepository;

    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Optional<Settings> getSetting() {
        List<Settings> allSettings = settingsRepository.findAll();
        //There should be not more than one
        assert allSettings.size() < 2;
        if (allSettings != null && allSettings.size() == 1) {
            return Optional.of(allSettings.get(0));
        } else {
            //try to get 
            return Optional.empty();
        }
    }

    public Settings createEmptySettings() {
        Settings settings = new Settings();
        String schema = envVar("HTTPS", "");
        String domain = envVar("DOMAIN", "");
        if (!schema.isEmpty() && !domain.isEmpty()) {
            String url = schema.equals("true") ? ("https://" + domain) : ("http://" + domain);
            settings.setInstanceURL(url);
        }
        return settingsRepository.save(settings);
    }

    public Settings updateSetting(Settings settings) {
        return settingsRepository.save(settings);
    }

    @VisibleForTesting
    String envVar(String varName, String defaultValue) {
        return System.getenv().getOrDefault(varName, defaultValue);
    }
}
