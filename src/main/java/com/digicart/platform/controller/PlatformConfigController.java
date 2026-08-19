package com.digicart.platform.controller;

import com.digicart.platform.dto.PlatformConfigDto;
import com.digicart.platform.entity.PlatformConfig;
import com.digicart.platform.service.PlatformConfigService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing platform config HTTP APIs for <em>platform-service</em>.
 */
@RestController
@RequestMapping("/api/platform")
public class PlatformConfigController {

    private final PlatformConfigService service;

    public PlatformConfigController(PlatformConfigService service) {
        this.service = service;
    }

    @GetMapping
    public PlatformConfig get(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.get();
    }

    @PutMapping
    public PlatformConfig update(
            @RequestBody PlatformConfigDto.UpdateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.update(req);
    }
}
