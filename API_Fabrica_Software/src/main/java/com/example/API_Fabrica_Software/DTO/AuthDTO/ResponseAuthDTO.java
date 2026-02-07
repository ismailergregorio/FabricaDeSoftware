package com.example.API_Fabrica_Software.DTO.AuthDTO;

import com.example.API_Fabrica_Software.Enun.NivelUsuario;

import lombok.Builder;

@Builder
public record ResponseAuthDTO(String toker, String refreshToker, String nome, NivelUsuario roles) {
 
}
