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
        /**
         * Returns data.
         * @return the string
         */
        public String getData() { return data; }
        /**
         * Sets data.
         *
         * @param data data
         */
        public void setData(String data) { this.data = data; }
    }
}
