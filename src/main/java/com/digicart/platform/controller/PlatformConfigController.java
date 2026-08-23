package com.digicart.platform.controller;

import com.digicart.platform.service.PlatformConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller exposing platform config HTTP APIs for <em>platform-service</em>.
 */
@RestController
@RequestMapping("/api/platform/platform-config")
public class PlatformConfigController {

    private final PlatformConfigService service;

    public PlatformConfigController(PlatformConfigService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> get() {
        return ResponseEntity.ok(service.getData());
    }

    @GetMapping("/admin-settings")
    public ResponseEntity<Map<String, Object>> getAdminSettings() {
        return ResponseEntity.ok(service.getAdminSettings());
    }

    @GetMapping("/info-content")
    public ResponseEntity<Object> getInfoContent() {
        return ResponseEntity.ok(service.getInfoContent());
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> patch(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(service.patch(body));
    }

    @PostMapping("/cloudflare-test")
    public ResponseEntity<Map<String, Object>> testCloudflare() {
        return ResponseEntity.ok(service.testCloudflare());
    }
}
