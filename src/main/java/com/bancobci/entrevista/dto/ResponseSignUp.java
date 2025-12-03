package com.bancobci.entrevista.dto;

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
    private String created;
    private String modified;
    private String last_login;
    private String token;
    private Boolean isactive;
}
