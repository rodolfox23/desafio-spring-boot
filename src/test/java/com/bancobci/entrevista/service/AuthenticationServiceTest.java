package com.bancobci.entrevista.service;

import com.bancobci.entrevista.dao.EstadoTareaRepository;
import com.bancobci.entrevista.dao.TokenRepository;
import com.bancobci.entrevista.dao.UsuarioRepository;
import com.bancobci.entrevista.dto.UsuarioDto;
import com.bancobci.entrevista.entity.usuario.Usuario;
import com.bancobci.entrevista.exceptions.InvalidEmailException;
import com.bancobci.entrevista.exceptions.PasswordPatternException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private EstadoTareaRepository estadoTareaRepository;
    @InjectMocks
    private AuthenticationService authenticationService;
    private UsuarioDto usuarioDto;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authenticationService, "passwordRegex", "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$");
        usuarioDto = UsuarioDto.builder()
                .nombre("Test User")
                .email("test@example.com")
                .password("password")
                .phones(new ArrayList<>())
                .build();
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

    @Test
    void testSignUpUsuario_InvalidPassword() {
        usuarioDto.setPassword("weak");

        assertThrows(PasswordPatternException.class, () -> authenticationService.signUpUsuario(usuarioDto));
    }

    @Test
    void testSignUpUsuario_EmailDuplicado() {
        usuarioDto.setPassword("Password123");
        when(usuarioRepository.findByEmail(usuarioDto.getEmail())).thenReturn(Optional.of(new Usuario()));

        assertThrows(InvalidEmailException.class, () -> authenticationService.signUpUsuario(usuarioDto));
    }
}
