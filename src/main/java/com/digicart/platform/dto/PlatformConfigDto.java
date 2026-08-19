package com.digicart.platform.dto;

public class PlatformConfigDto {

    public static class UpdateRequest {
        private String data = "{}";

        public String getData() { return data; }
        public void setData(String data) { this.data = data; }
    }
}
