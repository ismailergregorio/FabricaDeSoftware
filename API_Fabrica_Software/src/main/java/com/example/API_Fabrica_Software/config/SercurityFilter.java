package com.example.API_Fabrica_Software.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.API_Fabrica_Software.Model.ClassUsuario;
import com.example.API_Fabrica_Software.Repository.RepositoryUsuario;
import com.example.API_Fabrica_Software.Service.UsuarioService.AuthenticacaoServises;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SercurityFilter extends OncePerRequestFilter {
 @Autowired
 AuthenticacaoServises authenticacaoServises;

 @Autowired
 RepositoryUsuario repositoryUsuario;

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
   throws ServletException, IOException {

  String token = extrairToker(request);

  if (token != null) {
   String login = authenticacaoServises.validaToken(token);
   ClassUsuario user =  repositoryUsuario.findByLogin(login);
   
   var authenticated = new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());

   SecurityContextHolder.getContext().setAuthentication(authenticated);
  }

  filterChain.doFilter(request, response);
 }

 public String extrairToker(HttpServletRequest request) {
  var authHeder = request.getHeader("Authorization");

  if (authHeder == null) {
   return null;
  }
  if (!authHeder.split(" ")[0].equals("Bearer")) {
   return null;
  }
  return authHeder.split(" ")[1];
 }

}
