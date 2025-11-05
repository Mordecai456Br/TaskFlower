package com.begodex.taskflow.controllers;

import com.begodex.taskflow.DTO.BuyTicketDTO;
import com.begodex.taskflow.DTO.TicketResponseDTO;
import com.begodex.taskflow.models.ticket.Ticket;
import com.begodex.taskflow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.begodex.taskflow.services.AuthorizationService.getAuthenticatedUserId;


@RestController
@RequestMapping("tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;


    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody BuyTicketDTO body) {
        Long userId = getAuthenticatedUserId();
        Ticket ticket = ticketService.createTicket(userId, body.raceId(), body.price());
        TicketResponseDTO ticketDTO = new TicketResponseDTO(ticket);
        return ResponseEntity.ok(ticketDTO);
    }


    @PostMapping("/{id}/buy")
    public ResponseEntity<TicketResponseDTO> buyTicket(@PathVariable Long id) {
        Long userId = getAuthenticatedUserId();
        TicketResponseDTO ticket = ticketService.payTicket(id);
        return ResponseEntity.ok(ticket);
    }

    // GET /tickets?status=all|pending|paid|used|expired
    @GetMapping
    public ResponseEntity<?> listMyTickets(
            @RequestParam(name = "status", required = false, defaultValue = "all") String status) {

        Long userId = getAuthenticatedUserId();


        var ticketsByStatus = ticketService.getTicketsFromUser(userId, status.toLowerCase());

        return ResponseEntity.ok(ticketsByStatus);
    }


    // PUT /tickets/{id}/use
    @PutMapping("/{id}/use")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketResponseDTO> markAsUsed(@PathVariable Long id) {
        TicketResponseDTO ticket = ticketService.markTicketAsUsed(id);
        return ResponseEntity.ok(ticket);
    }
}
