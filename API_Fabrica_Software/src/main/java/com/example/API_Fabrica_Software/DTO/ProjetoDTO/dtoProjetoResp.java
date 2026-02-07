package com.example.API_Fabrica_Software.DTO.ProjetoDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.API_Fabrica_Software.Model.ClassAlunos;
import com.example.API_Fabrica_Software.Model.ClassGestores;
import com.example.API_Fabrica_Software.Model.ClassProjetos;

public class dtoProjetoResp {
  private String codigoProjeto;
  private String nomeDoProjeto;
  private String descricaoDoProjeto;
  private String areaDeConhecimento;
  private LocalDate dataDeInicioDoProjeto;
  private LocalDate dataDoFimDoProjeto;
  private List<String> alunosParticipantesDoProjeto;
  private List<String> profesorOrientador;
  private String linkGit;
  private String linkImage;

  public dtoProjetoResp() {

  }

  public dtoProjetoResp(ClassProjetos projetos) {
    this.codigoProjeto = projetos.getCodigoProjeto();
    this.nomeDoProjeto = projetos.getNomeDoProjeto();
    this.descricaoDoProjeto = projetos.getDescricaoDoProjeto();
    this.areaDeConhecimento = projetos.getAreaDeConhecimento();
    this.dataDeInicioDoProjeto = projetos.getDataDeInicioDoProjeto();
    this.dataDoFimDoProjeto = projetos.getDataDoFimDoProjeto();
    if (projetos.getAlunosParticipantesDoProjeto() != null) {
      this.alunosParticipantesDoProjeto = projetos.getAlunosParticipantesDoProjeto().stream()
          .map(ClassAlunos::getRa)
          .collect(Collectors.toList());
    } else {
      this.alunosParticipantesDoProjeto = new ArrayList<>();
    }

    if (projetos.getProfesorOrientador() != null) {
      this.profesorOrientador = projetos.getProfesorOrientador().stream()
          .map(ClassGestores::getCodigoGestor) // ou getNome(), dependendo do que você quer retornar
          .collect(Collectors.toList());
    } else {
      this.profesorOrientador = new ArrayList<>();
    }
    this.linkGit = projetos.getLinkGit();
    this.linkImage = projetos.getLinkImage();

  }

  public dtoProjetoResp(String codigoProjeto, String nomeDoProjeto, String descricaoDoProjeto,
      String areaDeConhecimento, LocalDate dataDeInicioDoProjeto, LocalDate dataDoFimDoProjeto,
      List<String> alunosParticipantesDoProjeto, List<String> profesorOrientador, String linkGit,
      String linkImage) {

    this.codigoProjeto = codigoProjeto;
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

  public String getCodigoProjeto() {
    return codigoProjeto;
  }

  public void setCodigoProjeto(String codigoProjeto) {
    this.codigoProjeto = codigoProjeto;
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
