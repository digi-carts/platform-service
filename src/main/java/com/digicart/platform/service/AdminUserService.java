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

    /**
     * Creates a new {@code AdminUserService}.
     *
     * @param repository repository
     */
    public AdminUserService(AdminUserRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds all.
     * @return matching records
     */
    public List<AdminUser> findAll() {
        return repository.findAll();
    }

    /**
     * Finds by id.
     *
     * @param id resource identifier
     * @return the admin user
     */
    public AdminUser findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AdminUser not found: " + id));
    }

    /**
     * Finds by email.
     *
     * @param email email address
     * @return the admin user
     */
    public AdminUser findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("AdminUser not found with email: " + email));
    }

    /**
     * Creates a new record.
     *
     * @param req request payload
     * @return the admin user
     */
    public AdminUser create(AdminUserDto.CreateRequest req) {
        AdminUser user = new AdminUser();
        user.setEmail(req.getEmail());
        if (req.getStatus() != null) user.setStatus(req.getStatus());
        user.setSubscriptionId(req.getSubscriptionId());
        user.setRenewsAt(req.getRenewsAt());
        if (req.getAvailableDays() != null) user.setAvailableDays(req.getAvailableDays());
        return repository.save(user);
    }

    /**
     * Updates an existing record.
     *
     * @param id resource identifier
     * @param req request payload
     * @return the admin user
     */
    public AdminUser update(String id, AdminUserDto.UpdateRequest req) {
        AdminUser user = findById(id);
        if (req.getStatus() != null) user.setStatus(req.getStatus());
        if (req.getSubscriptionId() != null) user.setSubscriptionId(req.getSubscriptionId());
        if (req.getRenewsAt() != null) user.setRenewsAt(req.getRenewsAt());
        if (req.getAvailableDays() != null) user.setAvailableDays(req.getAvailableDays());
        return repository.save(user);
    }

    /**
     * Deletes the record.
     *
     * @param id resource identifier
     */
    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }
}
