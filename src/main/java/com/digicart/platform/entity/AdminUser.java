package com.digicart.platform.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA entity mapped in this service schema (Admin User).
 */
@Entity
@Table(name = "admin_users", schema = "platform_svc")
@EntityListeners(AuditingEntityListener.class)
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AdminStatus status = AdminStatus.ACTIVE;

    @Column(name = "subscription_id")
    private String subscriptionId;

    @Column(name = "renews_at")
    private Instant renewsAt;

    @Column(name = "available_days", nullable = false)
    private Integer availableDays = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Creates a new {@code AdminUser}.
     */
    public AdminUser() {}
    /**
     * Returns id.
     * @return the string
     */
    public String getId() { return id; }
    /**
     * Sets id.
     *
     * @param id resource identifier
     */
    public void setId(String id) { this.id = id; }
    /**
     * Returns email.
     * @return the string
     */
    public String getEmail() { return email; }
    /**
     * Sets email.
     *
     * @param email email address
     */
    public void setEmail(String email) { this.email = email; }
    /**
     * Returns status.
     * @return the admin status
     */
    public AdminStatus getStatus() { return status; }
    /**
     * Sets status.
     *
     * @param status status
     */
    public void setStatus(AdminStatus status) { this.status = status; }
    /**
     * Returns subscription id.
     * @return the string
     */
    public String getSubscriptionId() { return subscriptionId; }
    /**
     * Sets subscription id.
     *
     * @param subscriptionId subscription id
     */
    public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }
    /**
     * Returns renews at.
     * @return the instant
     */
    public Instant getRenewsAt() { return renewsAt; }
    /**
     * Sets renews at.
     *
     * @param renewsAt renews at
     */
    public void setRenewsAt(Instant renewsAt) { this.renewsAt = renewsAt; }
    /**
     * Returns available days.
     * @return the integer
     */
    public Integer getAvailableDays() { return availableDays; }
    /**
     * Sets available days.
     *
     * @param availableDays available days
     */
    public void setAvailableDays(Integer availableDays) { this.availableDays = availableDays; }
    /**
     * Returns created at.
     * @return the instant
     */
    public Instant getCreatedAt() { return createdAt; }
    /**
     * Sets created at.
     *
     * @param createdAt created at
     */
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
