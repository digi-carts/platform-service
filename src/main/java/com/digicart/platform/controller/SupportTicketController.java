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

    public SupportTicketController(SupportTicketService service) {
        this.service = service;
    }

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

    @GetMapping("/{id}")
    public SupportTicket findById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<SupportTicket> create(
            @Valid @RequestBody SupportTicketDto.CreateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public SupportTicket update(
            @PathVariable String id,
            @RequestBody SupportTicketDto.UpdateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
