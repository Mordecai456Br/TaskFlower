package com.begodex.taskflower.exceptions;

public class TicketNotPaidException extends RuntimeException {
    public TicketNotPaidException() {
        super("You must pay for the ticket before use.");
    }
}
