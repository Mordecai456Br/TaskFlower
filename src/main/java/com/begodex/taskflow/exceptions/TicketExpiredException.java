package com.begodex.taskflow.exceptions;

public class TicketExpiredException extends RuntimeException {
    public TicketExpiredException() {
        super("Ticket expired, please create a new one.");
    }
}
