package com.begodex.taskflow.exceptions;

public class TicketNotPaidException extends RuntimeException {
    public TicketNotPaidException() {
        super("You must pay for the ticket before use.");
    }
}
