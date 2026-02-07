package com.example.API_Fabrica_Software.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Fabrica_Software.DTO.AuthDTO.ResponseAuthDTO;
import com.example.API_Fabrica_Software.DTO.AuthDTO.authDTO;
import com.example.API_Fabrica_Software.DTO.AuthDTO.authRefrashToker;
import com.example.API_Fabrica_Software.Service.UsuarioService.implementacao.AuthenticacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints responsáveis por autenticação e geração/atualização de token JWT.")
public class ControllersAuth {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticacaoService authenticacaoServices;

    @PostMapping
    @Operation(
        summary = "Realizar login",
        description = "Autentica o usuário com login e senha e retorna um token JWT válido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Login ou senha inválidos")
    })
    public ResponseAuthDTO auth(@RequestBody authDTO entity) {

        System.out.println(entity.login() + entity.senha());

        var usuarioAutenticadoComToken =
                new UsernamePasswordAuthenticationToken(entity.login(), entity.senha());

        authenticationManager.authenticate(usuarioAutenticadoComToken);

        return authenticacaoServices.obtetToken(entity);
    }

    @PostMapping("/refresh")
    @Operation(
        summary = "Atualizar token (Refresh Token)",
        description = "Gera um novo token JWT a partir de um refresh token válido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Refresh token inválido"),
        @ApiResponse(responseCode = "401", description = "Refresh token expirado ou não autorizado")
    })
    public ResponseAuthDTO authRefreshToker(@RequestBody authRefrashToker refresh) {
        return authenticacaoServices.obtetTokenRefreshToker(refresh.refresh());
    }

}
