package com.example.API_Fabrica_Software.Service.ProjetoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API_Fabrica_Software.DTO.ProjetoDTO.dtoProjetoResp;
import com.example.API_Fabrica_Software.Model.ClassProjetos;
import com.example.API_Fabrica_Software.Repository.RepositoryProjetos;

@Service
public class ServiceGetProjetos {
 @Autowired
 RepositoryProjetos repositoryProjetos;

 public List<dtoProjetoResp> getProjetos(){
  List<ClassProjetos> projetos = repositoryProjetos.findAll();
  return projetos.stream().map(dtoProjetoResp::new).toList();
 }
}
