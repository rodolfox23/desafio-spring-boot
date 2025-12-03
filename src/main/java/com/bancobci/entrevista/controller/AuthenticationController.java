package com.bancobci.entrevista.controller;


import com.bancobci.entrevista.dto.ErrorResponse;
import com.bancobci.entrevista.dto.LoginRequest;
import com.bancobci.entrevista.dto.ResponseSignUp;
import com.bancobci.entrevista.dto.UsuarioDto;
import com.bancobci.entrevista.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UsuarioDto usuarioDTO){
        var responseSignUp = authenticationService.signUpUsuario(usuarioDTO);
        if (responseSignUp != null)
            return new ResponseEntity<>(responseSignUp, HttpStatus.CREATED);
        return new ResponseEntity<>(new ErrorResponse(
                "Error al procesar la solicitud"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            var loginResponse = authenticationService.logInUsusario(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(
                    "Credenciales inválidas"),
                    HttpStatus.UNAUTHORIZED
            );
        }

    }
}
