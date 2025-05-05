package com.previred.entrevista.exceptions;

import com.previred.entrevista.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerGlobal {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse("Error al procesar la solicitud"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleExceptionEmail(InvalidEmailException e) {
        return new ResponseEntity<>(
                new ErrorResponse("Formato Email incorrecto"),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(PasswordPatternException.class)
    public ResponseEntity<Object> handleExceptionPassword(PasswordPatternException e) {
        return new ResponseEntity<>(
                new ErrorResponse("Contraseña ingresada no cumple con la recomendacion siguiente: al menos una letra mayuscula, un digito y minimo 6 caracteres"),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}
