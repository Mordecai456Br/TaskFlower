package com.begodex.taskflower.exceptions;

public class TicketAlreadyPaidException extends RuntimeException {
    public TicketAlreadyPaidException() {
        super("Ticket already paid");
    }
}
