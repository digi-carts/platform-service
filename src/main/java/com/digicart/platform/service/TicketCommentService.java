package com.digicart.platform.service;

import com.digicart.platform.dto.TicketCommentDto;
import com.digicart.platform.entity.TicketComment;
import com.digicart.platform.exception.EntityNotFoundException;
import com.digicart.platform.repository.TicketCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketCommentService {

    private final TicketCommentRepository repository;

    public TicketCommentService(TicketCommentRepository repository) {
        this.repository = repository;
    }

    public List<TicketComment> findByTicketId(String ticketId) {
        return repository.findByTicketId(ticketId);
    }

    public TicketComment findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TicketComment not found: " + id));
    }

    public TicketComment create(TicketCommentDto.CreateRequest req) {
        TicketComment comment = new TicketComment();
        comment.setTicketId(req.getTicketId());
        comment.setAuthorRole(req.getAuthorRole());
        comment.setAuthorEmail(req.getAuthorEmail());
        comment.setBody(req.getBody());
        return repository.save(comment);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }
}
