package com.example.API_Fabrica_Software.DTO.UsersDTO;
import com.example.API_Fabrica_Software.Enun.NivelUsuario;

public record criarUsuarioDTO(String nome,String login, String senha,NivelUsuario roles) {
}
