package com.digicart.platform.controller;

import com.digicart.platform.service.PlatformConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/ai")
    public ResponseEntity<Map<String, Object>> getAiConfig() {
        return ResponseEntity.ok(service.getAiConfig());
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> patch(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(service.patch(body));
    }

    @PatchMapping("/info-content")
    public ResponseEntity<Object> patchInfoContent(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(service.patchInfoContent(body));
    }

    @PostMapping("/cloudflare-test")
    public ResponseEntity<Map<String, Object>> testCloudflare() {
        return ResponseEntity.ok(service.testCloudflare());
    }

    @PostMapping("/cloudflare-dns")
    public ResponseEntity<Map<String, Object>> addCloudflareDns(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(service.addCloudflareDns(body));
    }

    @PostMapping("/firebase-authorized-domains")
    public ResponseEntity<Map<String, Object>> updateFirebaseAuthorizedDomains(@RequestBody Map<String, Object> body) {
        @SuppressWarnings("unchecked")
        List<String> domains = (List<String>) body.get("domains");
        return ResponseEntity.ok(service.updateFirebaseAuthorizedDomains(domains != null ? domains : List.of()));
    }

    @PostMapping("/ai-chat")
    public ResponseEntity<Map<String, Object>> aiChat(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.aiChat(body.get("message")));
    }
}
