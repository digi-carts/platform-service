package com.digicart.platform.controller;

import com.digicart.platform.dto.PlatformConfigDto;
import com.digicart.platform.entity.PlatformConfig;
import com.digicart.platform.service.PlatformConfigService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing platform config HTTP APIs for <em>platform-service</em>.
 */
@RestController
@RequestMapping("/platform-config")
public class PlatformConfigController {

    private final PlatformConfigService service;

    /**
     * Creates a new {@code PlatformConfigController}.
     *
     * @param service service
     */
    public PlatformConfigController(PlatformConfigService service) {
        this.service = service;
    }

    /**
     * Handles GET.
     *
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the platform config
     */
    @GetMapping
    public PlatformConfig get(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.get();
    }

    /**
     * Handles PUT.
     *
     * @param req request payload
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the platform config
     */
    @PutMapping
    public PlatformConfig update(
            @RequestBody PlatformConfigDto.UpdateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.update(req);
    }
}
