package com.previred.entrevista.service;

import com.previred.entrevista.dao.UsuarioRepository;
import com.previred.entrevista.dto.UsuarioDto;
import com.previred.entrevista.exceptions.InvalidEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private AuthenticationService authenticationService;
    private UsuarioDto usuarioDto;

    @BeforeEach
    void setUp() {
        usuarioDto = new UsuarioDto("Test User", "test@example.com", "password", new ArrayList<>());
    }

    @Test
    void testSignUpUsuario_InvalidEmailException() {
        usuarioDto.setEmail("invalidemail.com");

        assertThrows(InvalidEmailException.class, () -> authenticationService.signUpUsuario(usuarioDto));
    }

    @Test
    void testGetUsuarioById_NotFound() {
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        UsuarioDto result = authenticationService.getUsuarioById(id);

        assertNull(result);
    }
}
