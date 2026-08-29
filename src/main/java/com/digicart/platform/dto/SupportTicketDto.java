package com.digicart.platform.dto;

import com.digicart.platform.entity.TicketStatus;
import com.digicart.platform.entity.TicketType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request/response DTO: Support Ticket Dto.
 */
public class SupportTicketDto {

    /**
     * Request/response DTO: Create Request.
     */
    public static class CreateRequest {
        @NotBlank
        private String storeId;
        @NotBlank
        private String adminEmail;
        @NotNull
        private TicketType type;
        @NotBlank
        private String description;

        public String getStoreId() { return storeId; }
        public void setStoreId(String storeId) { this.storeId = storeId; }
        public String getAdminEmail() { return adminEmail; }
        public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
        public TicketType getType() { return type; }
        public void setType(TicketType type) { this.type = type; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    /**
     * Request/response DTO: Update Request.
     */
    public static class UpdateRequest {
        private TicketStatus status;
        private String description;

        public TicketStatus getStatus() { return status; }
        public void setStatus(TicketStatus status) { this.status = status; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
