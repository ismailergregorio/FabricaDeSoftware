package com.example.API_Fabrica_Software.DTO.UsersDTO;

import com.example.API_Fabrica_Software.Model.ClassUsuario;

public record respostaUsuarioSenhaDTO(String senha) {
 public respostaUsuarioSenhaDTO(ClassUsuario usuario) {
  this(usuario.getPassword());
 }
}
