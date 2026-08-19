package com.digicart.platform.controller;

import com.digicart.platform.dto.TicketCommentDto;
import com.digicart.platform.entity.TicketComment;
import com.digicart.platform.service.TicketCommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing ticket comment HTTP APIs for <em>platform-service</em>.
 */
@RestController
@RequestMapping("/ticket-comments")
public class TicketCommentController {

    private final TicketCommentService service;

    /**
     * Creates a new {@code TicketCommentController}.
     *
     * @param service service
     */
    public TicketCommentController(TicketCommentService service) {
        this.service = service;
    }

    /**
     * Handles GET.
     *
     * @param ticketId ticket id
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return matching records
     */
    @GetMapping
    public List<TicketComment> findByTicketId(
            @RequestParam String ticketId,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return service.findByTicketId(ticketId);
    }

    /**
     * Handles {@code GET /{id}}.
     *
     * @param id resource identifier
     * @param userId caller user id from the gateway ({@code X-User-Id})
     * @param userRole caller role from the gateway ({@code X-User-Role})
     * @return the ticket comment
     */
    @GetMapping("/{id}")
    public TicketComment findById(
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
    public ResponseEntity<TicketComment> create(
            @Valid @RequestBody TicketCommentDto.CreateRequest req,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
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
