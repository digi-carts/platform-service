package com.digicart.platform.controller;

import com.digicart.platform.entity.StoreTemplate;
import com.digicart.platform.repository.StoreTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/platform/templates")
public class StoreTemplateController {

    private final StoreTemplateRepository repository;

    public StoreTemplateController(StoreTemplateRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Map<String, Object> list() {
        List<Map<String, Object>> templates = repository.findAll().stream()
                .map(t -> Map.<String, Object>of(
                        "id", t.getId(),
                        "key", t.getKey(),
                        "name", t.getName(),
                        "description", t.getDescription() != null ? t.getDescription() : "",
                        "enabled", t.isEnabled()
                ))
                .toList();
        return Map.of("templates", templates);
    }

    @PatchMapping("/{id}")
    public Map<String, Object> update(@PathVariable String id, @RequestBody Map<String, Object> body) {
        StoreTemplate template = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Template not found: " + id));
        if (body.containsKey("enabled")) {
            template.setEnabled((Boolean) body.get("enabled"));
        }
        repository.save(template);
        return Map.of(
                "id", template.getId(),
                "key", template.getKey(),
                "name", template.getName(),
                "description", template.getDescription() != null ? template.getDescription() : "",
                "enabled", template.isEnabled()
        );
    }
}
