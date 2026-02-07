package com.example.API_Fabrica_Software.Enun;

import lombok.Getter;

@Getter
public enum NivelUsuario {

    ADMIN("admin"),
    USER_N1("usuario nivel 1"),
    USER_N2("usuario nivel 2"),
    USER("user");

    private final String descricao;

    NivelUsuario(String descricao) {
        this.descricao = descricao;
    }
    
}
