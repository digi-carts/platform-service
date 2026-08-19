package com.digicart.platform.service;

import com.digicart.platform.dto.TicketCommentDto;
import com.digicart.platform.entity.TicketComment;
import com.digicart.platform.exception.EntityNotFoundException;
import com.digicart.platform.repository.TicketCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementing ticket comment use cases for <em>platform-service</em>.
 */
@Service
public class TicketCommentService {

    private final TicketCommentRepository repository;

    /**
     * Creates a new {@code TicketCommentService}.
     *
     * @param repository repository
     */
    public TicketCommentService(TicketCommentRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds by ticket id.
     *
     * @param ticketId ticket id
     * @return matching records
     */
    public List<TicketComment> findByTicketId(String ticketId) {
        return repository.findByTicketId(ticketId);
    }

    /**
     * Finds by id.
     *
     * @param id resource identifier
     * @return the ticket comment
     */
    public TicketComment findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TicketComment not found: " + id));
    }

    /**
     * Creates a new record.
     *
     * @param req request payload
     * @return the ticket comment
     */
    public TicketComment create(TicketCommentDto.CreateRequest req) {
        TicketComment comment = new TicketComment();
        comment.setTicketId(req.getTicketId());
        comment.setAuthorRole(req.getAuthorRole());
        comment.setAuthorEmail(req.getAuthorEmail());
        comment.setBody(req.getBody());
        return repository.save(comment);
    }

    /**
     * Deletes the record.
     *
     * @param id resource identifier
     */
    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }
}
