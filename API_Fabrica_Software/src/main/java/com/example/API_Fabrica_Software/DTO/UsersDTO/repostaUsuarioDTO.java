package com.example.API_Fabrica_Software.DTO.UsersDTO;

import java.time.LocalDateTime;

import com.example.API_Fabrica_Software.Enun.NivelUsuario;
import com.example.API_Fabrica_Software.Model.ClassUsuario;

public record repostaUsuarioDTO(Long id,String usuario, String login, NivelUsuario role,LocalDateTime dataDeCriacao, LocalDateTime dataDeAtulizacao) {

 public repostaUsuarioDTO(ClassUsuario u) {
  this(u.getId(),u.getNome(),u.getLogin(), u.getRoles(),u.getDataDeCriacao(),u.getDataDeAtulizacao());
 }

}
