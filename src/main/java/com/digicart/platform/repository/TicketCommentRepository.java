package com.digicart.platform.repository;

import com.digicart.platform.entity.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for ticket comment  persistence.
 */
@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, String> {
    /**
     * Finds by ticket id.
     *
     * @param ticketId ticket id
     * @return matching records
     */
    List<TicketComment> findByTicketId(String ticketId);
}
