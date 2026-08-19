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
    /**
     * Finds by store id.
     *
     * @param storeId store (tenant) identifier
     * @return matching records
     */
    List<SupportTicket> findByStoreId(String storeId);
    /**
     * Finds by admin email.
     *
     * @param adminEmail admin email
     * @return matching records
     */
    List<SupportTicket> findByAdminEmail(String adminEmail);
    /**
     * Finds by status.
     *
     * @param status status
     * @return matching records
     */
    List<SupportTicket> findByStatus(TicketStatus status);
}
