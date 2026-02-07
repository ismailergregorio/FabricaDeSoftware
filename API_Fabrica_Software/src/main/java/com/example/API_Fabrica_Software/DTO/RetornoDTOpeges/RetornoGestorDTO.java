package com.example.API_Fabrica_Software.DTO.RetornoDTOpeges;

import java.util.List;

public record RetornoGestorDTO(String codigoGestor, String name,
  List<RetornoProjetosDTO> projetos) {

}
