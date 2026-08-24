package com.digicart.platform.controller;

import com.digicart.platform.service.ServiceStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/platform/services")
public class ServiceStatusController {

    private final ServiceStatusService service;

    public ServiceStatusController(ServiceStatusService service) {
        this.service = service;
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        try {
            return ResponseEntity.ok(service.getStatus());
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(503).body(Map.of("error", e.getMessage()));
        }
    }
}
