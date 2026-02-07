package com.example.API_Fabrica_Software.Service.UsuarioService;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.API_Fabrica_Software.DTO.AuthDTO.ResponseAuthDTO;
import com.example.API_Fabrica_Software.DTO.AuthDTO.authDTO;

public interface AuthenticacaoServises extends UserDetailsService {
    public ResponseAuthDTO obtetToken(authDTO ayAuthDTO);

    public String validaToken(String token);
}
