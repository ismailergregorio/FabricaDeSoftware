package com.example.API_Fabrica_Software.DTO.AlunosDTO;

import java.time.LocalDate;

public class dtoAlunoAtulizarInfomacao {
 private String emailInstitucional;
 private String nome;
 private String curso;
 private String projetoSelecionado;
 private String motivoDaInscricao;
 private LocalDate dataInscricao;
 private boolean status;

 public boolean isStatus() {
  return status;
}

 public void setStatus(boolean status) {
  this.status = status;
 }

 public dtoAlunoAtulizarInfomacao(){
  
 }

 public dtoAlunoAtulizarInfomacao(String emailInstitucional, String nome, String curso, String projetoSelecionado,
   String motivoDaInscricao, LocalDate dataInscricao, boolean status) {

  this.emailInstitucional = emailInstitucional;
  this.nome = nome;
  this.curso = curso;
  this.projetoSelecionado = projetoSelecionado;
  this.motivoDaInscricao = motivoDaInscricao;
  this.dataInscricao = dataInscricao;
  this.status = status;
 }

 public String getEmailInstitucional() {
  return emailInstitucional;
 }

 public void setEmailInstitucional(String emailInstitucional) {
  this.emailInstitucional = emailInstitucional;
 }

 public String getNome() {
  return nome;
 }

 public void setNome(String nome) {
  this.nome = nome;
 }

 public String getCurso() {
  return curso;
 }

 public void setCurso(String curso) {
  this.curso = curso;
 }

 public String getProjetoSelecionado() {
  return projetoSelecionado;
 }

 public void setProjetoSelecionado(String projetoSelecionado) {
  this.projetoSelecionado = projetoSelecionado;
 }

 public String getMotivoDaInscricao() {
  return motivoDaInscricao;
 }

 public void setMotivoDaInscricao(String motivoDaInscricao) {
  this.motivoDaInscricao = motivoDaInscricao;
 }

 public LocalDate getDataInscricao() {
  return dataInscricao;
 }

 public void setDataInscricao(LocalDate dataInscricao) {
  this.dataInscricao = dataInscricao;
 }
}
