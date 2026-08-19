package com.digicart.platform.repository;

import com.digicart.platform.entity.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, String> {
    List<TicketComment> findByTicketId(String ticketId);
}
