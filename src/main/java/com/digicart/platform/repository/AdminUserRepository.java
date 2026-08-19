package com.digicart.platform.repository;

import com.digicart.platform.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for admin user  persistence.
 */
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
    /**
     * Finds by email.
     *
     * @param email email address
     * @return the value if present
     */
    Optional<AdminUser> findByEmail(String email);
}
