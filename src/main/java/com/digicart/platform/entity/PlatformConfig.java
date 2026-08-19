package com.digicart.platform.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

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

    public PlatformConfig() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
