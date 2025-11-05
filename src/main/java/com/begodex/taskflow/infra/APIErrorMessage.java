package com.begodex.taskflow.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class APIErrorMessage {

    private HttpStatus httpStatus;
    private String message;
}
