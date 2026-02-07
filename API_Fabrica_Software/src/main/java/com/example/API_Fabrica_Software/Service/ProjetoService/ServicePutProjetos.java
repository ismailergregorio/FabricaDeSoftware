package com.example.API_Fabrica_Software.Service.ProjetoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.API_Fabrica_Software.DTO.ProjetoDTO.dtoProjetoAtulizacaoInfomacao;
import com.example.API_Fabrica_Software.Model.ClassAlunos;
import com.example.API_Fabrica_Software.Model.ClassGestores;
import com.example.API_Fabrica_Software.Model.ClassProjetos;
import com.example.API_Fabrica_Software.Repository.RepositoryAlunos;
import com.example.API_Fabrica_Software.Repository.RepositoryGestores;
import com.example.API_Fabrica_Software.Repository.RepositoryProjetos;

@Service
public class ServicePutProjetos {
  @Autowired
  RepositoryProjetos repositoryProjetos;

  @Autowired
  RepositoryAlunos repositoryAlunos;

  @Autowired
  RepositoryGestores repositoryGestores;

  public ResponseEntity<dtoProjetoAtulizacaoInfomacao> putProjeto(String identificador,
      dtoProjetoAtulizacaoInfomacao objDto) {

    Optional<ClassProjetos> projeto = repositoryProjetos.findByCodigoProjeto(identificador);

    ClassProjetos listaAlunosEgestores = projeto.get();

    List<String> raS = new ArrayList<>();
    List<String> codigosGestores = new ArrayList<>();

    List<ClassAlunos> alunos = new ArrayList<>();

    if (objDto.getAlunosParticipantesDoProjeto() != null) {
      // Apaga o projeto existente na entidade aluno para inserir um novo
      for (ClassAlunos projetoDoAluno : listaAlunosEgestores.getAlunosParticipantesDoProjeto()) {
        projetoDoAluno.setProjetoSelecionado(null);
        repositoryAlunos.save(projetoDoAluno);
      }

      for (String ra : objDto.getAlunosParticipantesDoProjeto()) {
        Optional<ClassAlunos> alunoExiste = repositoryAlunos.findByRa(ra);

        if (alunoExiste.isPresent()) {
          ClassAlunos projetoAluno = alunoExiste.get();
          projetoAluno.setProjetoSelecionado(listaAlunosEgestores);
          // repositoryAlunos.save(projetoAluno);
          raS.add(projetoAluno.getRa());
          alunos.add(projetoAluno);
        } else {
          return ResponseEntity.badRequest().build();
        }
      }
    }

    if (listaAlunosEgestores.getProfesorOrientador() != null) {
      for (ClassGestores gestorAntigo : listaAlunosEgestores.getProfesorOrientador()) {
        gestorAntigo.getProjetos().remove(listaAlunosEgestores); // remove o projeto do gestor
        repositoryGestores.save(gestorAntigo);
      }
    }

    listaAlunosEgestores.setProfesorOrientador(null);
    repositoryProjetos.save(listaAlunosEgestores);

    if (objDto.getProfesorOrientador() != null) {
      dtoProjetoAtulizacaoInfomacao dtoResposModificado = new dtoProjetoAtulizacaoInfomacao();

      dtoResposModificado.setProfesorOrientador(null);
      dtoResposModificado.setAlunosParticipantesDoProjeto(null);

      for (String gestor : objDto.getProfesorOrientador()) {
        Optional<ClassGestores> gestorSelecionado = repositoryGestores.findByCodigoGestor(gestor);

        if (!gestorSelecionado.isPresent()) {
          return ResponseEntity.badRequest().build();
        }
        ClassGestores gestorParaAtulisar = gestorSelecionado.get();

        List<ClassProjetos> novosGestores = new ArrayList<>();

        novosGestores.add(listaAlunosEgestores);
        codigosGestores.add(gestor);

        gestorParaAtulisar.setProjetos(novosGestores);

        repositoryGestores.save(gestorParaAtulisar);
      }

    }

    dtoProjetoAtulizacaoInfomacao respostaDTO = new dtoProjetoAtulizacaoInfomacao();

    listaAlunosEgestores.setNomeDoProjeto(objDto.getNomeDoProjeto());
    listaAlunosEgestores.setDescricaoDoProjeto(objDto.getDescricaoDoProjeto());
    listaAlunosEgestores.setAreaDeConhecimento(objDto.getAreaDeConhecimento());
    listaAlunosEgestores.setDataDeInicioDoProjeto(objDto.getDataDeInicioDoProjeto());
    listaAlunosEgestores.setDataDoFimDoProjeto(objDto.getDataDoFimDoProjeto());
    listaAlunosEgestores.setLinkGit(objDto.getLinkGit());
    listaAlunosEgestores.setLinkImage(objDto.getLinkImage());

    repositoryProjetos.save(listaAlunosEgestores);

    respostaDTO.setNomeDoProjeto(listaAlunosEgestores.getNomeDoProjeto());
    respostaDTO.setDescricaoDoProjeto(listaAlunosEgestores.getDescricaoDoProjeto());
    respostaDTO.setAreaDeConhecimento(listaAlunosEgestores.getAreaDeConhecimento());
    respostaDTO.setDataDeInicioDoProjeto(listaAlunosEgestores.getDataDeInicioDoProjeto());
    respostaDTO.setDataDoFimDoProjeto(listaAlunosEgestores.getDataDoFimDoProjeto());
    respostaDTO.setAlunosParticipantesDoProjeto(raS);
    respostaDTO.setProfesorOrientador(codigosGestores);
    respostaDTO.setLinkGit(listaAlunosEgestores.getLinkGit());
    respostaDTO.setLinkImage(listaAlunosEgestores.getLinkImage());

    return ResponseEntity.ok(respostaDTO);
  }
}
