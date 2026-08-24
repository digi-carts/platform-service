package com.digicart.platform.service;

import com.digicart.platform.entity.PlatformConfig;
import com.digicart.platform.repository.PlatformConfigRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * Application service implementing platform config use cases for <em>platform-service</em>.
 */
@Service
public class PlatformConfigService {

    private static final Set<String> SENSITIVE = Set.of("cloudflareApiToken", "geminiApiKey");
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final String KEY_CF_TOKEN = "cloudflareApiToken";
    private static final String KEY_CF_ZONE = "cloudflareZoneId";
    private static final String KEY_CF_DOMAIN = "cloudflareDomain";
    private static final String KEY_CF_CONFIGURED = "cloudflareConfigured";
    private static final String KEY_GEMINI_KEY = "geminiApiKey";
    private static final String KEY_GEMINI_CONFIGURED = "geminiConfigured";
    private static final String KEY_GEMINI_MODEL = "geminiModel";
    private static final String KEY_BUSINESS_LEVELS = "businessLevels";

    private final PlatformConfigRepository repository;

    public PlatformConfigService(PlatformConfigRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> getData() {
        return masked(load().getData());
    }

    public Map<String, Object> getAdminSettings() {
        Map<String, Object> data = load().getData();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("adminInactiveDays", data.getOrDefault("adminInactiveDays", 30));
        result.put("cloudflareConfigured", data.containsKey("cloudflareApiToken"));
        result.put("cloudflareZoneId", data.getOrDefault("cloudflareZoneId", ""));
        result.put("cloudflareDomain", data.getOrDefault("cloudflareDomain", ""));
        result.put("storefrontHost", data.getOrDefault("storefrontHost", ""));
        result.put("geminiConfigured", data.containsKey("geminiApiKey") && !String.valueOf(data.getOrDefault("geminiApiKey", "")).isBlank());
        result.put("geminiModel", data.getOrDefault("geminiModel", "gemini-3.5-flash-lite"));
        return result;
    }

    public Object getInfoContent() {
        return load().getData().getOrDefault("infoContent", new HashMap<>());
    }

    public Map<String, Object> patch(Map<String, Object> incoming) {
        PlatformConfig config = load();
        Map<String, Object> data = new HashMap<>(config.getData());
        data.putAll(incoming);
        config.setData(data);
        repository.save(config);
        return masked(data);
    }

    public Map<String, Object> testCloudflare() {
        Map<String, Object> data = load().getData();
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

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getBusinessLevels() {
        Object levels = load().getData().get(KEY_BUSINESS_LEVELS);
        if (levels instanceof List<?> list) {
            return (List<Map<String, Object>>) list;
        }
        return new ArrayList<>();
    }

    public List<Map<String, Object>> addBusinessLevel(Map<String, Object> level) {
        PlatformConfig config = load();
        Map<String, Object> data = new HashMap<>(config.getData());
        List<Map<String, Object>> levels = getBusinessLevels();
        Object rawKey = level.getOrDefault("key", level.get("label"));
        String key = rawKey instanceof String s ? s : null;
        if (key == null || key.isBlank()) throw new IllegalArgumentException("Level key is required");
        boolean exists = levels.stream().anyMatch(l -> key.equals(l.get("key")));
        if (exists) throw new IllegalArgumentException("Level key already exists: " + key);
        level.put("key", key);
        levels.add(level);
        data.put(KEY_BUSINESS_LEVELS, levels);
        config.setData(data);
        repository.save(config);
        return levels;
    }

    public List<Map<String, Object>> updateBusinessLevel(String originalKey, Map<String, Object> patch) {
        PlatformConfig config = load();
        Map<String, Object> data = new HashMap<>(config.getData());
        List<Map<String, Object>> levels = getBusinessLevels();
        boolean found = false;
        for (int i = 0; i < levels.size(); i++) {
            if (originalKey.equals(levels.get(i).get("key"))) {
                Map<String, Object> updated = new LinkedHashMap<>(levels.get(i));
                updated.putAll(patch);
                levels.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) throw new NoSuchElementException("Business level not found: " + originalKey);
        data.put(KEY_BUSINESS_LEVELS, levels);
        config.setData(data);
        repository.save(config);
        return levels;
    }

    public List<Map<String, Object>> deleteBusinessLevel(String key) {
        PlatformConfig config = load();
        Map<String, Object> data = new HashMap<>(config.getData());
        List<Map<String, Object>> levels = getBusinessLevels();
        boolean removed = levels.removeIf(l -> key.equals(l.get("key")));
        if (!removed) throw new NoSuchElementException("Business level not found: " + key);
        data.put(KEY_BUSINESS_LEVELS, levels);
        config.setData(data);
        repository.save(config);
        return levels;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getWizardConfig() {
        Object raw = load().getData().get("wizardConfig");
        if (raw instanceof Map<?, ?> m) {
            Object steps = m.get("steps");
            if (steps instanceof List<?> list && !list.isEmpty()) {
                return (Map<String, Object>) raw;
            }
        }
        return defaultWizardConfig();
    }

    public Map<String, Object> saveWizardConfig(Map<String, Object> incoming) {
        PlatformConfig config = load();
        Map<String, Object> data = new HashMap<>(config.getData());
        data.put("wizardConfig", incoming);
        config.setData(data);
        repository.save(config);
        return incoming;
    }

    private static Map<String, Object> wizardStep(String key, String label, boolean locked, boolean skippable, List<Map<String, Object>> fields) {
        Map<String, Object> s = new LinkedHashMap<>();
        s.put("key", key);
        s.put("label", label);
        s.put("description", "");
        s.put("enabled", true);
        s.put("skippable", skippable);
        if (locked) s.put("locked", true);
        if (fields != null) s.put("fields", fields);
        return s;
    }

    private static Map<String, Object> wizardField(String key, String label) {
        Map<String, Object> f = new LinkedHashMap<>();
        f.put("key", key);
        f.put("label", label);
        f.put("enabled", true);
        f.put("required", false);
        return f;
    }

    private static Map<String, Object> defaultWizardConfig() {
        List<Map<String, Object>> shopFields = List.of(
                wizardField("email", "Email"), wizardField("phone", "Phone"),
                wizardField("currency", "Currency"), wizardField("country", "Country"),
                wizardField("address", "Address"), wizardField("logo", "Store Logo"),
                wizardField("pwaIcon", "PWA Icon"));
        List<Map<String, Object>> notifFields = List.of(
                wizardField("whatsapp", "WhatsApp"), wizardField("sms", "SMS"),
                wizardField("email", "Email (SMTP)"));
        List<Map<String, Object>> steps = List.of(
                wizardStep("shop",         "Shop Details",  true,  false, shopFields),
                wizardStep("domain",       "Domain",        false, true,  null),
                wizardStep("payments",     "Payments",      false, true,  null),
                wizardStep("notifications","Notifications", false, true,  notifFields),
                wizardStep("ai",           "AI Assistant",  false, true,  null),
                wizardStep("subscription", "Subscription",  false, true,  null),
                wizardStep("finish",       "Finish",        true,  false, null));
        return Map.of("steps", steps);
    }

    private PlatformConfig load() {
        return repository.findById("singleton").orElseGet(() -> {
            PlatformConfig config = new PlatformConfig();
            config.setId("singleton");
            config.setData(new HashMap<>());
            return repository.save(config);
        });
    }

    private Map<String, Object> masked(Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>(data);
        SENSITIVE.forEach(result::remove);
        result.put("cloudflareConfigured", data.containsKey("cloudflareApiToken"));
        result.put("geminiConfigured", data.containsKey("geminiApiKey") && !String.valueOf(data.getOrDefault("geminiApiKey", "")).isBlank());
        return result;
    }
}
