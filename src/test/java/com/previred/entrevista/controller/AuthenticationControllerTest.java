package com.previred.entrevista.controller;

import com.previred.entrevista.dto.ResponseSignUp;
import com.previred.entrevista.dto.UsuarioDto;
import com.previred.entrevista.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    @Test
    void testSignUp_Successful() {
        UsuarioDto usuarioDTO = new UsuarioDto();
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        ResponseSignUp responseSignUp = new ResponseSignUp();
        when(authenticationService.signUpUsuario(usuarioDTO)).thenReturn(responseSignUp);

        AuthenticationController authenticationController = new AuthenticationController(authenticationService);
        ResponseEntity<?> response = authenticationController.signUp(usuarioDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseSignUp, response.getBody());
    }

    @Test
    void testSignUp_NullResponseSignUp() {
        UsuarioDto usuarioDTO = new UsuarioDto();
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        when(authenticationService.signUpUsuario(usuarioDTO)).thenReturn(null);

        AuthenticationController authenticationController = new AuthenticationController(authenticationService);
        ResponseEntity<?> response = authenticationController.signUp(usuarioDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
