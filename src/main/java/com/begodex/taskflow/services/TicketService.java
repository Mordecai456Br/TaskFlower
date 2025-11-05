package com.begodex.taskflow.services;

import com.begodex.taskflow.DTO.TicketResponseDTO;
import com.begodex.taskflow.exceptions.*;
import com.begodex.taskflow.models.ticket.Ticket;
import com.begodex.taskflow.models.ticket.TicketStatus;
import com.begodex.taskflow.repositories.RaceRepository;
import com.begodex.taskflow.repositories.TicketRepository;
import com.begodex.taskflow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço de tickets:
 * - método buyTicket lança exceções específicas em vez de retornar Optional.empty()
 * - getTicketsForUser retorna tickets filtrados por status
 * - markTicketAsUsed lança TicketAlreadyUsedException se já usado
 */
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RaceRepository raceRepository;


    // 1️⃣ Cria o ticket (status = PENDING)
    @Transactional
    public Ticket createTicket(Long userId, Long raceId, BigDecimal price) {
        // Busca as entidades relacionadas (lança EntityNotFoundException se não existirem)
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        var race = raceRepository.findById(raceId)
                .orElseThrow(() -> new EntityNotFoundException("Race", raceId));

        // Cria o ticket corretamente associando as entidades e preenchendo os campos
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setRace(race);
        ticket.setPrice(price);
        ticket.setPaid(false);
        ticket.setUsed(false);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setStatus(TicketStatus.PENDING);

        return ticketRepository.save(ticket);
    }

    // 2️⃣ Compra / paga o ticket
    @Transactional
    public TicketResponseDTO payTicket(Long ticketId) {
        // busca ticket (lança EntityNotFoundException se não existir)
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket", ticketId));

        // checa expiração (10 minutos)
        if (ticket.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now())) {
            ticket.setStatus(TicketStatus.EXPIRED);
            ticketRepository.save(ticket);
            throw new TicketExpiredException();
        }

        if (ticket.isPaid() || ticket.getStatus() == TicketStatus.PAID) {
            throw new TicketAlreadyPaidException();
        }

        var user = ticket.getUser();
        if (user == null) {
            throw new EntityNotFoundException("User", null);
        }

        // verifica saldo usando BigDecimal corretamente
        // se balance.compareTo(price) < 0 então saldo insuficiente
        if (user.getCurrency_balance() == null || user.getCurrency_balance().compareTo(ticket.getPrice()) < 0) {
            throw new InsufficientBalanceException(ticket.getPrice(), user.getCurrency_balance());

        }

        // debita do usuário e persiste o novo saldo
        BigDecimal newBalance = user.getCurrency_balance().subtract(ticket.getPrice());
        user.setCurrency_balance(newBalance);
        userRepository.save(user);


        ticket.setPaid(true);
        ticket.setStatus(TicketStatus.PAID);
        ticketRepository.save(ticket);

        // retorna DTO com o estado atualizado
        return new TicketResponseDTO(ticket);
    }




    // Marca ticket como usado por ID. Lança TicketAlreadyUsedException se já estiver usado.

    @Transactional
    public TicketResponseDTO markTicketAsUsed(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket", id));

        if (ticket.getStatus() != TicketStatus.PAID) {
            throw new TicketNotPaidException();
        }

        if (ticket.getStatus() == TicketStatus.USED) {
            throw new TicketAlreadyUsedException();
        }

        ticket.setStatus(TicketStatus.USED);
        ticket.setUsed(true);

        Ticket ticketSaved = ticketRepository.save(ticket);

        return new TicketResponseDTO(ticketSaved);
    }



    /*
      Retorna tickets do usuário:
      - status = "active"  -> tickets não usados
      - status = "used"    -> tickets usados
      - status = "all"     -> todos os tickets
     */
    public List<TicketResponseDTO> getTicketsFromUser(Long userId, String statusParam) {

        String status = (statusParam == null) ? "all" : statusParam.toLowerCase();

        var tickets = switch (status) {
            case "pending" -> ticketRepository.findByUserIdAndStatus(userId, TicketStatus.PENDING);
            case "paid" -> ticketRepository.findByUserIdAndStatus(userId, TicketStatus.PAID);
            case "used" -> ticketRepository.findByUserIdAndStatus(userId, TicketStatus.USED);
            case "expired" -> ticketRepository.findByUserIdAndStatus(userId, TicketStatus.EXPIRED);
            default -> ticketRepository.findByUserId(userId); // todos os tickets
        };

        return tickets.stream().map(TicketResponseDTO::new).toList();
    }

    @Scheduled(fixedRate = 60000) // roda a cada minuto
    @Transactional
    public void expirePendingTickets() {
        LocalDateTime now = LocalDateTime.now();
        List<Ticket> expiredTickets = ticketRepository
                .findByStatusAndCreatedAtBefore(TicketStatus.PENDING, now.minusMinutes(10));

        for (Ticket t : expiredTickets) {
            t.setStatus(TicketStatus.EXPIRED);
        }

        ticketRepository.saveAll(expiredTickets);
    }

}
