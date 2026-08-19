package com.digicart.platform.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA entity mapped in this service schema (Ticket Comment).
 */
@Entity
@Table(name = "ticket_comments", schema = "platform_svc")
@EntityListeners(AuditingEntityListener.class)
public class TicketComment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "ticket_id", nullable = false)
    private String ticketId;

    @Column(name = "author_role", nullable = false)
    private String authorRole;

    @Column(name = "author_email", nullable = false)
    private String authorEmail;

    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Creates a new {@code TicketComment}.
     */
    public TicketComment() {}
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
     * Returns ticket id.
     * @return the string
     */
    public String getTicketId() { return ticketId; }
    /**
     * Sets ticket id.
     *
     * @param ticketId ticket id
     */
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }
    /**
     * Returns author role.
     * @return the string
     */
    public String getAuthorRole() { return authorRole; }
    /**
     * Sets author role.
     *
     * @param authorRole author role
     */
    public void setAuthorRole(String authorRole) { this.authorRole = authorRole; }
    /**
     * Returns author email.
     * @return the string
     */
    public String getAuthorEmail() { return authorEmail; }
    /**
     * Sets author email.
     *
     * @param authorEmail author email
     */
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }
    /**
     * Returns body.
     * @return the string
     */
    public String getBody() { return body; }
    /**
     * Sets body.
     *
     * @param body JSON request body
     */
    public void setBody(String body) { this.body = body; }
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
