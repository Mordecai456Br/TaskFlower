package com.begodex.taskflow.repositories;

import com.begodex.taskflow.models.ticket.Ticket;
import com.begodex.taskflow.models.ticket.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByUserIdAndUsed(Long userId, boolean used);

    List<Ticket> findByUserIdAndStatus(Long userId, TicketStatus ticketStatus);

    List<Ticket> findByStatusAndCreatedAtBefore(TicketStatus ticketStatus, LocalDateTime localDateTime);
}
