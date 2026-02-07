package com.example.API_Fabrica_Software.Service.ProjetoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.API_Fabrica_Software.DTO.ProjetoDTO.dtoProjetoResp;
import com.example.API_Fabrica_Software.Exception.ApiError;
import com.example.API_Fabrica_Software.Model.ClassAlunos;
import com.example.API_Fabrica_Software.Model.ClassGestores;
import com.example.API_Fabrica_Software.Model.ClassProjetos;
import com.example.API_Fabrica_Software.Repository.RepositoryProjetos;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ServiceGetProjeto {
 @Autowired
 RepositoryProjetos repositoryProjetos;

 public ResponseEntity<?> getProjeto(String codigoDoProjeto, HttpServletRequest request) {
  Optional<ClassProjetos> optionalProjeto = repositoryProjetos.findByCodigoProjeto(codigoDoProjeto);

  if (!optionalProjeto.isPresent()) {
   System.out.println("Iten não encontardor");
   return ResponseEntity.status(404).body(
     new ApiError(
       LocalDateTime.now(),
       404,
       "NOT_FOUND",
       "Projeto nao encontrado",
       request.getRequestURI()));
  }

  ClassProjetos projetoSelecionado = optionalProjeto.get();

  dtoProjetoResp res = new dtoProjetoResp();

  List<String> listaAlunos = new ArrayList<>();
  for (ClassAlunos a : projetoSelecionado.getAlunosParticipantesDoProjeto()) {
   listaAlunos.add(a.getRa());
  }

  List<String> listaGestores = new ArrayList<>();
  for (ClassGestores g : projetoSelecionado.getProfesorOrientador()) {
   listaGestores.add(g.getCodigoGestor());
  }

  res.setCodigoProjeto(projetoSelecionado.getCodigoProjeto());
  res.setNomeDoProjeto(projetoSelecionado.getNomeDoProjeto());
  res.setDescricaoDoProjeto(projetoSelecionado.getDescricaoDoProjeto());
  res.setAreaDeConhecimento(projetoSelecionado.getAreaDeConhecimento());
  res.setDataDeInicioDoProjeto(projetoSelecionado.getDataDeInicioDoProjeto());
  res.setDataDoFimDoProjeto(projetoSelecionado.getDataDoFimDoProjeto());
  res.setAlunosParticipantesDoProjeto(listaAlunos);
  res.setProfesorOrientador(listaGestores);
  res.setLinkGit(projetoSelecionado.getLinkGit());
  res.setLinkImage(projetoSelecionado.getLinkImage());

  return ResponseEntity.ok(res);
 }
}
