package com.digicart.platform.controller;

import com.digicart.platform.dto.TicketCommentDto;
import com.digicart.platform.entity.TicketComment;
import com.digicart.platform.service.TicketCommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket-comments")
public class TicketCommentController {

    private final TicketCommentService service;

    public TicketCommentController(TicketCommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<TicketComment> findByTicketId(
            @RequestParam String ticketId,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.findByTicketId(ticketId);
    }

    @GetMapping("/{id}")
    public TicketComment findById(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<TicketComment> create(
            @Valid @RequestBody TicketCommentDto.CreateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
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
