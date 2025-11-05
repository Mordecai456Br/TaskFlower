package com.begodex.taskflow.models.ticket;

public enum TicketStatus {
    PENDING, // criado, aguardando pagamento
    PAID,    // comprado/pago
    EXPIRED, // expirado por não pagamento
    USED     // usado na corrida
}
