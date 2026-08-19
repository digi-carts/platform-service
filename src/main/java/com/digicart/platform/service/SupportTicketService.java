package com.digicart.platform.service;

import com.digicart.platform.dto.SupportTicketDto;
import com.digicart.platform.entity.SupportTicket;
import com.digicart.platform.entity.TicketStatus;
import com.digicart.platform.exception.EntityNotFoundException;
import com.digicart.platform.repository.SupportTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementing support ticket use cases for <em>platform-service</em>.
 */
@Service
public class SupportTicketService {

    private final SupportTicketRepository repository;

    public SupportTicketService(SupportTicketRepository repository) {
        this.repository = repository;
    }

    public List<SupportTicket> findAll() {
        return repository.findAll();
    }

    public SupportTicket findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SupportTicket not found: " + id));
    }

    public List<SupportTicket> findByStoreId(String storeId) {
        return repository.findByStoreId(storeId);
    }

    public List<SupportTicket> findByAdminEmail(String adminEmail) {
        return repository.findByAdminEmail(adminEmail);
    }

    public List<SupportTicket> findByStatus(TicketStatus status) {
        return repository.findByStatus(status);
    }

    public SupportTicket create(SupportTicketDto.CreateRequest req) {
        SupportTicket ticket = new SupportTicket();
        ticket.setStoreId(req.getStoreId());
        ticket.setAdminEmail(req.getAdminEmail());
        ticket.setType(req.getType());
        ticket.setDescription(req.getDescription());
        return repository.save(ticket);
    }

    public SupportTicket update(String id, SupportTicketDto.UpdateRequest req) {
        SupportTicket ticket = findById(id);
        if (req.getStatus() != null) ticket.setStatus(req.getStatus());
        if (req.getDescription() != null) ticket.setDescription(req.getDescription());
        return repository.save(ticket);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }
}
