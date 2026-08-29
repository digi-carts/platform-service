package com.digicart.platform.service;

import com.digicart.platform.dto.AdminUserDto;
import com.digicart.platform.entity.AdminStatus;
import com.digicart.platform.entity.AdminUser;
import com.digicart.platform.exception.EntityNotFoundException;
import com.digicart.platform.repository.AdminUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class AdminUserService {

    private final AdminUserRepository repository;

    public AdminUserService(AdminUserRepository repository) {
        this.repository = repository;
    }

    public List<AdminUser> findAll() {
        return repository.findAll();
    }

    public AdminUser findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AdminUser not found: " + id));
    }

    public AdminUser findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("AdminUser not found with email: " + email));
    }

    public AdminUser create(AdminUserDto.CreateRequest req) {
        AdminUser user = new AdminUser();
        user.setEmail(req.getEmail());
        if (req.getStatus() != null) user.setStatus(req.getStatus());
        user.setSubscriptionId(req.getSubscriptionId());
        user.setRenewsAt(req.getRenewsAt());
        if (req.getAvailableDays() != null) user.setAvailableDays(req.getAvailableDays());
        return repository.save(user);
    }

    public AdminUser update(String id, AdminUserDto.UpdateRequest req) {
        AdminUser user = findById(id);
        if (req.getStatus() != null) user.setStatus(req.getStatus());
        if (req.getSubscriptionId() != null) user.setSubscriptionId(req.getSubscriptionId());
        if (req.getRenewsAt() != null) user.setRenewsAt(req.getRenewsAt());
        if (req.getAvailableDays() != null) user.setAvailableDays(req.getAvailableDays());
        return repository.save(user);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    @Transactional
    public AdminUser upsertByEmail(String email, Map<String, Object> body) {
        AdminUser user = repository.findByEmail(email).orElseGet(() -> {
            AdminUser u = new AdminUser();
            u.setEmail(email);
            return u;
        });
        if (body.containsKey("status")) {
            user.setStatus(AdminStatus.valueOf((String) body.get("status")));
        }
        if (body.containsKey("subscriptionId")) user.setSubscriptionId((String) body.get("subscriptionId"));
        return repository.save(user);
    }

    @Transactional
    public AdminUser updateStatus(String id, AdminStatus status) {
        AdminUser user = findById(id);
        user.setStatus(status);
        return repository.save(user);
    }

    @Transactional
    public AdminUser updateSubscription(String id, Map<String, Object> body) {
        AdminUser user = findById(id);
        if (body.containsKey("subscriptionId")) user.setSubscriptionId((String) body.get("subscriptionId"));
        if (body.containsKey("renewsAt")) {
            Object raw = body.get("renewsAt");
            if (raw instanceof String s) user.setRenewsAt(Instant.parse(s));
        }
        if (body.containsKey("availableDays")) {
            Object days = body.get("availableDays");
            if (days instanceof Number n) user.setAvailableDays(n.intValue());
        }
        return repository.save(user);
    }
}
