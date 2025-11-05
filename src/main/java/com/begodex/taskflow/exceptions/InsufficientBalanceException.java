package com.begodex.taskflow.exceptions;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(BigDecimal price, BigDecimal userBalance) {
        super("Insufficient balance | ticket price: " + price + " | your balance: "+userBalance);
    }
}
