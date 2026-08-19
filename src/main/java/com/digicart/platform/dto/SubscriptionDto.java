package com.digicart.platform.dto;

import com.digicart.platform.entity.BillingPeriod;
import jakarta.validation.constraints.NotBlank;

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
        private String features = "{}";
        private String details;
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
        private String features;
        private String details;
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
    }
}
