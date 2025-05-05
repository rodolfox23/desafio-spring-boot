package com.previred.entrevista.controller;

import com.previred.entrevista.dto.UsuarioDto;
import com.previred.entrevista.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private Logger log;

    @Test
    void testGetAllUsuarios() {
        List<UsuarioDto> usuarios = new ArrayList<>();
        usuarios.add(new UsuarioDto("nombre1", "email1@example.com", "password1", null));
        usuarios.add(new UsuarioDto("nombre2", "email2@example.com", "password2", null));

        when(authenticationService.getAllUsuarios()).thenReturn(usuarios);

        ResponseEntity<List<UsuarioDto>> response = usuarioController.getAllUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarios, response.getBody());
    }

    @Test
    void testGetUsuarioById_Found() {
        Long id = 1L;
        UsuarioDto usuario = new UsuarioDto("nombre", "email@example.com", "password", null);

        when(authenticationService.getUsuarioById(id)).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.getUsuarioById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void testGetUsuarioById_NotFound() {
        Long id = 1L;

        when(authenticationService.getUsuarioById(id)).thenReturn(null);

        ResponseEntity<?> response = usuarioController.getUsuarioById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdate_Found() {
        Long id = 1L;
        UsuarioDto usuarioDto = new UsuarioDto("nombre", "email@example.com", "password", null);
        UsuarioDto updatedUsuarioDto = new UsuarioDto("nombreActualizado", "emailActualizado@example.com", "passwordActualizado", null);

        when(authenticationService.updateUsuario(id, usuarioDto)).thenReturn(updatedUsuarioDto);

        ResponseEntity<UsuarioDto> response = usuarioController.update(id, usuarioDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUsuarioDto, response.getBody());
    }

    @Test
    void testUpdate_NotFound() {
        Long id = 1L;
        UsuarioDto usuarioDto = new UsuarioDto("nombre", "email@example.com", "password", null);

        when(authenticationService.updateUsuario(id, usuarioDto)).thenReturn(null);

        ResponseEntity<UsuarioDto> response = usuarioController.update(id, usuarioDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDelete() {
        Long id = 1L;

        ResponseEntity<Void> response = usuarioController.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(authenticationService, times(1)).deleteUsuario(id);
    }
}
