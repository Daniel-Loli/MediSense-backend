package com.medisense.medisense_back.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medisense.medisense_back.dto.usuario.UsuarioAllResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse{
    private String token;
    private UsuarioAllResponse usuario;
    private EspecialistaAuthResponse especialista;
}
