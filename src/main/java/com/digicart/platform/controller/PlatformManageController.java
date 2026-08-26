package com.digicart.platform.controller;

import com.digicart.platform.entity.AdminUser;
import com.digicart.platform.entity.BillingPeriod;
import com.digicart.platform.entity.Subscription;
import com.digicart.platform.repository.AdminUserRepository;
import com.digicart.platform.repository.SubscriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller for platform merchant management — subscription activation and admin listing.
 */
@RestController
@RequestMapping("/api/platform/manage")
public class PlatformManageController {

    private final AdminUserRepository adminUserRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PlatformManageController(AdminUserRepository adminUserRepository,
                                    SubscriptionRepository subscriptionRepository) {
        this.adminUserRepository = adminUserRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping
    public ResponseEntity<?> listAdmins(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"superadmin".equalsIgnoreCase(userRole)) {
            return ResponseEntity.status(403).body(Map.of("error", "Forbidden"));
        }
        List<Map<String, Object>> admins = adminUserRepository.findAll().stream().map(a -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", a.getId());
            m.put("email", a.getEmail());
            m.put("availableDays", a.getAvailableDays());
            m.put("renewsAt", a.getRenewsAt());
            m.put("daysUntilExpiry", a.getAvailableDays());
            return m;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("admins", admins));
    }

    @GetMapping("/my-usage")
    public Map<String, Object> myUsage(
            @RequestHeader(value = "X-User-Email", required = false) String email,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        Map<String, Integer> usage = subscriptionRepository.findAll().stream()
                .collect(Collectors.toMap(Subscription::getId, Subscription::getTotalUses));
        return Map.of("usage", usage);
    }

    @PostMapping("/buy")
    public ResponseEntity<Map<String, Object>> buy(
            @RequestBody BuyRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        Subscription sub = subscriptionRepository.findById(request.subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + request.subscriptionId));

        int daysToAdd = resolveDays(sub);

        AdminUser admin = adminUserRepository.findByEmail(request.adminEmail)
                .orElseGet(() -> {
                    AdminUser a = new AdminUser();
                    a.setEmail(request.adminEmail);
                    return adminUserRepository.save(a);
                });

        int current = admin.getAvailableDays() != null ? Math.max(0, admin.getAvailableDays()) : 0;
        admin.setAvailableDays(current + daysToAdd);
        admin.setSubscriptionId(sub.getId());
        admin.setRenewsAt(Instant.now().plusSeconds((long) daysToAdd * 86400));
        adminUserRepository.save(admin);

        sub.setTotalUses(sub.getTotalUses() + 1);
        subscriptionRepository.save(sub);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "daysAdded", daysToAdd,
                "availableDays", admin.getAvailableDays()
        ));
    }

    private int resolveDays(Subscription sub) {
        if (sub.getBillingPeriod() == BillingPeriod.CUSTOM) {
            return sub.getCustomDays() != null ? sub.getCustomDays() : 30;
        }
        return switch (sub.getBillingPeriod()) {
            case MONTHLY -> 30;
            case QUARTERLY -> 90;
            case YEARLY -> 365;
            case UNLIMITED -> 36500;
            default -> 30;
        };
    }

    static class BuyRequest {
        public String subscriptionId;
        public String adminEmail;
        public String paymentMethod;
        public String paymentRef;
    }
}
