package com.digicart.platform.dto;

import com.digicart.platform.entity.AdminStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class AdminUserDto {

    public static class CreateRequest {
        @NotBlank @Email
        private String email;
        private AdminStatus status = AdminStatus.ACTIVE;
        private String subscriptionId;
        private Instant renewsAt;
        private Integer availableDays = 0;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public AdminStatus getStatus() { return status; }
        public void setStatus(AdminStatus status) { this.status = status; }
        public String getSubscriptionId() { return subscriptionId; }
        public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }
        public Instant getRenewsAt() { return renewsAt; }
        public void setRenewsAt(Instant renewsAt) { this.renewsAt = renewsAt; }
        public Integer getAvailableDays() { return availableDays; }
        public void setAvailableDays(Integer availableDays) { this.availableDays = availableDays; }
    }

    public static class UpdateRequest {
        private AdminStatus status;
        private String subscriptionId;
        private Instant renewsAt;
        private Integer availableDays;

        public AdminStatus getStatus() { return status; }
        public void setStatus(AdminStatus status) { this.status = status; }
        public String getSubscriptionId() { return subscriptionId; }
        public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }
        public Instant getRenewsAt() { return renewsAt; }
        public void setRenewsAt(Instant renewsAt) { this.renewsAt = renewsAt; }
        public Integer getAvailableDays() { return availableDays; }
        public void setAvailableDays(Integer availableDays) { this.availableDays = availableDays; }
    }
}
