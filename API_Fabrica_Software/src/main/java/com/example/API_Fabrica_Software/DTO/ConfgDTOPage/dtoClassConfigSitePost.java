package com.example.API_Fabrica_Software.DTO.ConfgDTOPage;

import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassConfigSite;

public class dtoClassConfigSitePost {
 private String nomeConfig;
 private String valorSalvo;
 public dtoClassConfigSitePost(String nomeConfig, String valorSalvo) {
  this.nomeConfig = nomeConfig;
  this.valorSalvo = valorSalvo;
 }
 public dtoClassConfigSitePost(){

 }

 public dtoClassConfigSitePost(ClassConfigSite config){
  this.nomeConfig = config.getNomeConfig();
  this.valorSalvo = config.getValorSalvo();
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
