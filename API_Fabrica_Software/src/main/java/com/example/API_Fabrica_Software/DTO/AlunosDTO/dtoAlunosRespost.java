package com.example.API_Fabrica_Software.DTO.AlunosDTO;


import com.example.API_Fabrica_Software.Model.ClassAlunos;
// import com.example.API_Fabrica_Software.Model.ClassProjetos;

//CLASSE DE CRIAÇÃO DE DTO DE ALUNOS PARA RESPOSTA PARA OS USUARIOS 

public class dtoAlunosRespost {
 private String ra;
 private String emailInstitucional;
 private String nome;
 private String curso;
 private String projetoSelecionado;
 private String motivoDaInscricao;
 private boolean status;

 public boolean isStatus() {
  return status;
}

 public void setStatus(boolean status) {
  this.status = status;
 }

 public dtoAlunosRespost(){

 }

 public dtoAlunosRespost(ClassAlunos classAlunos) {
  this.ra = classAlunos.getRa();
  this.emailInstitucional = classAlunos.getEmailInstitucional();
  this.nome = classAlunos.getNome();
  this.curso = classAlunos.getCurso();
  if (classAlunos.getProjetoSelecionado() != null) {
  this.projetoSelecionado = classAlunos.getProjetoSelecionado().getCodigoProjeto();}
  else{
    this.projetoSelecionado = null;
  }
  this.motivoDaInscricao = classAlunos.getMotivoDaInscricao();
  this.status = classAlunos.isStatus();
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
}
