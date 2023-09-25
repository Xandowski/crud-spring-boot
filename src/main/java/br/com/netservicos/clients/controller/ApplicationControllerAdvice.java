package br.com.netservicos.clients.controller;

import br.com.netservicos.clients.exception.InvalidFormatEntityException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequestException(MethodArgumentNotValidException ex){
        String errorMessage = "";
        if(ex.getBindingResult().getAllErrors().size() == 1){
            errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        } else if(ex.getBindingResult().getAllErrors().size() == 2){
            errorMessage = ex.getBindingResult().getAllErrors().get(1).getDefaultMessage();
        }

        return getObjectResponseEntity(errorMessage, BAD_REQUEST, "Validation failed");

    }

    @ExceptionHandler(InvalidFormatEntityException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidFormatEntityException(InvalidFormatEntityException ex){
        String message = ex.getMessage();

        return getObjectResponseEntity(message, BAD_REQUEST, "Validation failed");
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    @ResponseStatus(BAD_REQUEST)
//    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//        return getObjectResponseEntity(ex.getMessage(), BAD_REQUEST, "Constratin");
//    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        return getObjectResponseEntity(ex.getMessage(), NOT_FOUND, "Client not Found.");
    }

    private ResponseEntity<Object> getObjectResponseEntity(String errorMessage, HttpStatus httpStatus, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", httpStatus.value());
        errorResponse.put("reason", httpStatus.getReasonPhrase());
        errorResponse.put("message", message);
        errorResponse.put("error", errorMessage);

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
