package com.begodex.taskflower.infra;

import com.begodex.taskflower.exceptions.*;
import com.begodex.taskflower.exceptions.httpExceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Hidden
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<APIErrorMessage> entityNotFoundHandler(EntityNotFoundException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(AuthenticationRequiredException.class)
    private ResponseEntity<APIErrorMessage> authenticationRequiredHandler(AuthenticationRequiredException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(UserIsAlreadyAdminException.class)
    private ResponseEntity<APIErrorMessage> userIsAlreadyAdminHandler(UserIsAlreadyAdminException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    private ResponseEntity<APIErrorMessage> insufficientBalanceHandler(InsufficientBalanceException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(TicketAlreadyPaidException.class)
    private ResponseEntity<APIErrorMessage> ticketAlreadyPaidHandler(TicketAlreadyPaidException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(TicketAlreadyUsedException.class)
    private ResponseEntity<APIErrorMessage> ticketAlreadyUsedHandler(TicketAlreadyUsedException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(TicketExpiredException.class)
    private ResponseEntity<APIErrorMessage> ticketExpiredHandler(TicketExpiredException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(TicketNotPaidException.class)
    private ResponseEntity<APIErrorMessage> ticketNotPaidHandler(TicketNotPaidException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<APIErrorMessage> userAlreadyExistsHandler(UserAlreadyExistsException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(YouDontHavePermissionException.class)
    private ResponseEntity<APIErrorMessage> youDontHavePermissionHandler(YouDontHavePermissionException exception){
        APIErrorMessage threatResponse = new APIErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }





}
