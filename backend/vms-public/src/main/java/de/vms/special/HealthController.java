package de.vms.special;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/health")
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<Map> getHealth() {
        return ResponseEntity.ok(Map.of("live", true));
    }

}
