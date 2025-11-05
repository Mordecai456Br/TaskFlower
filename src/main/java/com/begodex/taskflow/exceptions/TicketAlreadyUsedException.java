package com.begodex.taskflow.exceptions;

public class TicketAlreadyUsedException extends RuntimeException {
    public TicketAlreadyUsedException() {
        super("Ticket already used");
    }
}
