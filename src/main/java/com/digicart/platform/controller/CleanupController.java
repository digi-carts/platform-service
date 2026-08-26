package com.digicart.platform.controller;

import com.digicart.platform.service.CleanupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/platform/cleanup")
public class CleanupController {

    private final CleanupService cleanupService;

    public CleanupController(CleanupService cleanupService) {
        this.cleanupService = cleanupService;
    }

    @GetMapping("/schema")
    public ResponseEntity<?> getSchema(
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"superadmin".equalsIgnoreCase(userRole)) {
            return ResponseEntity.status(403).body(Map.of("error", "Forbidden"));
        }
        try {
            return ResponseEntity.ok(cleanupService.getSchemaInfo());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
