package com.digicart.platform.controller;

import com.digicart.platform.entity.AdminUser;
import com.digicart.platform.entity.Subscription;
import com.digicart.platform.repository.AdminUserRepository;
import com.digicart.platform.repository.SubscriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Returns the calling merchant's subscription status for the dashboard.
 */
@RestController
@RequestMapping("/api/platform")
public class MerchantSubscriptionStatusController {

    private final AdminUserRepository adminUserRepository;
    private final SubscriptionRepository subscriptionRepository;

    public MerchantSubscriptionStatusController(AdminUserRepository adminUserRepository,
                                                SubscriptionRepository subscriptionRepository) {
        this.adminUserRepository = adminUserRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/subscription-status")
    public ResponseEntity<Map<String, Object>> subscriptionStatus(
            @RequestHeader(value = "X-User-Email", required = false) String email) {

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "X-User-Email header required"));
        }

        AdminUser admin = adminUserRepository.findByEmail(email).orElse(null);
        if (admin == null || admin.getSubscriptionId() == null) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("subscribed", false);
            result.put("availableDays", admin != null ? (admin.getAvailableDays() != null ? admin.getAvailableDays() : 0) : 0);
            result.put("expired", false);
            result.put("subscription", null);
            return ResponseEntity.ok(result);
        }

        int availableDays = admin.getAvailableDays() != null ? admin.getAvailableDays() : 0;
        boolean expired = availableDays <= 0;

        Subscription sub = subscriptionRepository.findById(admin.getSubscriptionId()).orElse(null);
        Map<String, Object> subMap = null;
        if (sub != null) {
            subMap = new LinkedHashMap<>();
            subMap.put("name", sub.getName());
            subMap.put("price", sub.getPrice());
            subMap.put("currency", sub.getCurrency());
            subMap.put("billingPeriod", sub.getBillingPeriod() != null ? sub.getBillingPeriod().name() : null);
            subMap.put("maxProducts", sub.getMaxProducts());
            subMap.put("features", sub.getFeatures() != null ? sub.getFeatures() : Map.of());
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("subscribed", true);
        result.put("availableDays", availableDays);
        result.put("expired", expired);
        result.put("subscription", subMap);
        return ResponseEntity.ok(result);
    }
}
