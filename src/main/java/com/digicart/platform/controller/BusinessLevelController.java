package com.digicart.platform.controller;

import com.digicart.platform.service.PlatformConfigService;
import com.digicart.platform.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/platform/business-levels")
public class BusinessLevelController {

    private final PlatformConfigService configService;
    private final SubscriptionService subscriptionService;

    public BusinessLevelController(PlatformConfigService configService, SubscriptionService subscriptionService) {
        this.configService = configService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public Map<String, Object> getAll() {
        return Map.of("levels", configService.getBusinessLevels());
    }

    @PostMapping
    public Map<String, Object> add(@RequestBody Map<String, Object> body) {
        return Map.of("levels", configService.addBusinessLevel(body));
    }

    @PatchMapping("/{key}")
    public Map<String, Object> update(@PathVariable String key, @RequestBody Map<String, Object> body) {
        return Map.of("levels", configService.updateBusinessLevel(key, body));
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String key) {
        List<String> plans = subscriptionService.findNamesByLevel(key);
        if (!plans.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Business level is in use by plans",
                    "plans", plans));
        }
        try {
            configService.deleteBusinessLevel(key);
            return ResponseEntity.ok(Map.of("levels", configService.getBusinessLevels()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
