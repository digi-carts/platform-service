package com.digicart.platform.dto;

/**
 * Request/response DTO: Platform Config Dto.
 */
public class PlatformConfigDto {

    /**
     * Request/response DTO: Update Request.
     */
    public static class UpdateRequest {
        private String data = "{}";

        public String getData() { return data; }
        public void setData(String data) { this.data = data; }
    }
}
