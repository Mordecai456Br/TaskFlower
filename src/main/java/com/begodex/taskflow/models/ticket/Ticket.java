package com.begodex.taskflow.models.ticket;

import com.begodex.taskflow.models.race.Race;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "tickets")
@Table(name = "tickets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relação com Race
    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    // Relação com User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private com.begodex.taskflow.models.user.User user;

    // Preço do ticket
    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private boolean paid;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.PENDING;


    // Substitua o construtor existente por este (Ticket.java)
    public Ticket(com.begodex.taskflow.models.user.User user, Race race, BigDecimal price) {
        this.user = user;          // associa a entidade User
        this.race = race;          // associa a entidade Race
        this.price = price;
        this.used = false;
        this.paid = false;
        this.createdAt = LocalDateTime.now();
        this.status = TicketStatus.PENDING;
    }

}
