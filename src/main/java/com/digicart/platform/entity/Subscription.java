package com.digicart.platform.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * JPA entity mapped in this service schema (Subscription).
 */
@Entity
@Table(name = "subscriptions", schema = "platform_svc")
@EntityListeners(AuditingEntityListener.class)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "max_products", nullable = false)
    private Integer maxProducts = 50;

    @Column(name = "price", nullable = false)
    private Double price = 0.0;

    @Column(name = "currency", nullable = false)
    private String currency = "INR";

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_period", nullable = false)
    private BillingPeriod billingPeriod = BillingPeriod.MONTHLY;

    @Column(name = "custom_days")
    private Integer customDays;

    @Column(name = "features", columnDefinition = "jsonb", nullable = false)
    private String features = "{}";

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Creates a new {@code Subscription}.
     */
    public Subscription() {}
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
     * Returns name.
     * @return the string
     */
    public String getName() { return name; }
    /**
     * Sets name.
     *
     * @param name name
     */
    public void setName(String name) { this.name = name; }
    /**
     * Returns max products.
     * @return the integer
     */
    public Integer getMaxProducts() { return maxProducts; }
    /**
     * Sets max products.
     *
     * @param maxProducts max products
     */
    public void setMaxProducts(Integer maxProducts) { this.maxProducts = maxProducts; }
    /**
     * Returns price.
     * @return the double
     */
    public Double getPrice() { return price; }
    /**
     * Sets price.
     *
     * @param price price
     */
    public void setPrice(Double price) { this.price = price; }
    /**
     * Returns currency.
     * @return the string
     */
    public String getCurrency() { return currency; }
    /**
     * Sets currency.
     *
     * @param currency currency
     */
    public void setCurrency(String currency) { this.currency = currency; }
    /**
     * Returns billing period.
     * @return the billing period
     */
    public BillingPeriod getBillingPeriod() { return billingPeriod; }
    /**
     * Sets billing period.
     *
     * @param billingPeriod billing period
     */
    public void setBillingPeriod(BillingPeriod billingPeriod) { this.billingPeriod = billingPeriod; }
    /**
     * Returns custom days.
     * @return the integer
     */
    public Integer getCustomDays() { return customDays; }
    /**
     * Sets custom days.
     *
     * @param customDays custom days
     */
    public void setCustomDays(Integer customDays) { this.customDays = customDays; }
    /**
     * Returns features.
     * @return the string
     */
    public String getFeatures() { return features; }
    /**
     * Sets features.
     *
     * @param features features
     */
    public void setFeatures(String features) { this.features = features; }
    /**
     * Returns details.
     * @return the string
     */
    public String getDetails() { return details; }
    /**
     * Sets details.
     *
     * @param details details
     */
    public void setDetails(String details) { this.details = details; }
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
