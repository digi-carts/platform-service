package com.digicart.platform.repository;

import com.digicart.platform.entity.SupportTicket;
import com.digicart.platform.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for support ticket  persistence.
 */
@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, String> {
    List<SupportTicket> findByStoreId(String storeId);
    List<SupportTicket> findByAdminEmail(String adminEmail);
    List<SupportTicket> findByStatus(TicketStatus status);
    long countByStatusIn(List<TicketStatus> statuses);
}
