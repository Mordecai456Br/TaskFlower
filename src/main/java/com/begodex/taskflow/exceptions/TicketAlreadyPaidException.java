package com.begodex.taskflow.exceptions;

public class TicketAlreadyPaidException extends RuntimeException {
    public TicketAlreadyPaidException() {
        super("Ticket already paid");
    }
}
