package com.begodex.taskflow.DTO;


import com.begodex.taskflow.models.ticket.Ticket;
import com.begodex.taskflow.models.ticket.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketResponseDTO(
        Long id,
        String raceName,
        BigDecimal price,
        boolean used,
        boolean paid,
        TicketStatus status,
        LocalDateTime createdAt
) {

    public TicketResponseDTO(Ticket ticket) {
        this(
                ticket.getId(),
                ticket.getRace().getName(),
                ticket.getPrice(),
                ticket.isUsed(),
                ticket.isPaid(),
                ticket.getStatus(),
                ticket.getCreatedAt()
        );
    }
}
