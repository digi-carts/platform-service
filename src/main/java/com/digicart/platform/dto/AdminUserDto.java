package com.digicart.platform.dto;

import com.digicart.platform.entity.AdminStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

/**
 * Request/response DTO: Admin User Dto.
 */
public class AdminUserDto {

    /**
     * Request/response DTO: Create Request.
     */
    public static class CreateRequest {
        @NotBlank @Email
        private String email;
        private AdminStatus status = AdminStatus.ACTIVE;
        private String subscriptionId;
        private Instant renewsAt;
        private Integer availableDays = 0;
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
    }

    /**
     * Request/response DTO: Update Request.
     */
    public static class UpdateRequest {
        private AdminStatus status;
        private String subscriptionId;
        private Instant renewsAt;
        private Integer availableDays;
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
    }
}
