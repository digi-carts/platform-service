package com.digicart.platform.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA entity mapped in this service schema (Platform Config).
 */
@Entity
@Table(name = "platform_config", schema = "platform_svc")
@EntityListeners(AuditingEntityListener.class)
public class PlatformConfig {

    @Id
    @Column(name = "id", nullable = false)
    private String id = "singleton";

    @Column(name = "data", columnDefinition = "jsonb", nullable = false)
    private String data = "{}";

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Creates a new {@code PlatformConfig}.
     */
    public PlatformConfig() {}
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
     * Returns data.
     * @return the string
     */
    public String getData() { return data; }
    /**
     * Sets data.
     *
     * @param data data
     */
    public void setData(String data) { this.data = data; }
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
