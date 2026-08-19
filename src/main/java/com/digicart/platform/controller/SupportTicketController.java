package com.digicart.platform.controller;

import com.digicart.platform.dto.SupportTicketDto;
import com.digicart.platform.entity.SupportTicket;
import com.digicart.platform.entity.TicketStatus;
import com.digicart.platform.service.SupportTicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing support ticket HTTP APIs for <em>platform-service</em>.
 */
@RestController
@RequestMapping("/support-tickets")
public class SupportTicketController {

    private final SupportTicketService service;

    /**
     * Creates a new {@code SupportTicketController}.
     *
     * @param service service
     */
    public SupportTicketController(SupportTicketService service) {
        this.service = service;
    }

    /**
     * Handles GET.
     *
     * @param storeId store (tenant) identifier
     * @param adminEmail admin email
     * @param status status
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return matching records
     */
    @GetMapping
    public List<SupportTicket> findAll(
            @RequestParam(required = false) String storeId,
            @RequestParam(required = false) String adminEmail,
            @RequestParam(required = false) TicketStatus status,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (storeId != null) return service.findByStoreId(storeId);
        if (adminEmail != null) return service.findByAdminEmail(adminEmail);
        if (status != null) return service.findByStatus(status);
        return service.findAll();
    }

    /**
     * Handles {@code GET /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the support ticket
     */
    @GetMapping("/{id}")
    public SupportTicket findById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.findById(id);
    }

    /**
     * Handles POST.
     *
     * @param req request payload
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @PostMapping
    public ResponseEntity<SupportTicket> create(
            @Valid @RequestBody SupportTicketDto.CreateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /**
     * Handles {@code PUT /{id}}.
     *
     * @param id resource identifier
     * @param req request payload
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the support ticket
     */
    @PutMapping("/{id}")
    public SupportTicket update(
            @PathVariable String id,
            @RequestBody SupportTicketDto.UpdateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.update(id, req);
    }

    /**
     * Handles {@code DELETE /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return HTTP response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
