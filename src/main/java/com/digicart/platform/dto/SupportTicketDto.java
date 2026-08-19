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
        /**
         * Returns store id.
         * @return the string
         */
        public String getStoreId() { return storeId; }
        /**
         * Sets store id.
         *
         * @param storeId store (tenant) identifier
         */
        public void setStoreId(String storeId) { this.storeId = storeId; }
        /**
         * Returns admin email.
         * @return the string
         */
        public String getAdminEmail() { return adminEmail; }
        /**
         * Sets admin email.
         *
         * @param adminEmail admin email
         */
        public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
        /**
         * Returns type.
         * @return the ticket type
         */
        public TicketType getType() { return type; }
        /**
         * Sets type.
         *
         * @param type type
         */
        public void setType(TicketType type) { this.type = type; }
        /**
         * Returns description.
         * @return the string
         */
        public String getDescription() { return description; }
        /**
         * Sets description.
         *
         * @param description description
         */
        public void setDescription(String description) { this.description = description; }
    }

    /**
     * Request/response DTO: Update Request.
     */
    public static class UpdateRequest {
        private TicketStatus status;
        private String description;
        /**
         * Returns status.
         * @return the ticket status
         */
        public TicketStatus getStatus() { return status; }
        /**
         * Sets status.
         *
         * @param status status
         */
        public void setStatus(TicketStatus status) { this.status = status; }
        /**
         * Returns description.
         * @return the string
         */
        public String getDescription() { return description; }
        /**
         * Sets description.
         *
         * @param description description
         */
        public void setDescription(String description) { this.description = description; }
    }
}
