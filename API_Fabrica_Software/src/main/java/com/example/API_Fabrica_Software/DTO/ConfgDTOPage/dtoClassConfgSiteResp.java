package com.example.API_Fabrica_Software.DTO.ConfgDTOPage;

import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassConfigSite;

public class dtoClassConfgSiteResp {
 private String codigoDaConfguracao;
 private String nomeConfig;
 private String valorSalvo;

 public dtoClassConfgSiteResp(String codigoDaConfguracao, String nomeConfig, String valorSalvo) {
  this.codigoDaConfguracao = codigoDaConfguracao;
  this.nomeConfig = nomeConfig;
  this.valorSalvo = valorSalvo;
 }
 public dtoClassConfgSiteResp(ClassConfigSite config) {
  this.codigoDaConfguracao = config.getCodigoDaConfguracao();
  this.nomeConfig = config.getNomeConfig();
  this.valorSalvo = config.getValorSalvo();
 }

 public dtoClassConfgSiteResp() {

 }

 public String getCodigoDaConfguracao() {
  return codigoDaConfguracao;
 }
 public void setCodigoDaConfguracao(String codigoDaConfguracao) {
  this.codigoDaConfguracao = codigoDaConfguracao;
 }
 public String getNomeConfig() {
  return nomeConfig;
 }
 public void setNomeConfig(String nomeConfig) {
  this.nomeConfig = nomeConfig;
 }
 public String getValorSalvo() {
  return valorSalvo;
 }
 public void setValorSalvo(String valorSalvo) {
  this.valorSalvo = valorSalvo;
 }
 
}
