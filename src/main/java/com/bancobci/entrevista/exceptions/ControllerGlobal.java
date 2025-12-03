package com.bancobci.entrevista.exceptions;

import com.bancobci.entrevista.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerGlobal {
    private static final Logger log = LoggerFactory.getLogger(ControllerGlobal.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.error("Error no manejado", e);
        return new ResponseEntity<>(
                new ErrorResponse("Error al procesar la solicitud"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleExceptionEmail(InvalidEmailException e) {
        String mensaje = e.getMessage();
        if (mensaje == null || mensaje.isEmpty()) {
            mensaje = "Formato Email incorrecto";
        }
        return new ResponseEntity<>(
                new ErrorResponse(mensaje),
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
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
