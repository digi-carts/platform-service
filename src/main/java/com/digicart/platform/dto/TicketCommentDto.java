package com.digicart.platform.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request/response DTO: Ticket Comment Dto.
 */
public class TicketCommentDto {

    /**
     * Request/response DTO: Create Request.
     */
    public static class CreateRequest {
        @NotBlank
        private String ticketId;
        @NotBlank
        private String authorRole;
        @NotBlank
        private String authorEmail;
        @NotBlank
        private String body;

        public String getTicketId() { return ticketId; }
        public void setTicketId(String ticketId) { this.ticketId = ticketId; }
        public String getAuthorRole() { return authorRole; }
        public void setAuthorRole(String authorRole) { this.authorRole = authorRole; }
        public String getAuthorEmail() { return authorEmail; }
        public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }
        public String getBody() { return body; }
        public void setBody(String body) { this.body = body; }
    }
}
