package com.begodex.taskflower.exceptions;

public class TicketAlreadyUsedException extends RuntimeException {
    public TicketAlreadyUsedException() {
        super("Ticket already used");
    }
}
