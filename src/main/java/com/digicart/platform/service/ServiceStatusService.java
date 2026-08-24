package com.digicart.platform.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceStatusService {

    private static final HttpClient HTTP = HttpClient.newHttpClient();
    private static final String METADATA_TOKEN_URL =
            "http://metadata.google.internal/computeMetadata/v1/instance/service-accounts/default/token";
    private static final String CLOUD_RUN_API =
            "https://run.googleapis.com/v2/projects/%s/locations/%s/services";

    @Value("${GCP_PROJECT_ID:digi-carts}")
    private String projectId;

    @Value("${GCP_REGION:us-east1}")
    private String region;

    private final ObjectMapper mapper;

    public ServiceStatusService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Map<String, Object> getStatus() throws IOException, InterruptedException {
        String token = fetchAccessToken();
        List<Map<String, Object>> services = fetchServices(token);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("services", services);
        result.put("fetchedAt", Instant.now().toString());
        return result;
    }

    private String fetchAccessToken() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(METADATA_TOKEN_URL))
                .header("Metadata-Flavor", "Google")
                .GET()
                .build();
        HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) {
            throw new IOException("Failed to fetch GCP access token: HTTP " + res.statusCode());
        }
        return mapper.readTree(res.body()).get("access_token").asText();
    }

    private List<Map<String, Object>> fetchServices(String token) throws IOException, InterruptedException {
        String url = String.format(CLOUD_RUN_API, projectId, region);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
        HttpResponse<String> res = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() != 200) {
            throw new IOException("Cloud Run API returned HTTP " + res.statusCode());
        }
        JsonNode servicesNode = mapper.readTree(res.body()).path("services");
        List<Map<String, Object>> result = new ArrayList<>();
        if (servicesNode.isArray()) {
            for (JsonNode svc : servicesNode) {
                result.add(toServiceStatus(svc));
            }
        }
        return result;
    }

    private Map<String, Object> toServiceStatus(JsonNode svc) {
        String fullName = svc.path("name").asText("");
        String name = fullName.contains("/") ? fullName.substring(fullName.lastIndexOf('/') + 1) : fullName;

        String status = resolveStatus(svc.path("conditions"));

        JsonNode scaling = svc.path("scaling");
        int minInstances = scaling.path("minInstanceCount").asInt(0);
        int maxInstances = scaling.path("maxInstanceCount").asInt(0);

        JsonNode uriNode = svc.path("uri");
        String uri = (!uriNode.isNull() && !uriNode.isMissingNode()) ? uriNode.asText(null) : null;

        String latestRevisionFull = svc.path("latestReadyRevision").asText(null);
        String lastRevision = null;
        if (latestRevisionFull != null && !latestRevisionFull.isBlank()) {
            lastRevision = latestRevisionFull.contains("/")
                    ? latestRevisionFull.substring(latestRevisionFull.lastIndexOf('/') + 1)
                    : latestRevisionFull;
        }

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("name", name);
        m.put("status", status);
        m.put("instances", "running".equals(status) ? 1 : 0);
        m.put("minInstances", minInstances);
        m.put("maxInstances", maxInstances);
        m.put("url", uri);
        m.put("lastRevision", lastRevision);
        return m;
    }

    private String resolveStatus(JsonNode conditions) {
        if (conditions.isArray()) {
            for (JsonNode c : conditions) {
                if ("Ready".equals(c.path("type").asText())) {
                    return switch (c.path("state").asText("")) {
                        case "CONDITION_SUCCEEDED" -> "running";
                        case "CONDITION_FAILED" -> "inactive";
                        default -> "unknown";
                    };
                }
            }
        }
        return "unknown";
    }
}
