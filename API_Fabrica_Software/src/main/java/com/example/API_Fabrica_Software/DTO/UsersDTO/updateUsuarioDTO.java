package com.example.API_Fabrica_Software.DTO.UsersDTO;

import com.example.API_Fabrica_Software.Enun.NivelUsuario;

public record updateUsuarioDTO(String nome, String login, String senhaAtual, String novaSenha, NivelUsuario roles) {

}
