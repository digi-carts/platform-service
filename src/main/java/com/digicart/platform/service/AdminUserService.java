package com.digicart.platform.service;

import com.digicart.platform.dto.AdminUserDto;
import com.digicart.platform.entity.AdminUser;
import com.digicart.platform.exception.EntityNotFoundException;
import com.digicart.platform.repository.AdminUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementing admin user use cases for <em>platform-service</em>.
 */
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
}
