package br.com.netservicos.clients.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFormatEntityException extends RuntimeException{
    private String parameter;

    public InvalidFormatEntityException(String message, String parameter) {
        super(message);
        this.parameter = parameter;
    }
}
