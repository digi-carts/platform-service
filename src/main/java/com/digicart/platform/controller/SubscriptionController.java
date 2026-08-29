package com.digicart.platform.controller;

import com.digicart.platform.dto.SubscriptionDto;
import com.digicart.platform.entity.Subscription;
import com.digicart.platform.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller exposing subscription HTTP APIs for <em>platform-service</em>.
 */
@RestController
@RequestMapping("/api/platform/subscriptions")
public class SubscriptionController {

    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> findAll(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return Map.of("subscriptions", service.findAll());
    }

    @GetMapping("/{id}")
    public Subscription findById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Subscription> create(
            @Valid @RequestBody SubscriptionDto.CreateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PatchMapping("/{id}")
    public Subscription update(
            @PathVariable String id,
            @RequestBody SubscriptionDto.UpdateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
