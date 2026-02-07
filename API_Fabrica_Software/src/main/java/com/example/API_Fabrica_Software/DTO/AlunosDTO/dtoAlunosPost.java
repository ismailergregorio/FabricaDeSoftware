package com.example.API_Fabrica_Software.DTO.AlunosDTO;

import java.time.LocalDate;

//CLASSE DE CRIAÇÃO DE DTO DE ALUNOS PARA POSTAR INFOMAÇOES PARA OS USUARIOS

public class dtoAlunosPost {
 private String ra;
 private String emailInstitucional;
 private String nome;
 private String curso;
 private String projetoSelecionado;
 private String motivoDaInscricao;
 private LocalDate dataInscricao;

 public dtoAlunosPost(){
  
 }

 public dtoAlunosPost(String ra, String emailInstitucional, String nome, String curso, String projetoSelecionado,
   String motivoDaInscricao, LocalDate dataInscricao) {

  this.ra = ra;
  this.emailInstitucional = emailInstitucional;
  this.nome = nome;
  this.curso = curso;
  this.projetoSelecionado = projetoSelecionado;
  this.motivoDaInscricao = motivoDaInscricao;
  this.dataInscricao = dataInscricao;
 }

 public String getRa() {
  return ra;
 }

 public void setRa(String ra) {
  this.ra = ra;
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
