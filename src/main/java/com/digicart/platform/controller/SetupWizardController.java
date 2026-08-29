package com.digicart.platform.controller;

import com.digicart.platform.service.PlatformConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/platform/setup-wizard")
public class SetupWizardController {

    private final PlatformConfigService service;

    public SetupWizardController(PlatformConfigService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> get() {
        return ResponseEntity.ok(Map.of("config", service.getWizardConfig()));
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> patch(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("config", service.saveWizardConfig(body)));
    }
}
