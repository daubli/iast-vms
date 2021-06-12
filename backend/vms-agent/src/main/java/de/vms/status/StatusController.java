package de.vms.status;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/agents")
public class StatusController {
    @PutMapping("/{identifier}/health-check")
    public ResponseEntity updateHealthOfAgent(@PathVariable UUID identifier) {
        return ResponseEntity.noContent().build();
    }

}
