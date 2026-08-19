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
        /**
         * Returns ticket id.
         * @return the string
         */
        public String getTicketId() { return ticketId; }
        /**
         * Sets ticket id.
         *
         * @param ticketId ticket id
         */
        public void setTicketId(String ticketId) { this.ticketId = ticketId; }
        /**
         * Returns author role.
         * @return the string
         */
        public String getAuthorRole() { return authorRole; }
        /**
         * Sets author role.
         *
         * @param authorRole author role
         */
        public void setAuthorRole(String authorRole) { this.authorRole = authorRole; }
        /**
         * Returns author email.
         * @return the string
         */
        public String getAuthorEmail() { return authorEmail; }
        /**
         * Sets author email.
         *
         * @param authorEmail author email
         */
        public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }
        /**
         * Returns body.
         * @return the string
         */
        public String getBody() { return body; }
        /**
         * Sets body.
         *
         * @param body JSON request body
         */
        public void setBody(String body) { this.body = body; }
    }
}
