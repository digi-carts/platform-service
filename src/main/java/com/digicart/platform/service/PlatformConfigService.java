package com.digicart.platform.service;

import com.digicart.platform.entity.PlatformConfig;
import com.digicart.platform.repository.PlatformConfigRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Application service implementing platform config use cases for <em>platform-service</em>.
 */
@Service
public class PlatformConfigService {

    private static final Set<String> SENSITIVE = Set.of("cloudflareApiToken", "geminiApiKey");
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final String KEY_CF_TOKEN = "cloudflareApiToken";
    private static final String KEY_CF_ZONE = "cloudflareZoneId";
    private static final String KEY_CF_DOMAIN = "cloudflareDomain";
    private static final String KEY_CF_CONFIGURED = "cloudflareConfigured";
    private static final String KEY_GEMINI_KEY = "geminiApiKey";
    private static final String KEY_GEMINI_CONFIGURED = "geminiConfigured";
    private static final String KEY_GEMINI_MODEL = "geminiModel";

    private final PlatformConfigRepository repository;
    private final ObjectMapper objectMapper;

    public PlatformConfigService(PlatformConfigRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> getData() {
        return masked(parse(load().getData()));
    }

    public Map<String, Object> getAdminSettings() {
        Map<String, Object> data = parse(load().getData());
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("adminInactiveDays", data.getOrDefault("adminInactiveDays", 30));
        result.put("cloudflareConfigured", data.containsKey("cloudflareApiToken"));
        result.put("cloudflareZoneId", data.getOrDefault("cloudflareZoneId", ""));
        result.put("cloudflareDomain", data.getOrDefault("cloudflareDomain", ""));
        result.put("storefrontHost", data.getOrDefault("storefrontHost", ""));
        result.put("geminiConfigured", data.containsKey("geminiApiKey") && !((String) data.getOrDefault("geminiApiKey", "")).isBlank());
        result.put("geminiModel", data.getOrDefault("geminiModel", "gemini-3.5-flash-lite"));
        return result;
    }

    public Object getInfoContent() {
        Map<String, Object> data = parse(load().getData());
        return data.getOrDefault("infoContent", new HashMap<>());
    }

    public Map<String, Object> patch(Map<String, Object> incoming) {
        PlatformConfig config = load();
        Map<String, Object> data = parse(config.getData());
        data.putAll(incoming);
        config.setData(serialize(data));
        repository.save(config);
        return masked(data);
    }

    public Map<String, Object> testCloudflare() {
        Map<String, Object> data = parse(load().getData());
        String token = (String) data.get("cloudflareApiToken");
        String zoneId = (String) data.get("cloudflareZoneId");
        if (token == null || token.isBlank() || zoneId == null || zoneId.isBlank()) {
            return Map.of("ok", false, "error", "Cloudflare API token or Zone ID not configured");
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.cloudflare.com/client/v4/zones/" + zoneId))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return Map.of("ok", true);
            }
            return Map.of("ok", false, "error", "Cloudflare returned status " + response.statusCode());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return Map.of("ok", false, "error", e.getMessage());
        }
    }

    private PlatformConfig load() {
        return repository.findById("singleton").orElseGet(() -> {
            PlatformConfig config = new PlatformConfig();
            config.setId("singleton");
            config.setData("{}");
            return repository.save(config);
        });
    }

    private Map<String, Object> parse(String json) {
        try {
            Map<String, Object> result = objectMapper.readValue(json, MAP_TYPE);
            return new HashMap<>(result);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    private String serialize(Map<String, Object> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            return "{}";
        }
    }

    private Map<String, Object> masked(Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>(data);
        SENSITIVE.forEach(result::remove);
        result.put("cloudflareConfigured", data.containsKey("cloudflareApiToken"));
        result.put("geminiConfigured", data.containsKey("geminiApiKey") && !((String) data.getOrDefault("geminiApiKey", "")).isBlank());
        return result;
    }
}
