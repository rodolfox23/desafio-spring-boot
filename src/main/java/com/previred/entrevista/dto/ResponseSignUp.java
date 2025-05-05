package com.previred.entrevista.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSignUp {

    private String id;
    private String nombre;
    private String email;
    private String fechaCreacion;
    private String modificado;
    private String ultimoLogin;
    private String token;
    private Boolean estaActivo;
}
