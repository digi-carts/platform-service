package com.digicart.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", columnDefinition = "jsonb", nullable = false)
    private String data = "{}";

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public PlatformConfig() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
