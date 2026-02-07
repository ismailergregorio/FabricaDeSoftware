package com.example.API_Fabrica_Software.DTO.ProjetoDTO;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class dtoProjetoPost {
  @NotBlank(message = "Nome do projeto é obrigatório")
  private String nomeDoProjeto;

  @NotBlank(message = "Descrição do projeto é obrigatória")
  @Size(min = 10, message = "A descrição deve ter no mínimo 10 caracteres")
  private String descricaoDoProjeto;

  @NotBlank(message = "Área de conhecimento é obrigatória")
  private String areaDeConhecimento;

  @NotNull(message = "Data de início é obrigatória")
  private LocalDate dataDeInicioDoProjeto;

  @NotNull(message = "Data de fim é obrigatória")
  private LocalDate dataDoFimDoProjeto;

  private List<String> alunosParticipantesDoProjeto;

  private List<String> profesorOrientador;
  private String linkGit;
  @NotNull(message = "Imagen e Obrigatoria")
  private String linkImage;

  public dtoProjetoPost(String nomeDoProjeto, String descricaoDoProjeto, String areaDeConhecimento,
      LocalDate dataDeInicioDoProjeto, LocalDate dataDoFimDoProjeto, List<String> alunosParticipantesDoProjeto,
      List<String> profesorOrientador, String linkGit, String linkImage) {
    this.nomeDoProjeto = nomeDoProjeto;
    this.descricaoDoProjeto = descricaoDoProjeto;
    this.areaDeConhecimento = areaDeConhecimento;
    this.dataDeInicioDoProjeto = dataDeInicioDoProjeto;
    this.dataDoFimDoProjeto = dataDoFimDoProjeto;
    this.alunosParticipantesDoProjeto = alunosParticipantesDoProjeto;
    this.profesorOrientador = profesorOrientador;
    this.linkGit = linkGit;
    this.linkImage = linkImage;
  }

  public String getNomeDoProjeto() {
    return nomeDoProjeto;
  }

  public void setNomeDoProjeto(String nomeDoProjeto) {
    this.nomeDoProjeto = nomeDoProjeto;
  }

  public String getDescricaoDoProjeto() {
    return descricaoDoProjeto;
  }

  public void setDescricaoDoProjeto(String descricaoDoProjeto) {
    this.descricaoDoProjeto = descricaoDoProjeto;
  }

  public String getAreaDeConhecimento() {
    return areaDeConhecimento;
  }

  public void setAreaDeConhecimento(String areaDeConhecimento) {
    this.areaDeConhecimento = areaDeConhecimento;
  }

  public LocalDate getDataDeInicioDoProjeto() {
    return dataDeInicioDoProjeto;
  }

  public void setDataDeInicioDoProjeto(LocalDate dataDeInicioDoProjeto) {
    this.dataDeInicioDoProjeto = dataDeInicioDoProjeto;
  }

  public LocalDate getDataDoFimDoProjeto() {
    return dataDoFimDoProjeto;
  }

  public void setDataDoFimDoProjeto(LocalDate dataDoFimDoProjeto) {
    this.dataDoFimDoProjeto = dataDoFimDoProjeto;
  }

  public List<String> getAlunosParticipantesDoProjeto() {
    return alunosParticipantesDoProjeto;
  }

  public void setAlunosParticipantesDoProjeto(List<String> alunosParticipantesDoProjeto) {
    this.alunosParticipantesDoProjeto = alunosParticipantesDoProjeto;
  }

  public List<String> getProfesorOrientador() {
    return profesorOrientador;
  }

  public void setProfesorOrientador(List<String> profesorOrientador) {
    this.profesorOrientador = profesorOrientador;
  }

  public String getLinkGit() {
    return linkGit;
  }

  public void setLinkGit(String linkGit) {
    this.linkGit = linkGit;
  }

  public String getLinkImage() {
    return linkImage;
  }

  public void setLinkImage(String linkImage) {
    this.linkImage = linkImage;
  }

}
