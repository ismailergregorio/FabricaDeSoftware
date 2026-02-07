package com.example.API_Fabrica_Software.Service.UsuarioService.implementacao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.API_Fabrica_Software.DTO.AuthDTO.ResponseAuthDTO;
import com.example.API_Fabrica_Software.DTO.AuthDTO.authDTO;
import com.example.API_Fabrica_Software.Model.ClassUsuario;
import com.example.API_Fabrica_Software.Repository.RepositoryUsuario;
import com.example.API_Fabrica_Software.Service.UsuarioService.AuthenticacaoServises;

@Service
public class AuthenticacaoService implements AuthenticacaoServises {

    @Autowired
    RepositoryUsuario repositoryUsuario;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repositoryUsuario.findByLogin(login);
    }
    Integer horaExpiracaoToken = 1;
    Integer horaExpiracaoRefreshToken = 5;

    @Override
    public ResponseAuthDTO obtetToken(authDTO ayAuthDTO) {

        ClassUsuario usuario = repositoryUsuario.findByLogin(ayAuthDTO.login());
        return ResponseAuthDTO
                .builder()
                .nome(usuario.getNome())
                .roles(usuario.getRoles())
                .toker(geraToken(usuario, horaExpiracaoToken))
                .refreshToker(geraToken(usuario, horaExpiracaoRefreshToken))
                .build();
    }

    public String geraToken(ClassUsuario usuario, Integer expiracao) {

        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            return JWT.create().withIssuer("auth-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(geraDataExpiracao(expiracao))
                    .sign(algorithm);
        } catch (Exception e) {
            return "Erro ao gerar token";
        }
    }

    public String validaToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return "token invalido";
        }
    }

    public ResponseAuthDTO obtetTokenRefreshToker(String refreshToker) {
        String login = validaToken(refreshToker);
        ClassUsuario user = repositoryUsuario.findByLogin(login);
        
        if (user == null) {
            System.out.println("Erro na Altenticaçã do Toker");
        }

        var authenticated = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticated);

        return ResponseAuthDTO
                .builder()
                .toker(geraToken(user, horaExpiracaoToken))
                .refreshToker(geraToken(user, horaExpiracaoRefreshToken))
                .build();
    }

    private Instant geraDataExpiracao(Integer expiracao) {
        return LocalDateTime.now().plusHours(expiracao).toInstant(ZoneOffset.of("-03:00"));
    }

}
