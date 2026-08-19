package com.digicart.platform.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA entity mapped in this service schema (Support Ticket).
 */
@Entity
@Table(name = "support_tickets", schema = "platform_svc")
@EntityListeners(AuditingEntityListener.class)
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "admin_email", nullable = false)
    private String adminEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TicketType type;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status = TicketStatus.OPEN;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Creates a new {@code SupportTicket}.
     */
    public SupportTicket() {}
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
     * Returns store id.
     * @return the string
     */
    public String getStoreId() { return storeId; }
    /**
     * Sets store id.
     *
     * @param storeId store (tenant) identifier
     */
    public void setStoreId(String storeId) { this.storeId = storeId; }
    /**
     * Returns admin email.
     * @return the string
     */
    public String getAdminEmail() { return adminEmail; }
    /**
     * Sets admin email.
     *
     * @param adminEmail admin email
     */
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
    /**
     * Returns type.
     * @return the ticket type
     */
    public TicketType getType() { return type; }
    /**
     * Sets type.
     *
     * @param type type
     */
    public void setType(TicketType type) { this.type = type; }
    /**
     * Returns description.
     * @return the string
     */
    public String getDescription() { return description; }
    /**
     * Sets description.
     *
     * @param description description
     */
    public void setDescription(String description) { this.description = description; }
    /**
     * Returns status.
     * @return the ticket status
     */
    public TicketStatus getStatus() { return status; }
    /**
     * Sets status.
     *
     * @param status status
     */
    public void setStatus(TicketStatus status) { this.status = status; }
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
    /**
     * Returns updated at.
     * @return the instant
     */
    public Instant getUpdatedAt() { return updatedAt; }
    /**
     * Sets updated at.
     *
     * @param updatedAt updated at
     */
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
