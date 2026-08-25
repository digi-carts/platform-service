package com.digicart.platform.controller;

import com.digicart.platform.repository.StoreTemplateRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
