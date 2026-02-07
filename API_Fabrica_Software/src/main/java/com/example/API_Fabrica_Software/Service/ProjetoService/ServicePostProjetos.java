package com.example.API_Fabrica_Software.Service.ProjetoService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.API_Fabrica_Software.DTO.ProjetoDTO.dtoProjetoPost;
import com.example.API_Fabrica_Software.DTO.ProjetoDTO.dtoProjetoResp;
import com.example.API_Fabrica_Software.Model.ClassAlunos;
import com.example.API_Fabrica_Software.Model.ClassGestores;
import com.example.API_Fabrica_Software.Model.ClassProjetos;
import com.example.API_Fabrica_Software.Repository.RepositoryAlunos;
import com.example.API_Fabrica_Software.Repository.RepositoryGestores;
import com.example.API_Fabrica_Software.Repository.RepositoryProjetos;

@Service
public class ServicePostProjetos {
 @Autowired
 RepositoryGestores repositoryGestores;

 @Autowired
 RepositoryProjetos repositoryProjetos;

 @Autowired
 RepositoryAlunos repositoryAlunos;

 public ResponseEntity<dtoProjetoResp> postProjeto(dtoProjetoPost dto) {

  Set<ClassGestores> gestores = new LinkedHashSet<>();
  List<ClassAlunos> alunos = new ArrayList<>();

  if (dto.getProfesorOrientador() != null) {
   for (String identicadorGestor : dto.getProfesorOrientador()) {
    Optional<ClassGestores> gestor = repositoryGestores.findByCodigoGestor(identicadorGestor);
    if (gestor.isPresent()) {
     gestores.add(gestor.get());
    } else {
     return ResponseEntity.notFound().build();
    }
   }
  }

  if (dto.getAlunosParticipantesDoProjeto() != null) {
   for (String identicadorAlunos : dto.getAlunosParticipantesDoProjeto()) {
    Optional<ClassAlunos> aluno = repositoryAlunos.findByRa(identicadorAlunos);
    if (aluno.isPresent()) {
     alunos.add(aluno.get());
    } else {
     return ResponseEntity.notFound().build();
    }
   }
  }

  ClassProjetos novoProjeto = new ClassProjetos() ;
  novoProjeto.setNomeDoProjeto(dto.getNomeDoProjeto());
  novoProjeto.setDescricaoDoProjeto(dto.getDescricaoDoProjeto());
  novoProjeto.setAreaDeConhecimento(dto.getAreaDeConhecimento());
  novoProjeto.setDataDeInicioDoProjeto(dto.getDataDeInicioDoProjeto());
  novoProjeto.setDataDoFimDoProjeto(dto.getDataDoFimDoProjeto());
  novoProjeto.setLinkGit(dto.getLinkGit());
  novoProjeto.setLinkImage(dto.getLinkImage());
  novoProjeto.setAlunosParticipantesDoProjeto(alunos);
  novoProjeto.setProfesorOrientador(gestores);

  ClassProjetos projetosavo = repositoryProjetos.save(novoProjeto);

  for (String identicadorAlunos : dto.getAlunosParticipantesDoProjeto()) {
   Optional<ClassAlunos> aluno = repositoryAlunos.findByRa(identicadorAlunos);

   ClassAlunos atulizarProjetoAluno = aluno.get();
   atulizarProjetoAluno.setProjetoSelecionado(projetosavo);
   repositoryAlunos.save(atulizarProjetoAluno);

  }

   for (String identicadorGestor : dto.getProfesorOrientador()) {
    Optional<ClassGestores> gestor = repositoryGestores.findByCodigoGestor(identicadorGestor);
    ClassGestores atualizarGestor = gestor.get();
    List<ClassProjetos> listaProjetos = atualizarGestor.getProjetos();
    listaProjetos.add(projetosavo);
    atualizarGestor.setProjetos(listaProjetos);
    repositoryGestores.save(atualizarGestor);

   }

  dtoProjetoResp resProjeto = new dtoProjetoResp();

  resProjeto.setCodigoProjeto(projetosavo.getCodigoProjeto());
  resProjeto.setNomeDoProjeto(projetosavo.getNomeDoProjeto());
  resProjeto.setDescricaoDoProjeto(projetosavo.getDescricaoDoProjeto());
  resProjeto.setAreaDeConhecimento(projetosavo.getAreaDeConhecimento());
  resProjeto.setDataDeInicioDoProjeto(projetosavo.getDataDeInicioDoProjeto());
  resProjeto.setDataDoFimDoProjeto(projetosavo.getDataDoFimDoProjeto());
  resProjeto.setAlunosParticipantesDoProjeto(dto.getAlunosParticipantesDoProjeto());
  resProjeto.setProfesorOrientador(dto.getProfesorOrientador());
  resProjeto.setLinkGit(projetosavo.getLinkGit());
  resProjeto.setLinkImage(projetosavo.getLinkImage());

  return ResponseEntity.ok(resProjeto);
 }
}
