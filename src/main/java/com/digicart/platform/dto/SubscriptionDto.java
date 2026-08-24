package com.digicart.platform.dto;

import com.digicart.platform.entity.BillingPeriod;
import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;
import java.util.Map;

/**
 * Request/response DTO: Subscription Dto.
 */
public class SubscriptionDto {

    /**
     * Request/response DTO: Create Request.
     */
    public static class CreateRequest {
        @NotBlank
        private String name;
        private Integer maxProducts = 50;
        private Double price = 0.0;
        private String currency = "INR";
        private BillingPeriod billingPeriod = BillingPeriod.MONTHLY;
        private Integer customDays;
        private Map<String, Object> features = new HashMap<>();
        private String details;
        private String level;
        private Integer maxUses;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getMaxProducts() { return maxProducts; }
        public void setMaxProducts(Integer maxProducts) { this.maxProducts = maxProducts; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        public BillingPeriod getBillingPeriod() { return billingPeriod; }
        public void setBillingPeriod(BillingPeriod billingPeriod) { this.billingPeriod = billingPeriod; }
        public Integer getCustomDays() { return customDays; }
        public void setCustomDays(Integer customDays) { this.customDays = customDays; }
        public Map<String, Object> getFeatures() { return features; }
        public void setFeatures(Map<String, Object> features) { this.features = features; }
        public String getDetails() { return details; }
        public void setDetails(String details) { this.details = details; }
        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }
        public Integer getMaxUses() { return maxUses; }
        public void setMaxUses(Integer maxUses) { this.maxUses = maxUses; }
    }

    /**
     * Request/response DTO: Update Request.
     */
    public static class UpdateRequest {
        private String name;
        private Integer maxProducts;
        private Double price;
        private String currency;
        private BillingPeriod billingPeriod;
        private Integer customDays;
        private Map<String, Object> features;
        private String details;
        private String level;
        private Integer maxUses;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getMaxProducts() { return maxProducts; }
        public void setMaxProducts(Integer maxProducts) { this.maxProducts = maxProducts; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        public BillingPeriod getBillingPeriod() { return billingPeriod; }
        public void setBillingPeriod(BillingPeriod billingPeriod) { this.billingPeriod = billingPeriod; }
        public Integer getCustomDays() { return customDays; }
        public void setCustomDays(Integer customDays) { this.customDays = customDays; }
        public Map<String, Object> getFeatures() { return features; }
        public void setFeatures(Map<String, Object> features) { this.features = features; }
        public String getDetails() { return details; }
        public void setDetails(String details) { this.details = details; }
        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }
        public Integer getMaxUses() { return maxUses; }
        public void setMaxUses(Integer maxUses) { this.maxUses = maxUses; }
    }
}
