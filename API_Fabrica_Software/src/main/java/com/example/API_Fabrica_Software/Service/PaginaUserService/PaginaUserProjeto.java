package com.example.API_Fabrica_Software.Service.PaginaUserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API_Fabrica_Software.DTO.RetornoDTOpeges.RetornoAlunosDTO;
import com.example.API_Fabrica_Software.DTO.RetornoDTOpeges.RetornoProjetosDTO;
import com.example.API_Fabrica_Software.Model.ClassProjetos;
import com.example.API_Fabrica_Software.Repository.RepositoryProjetos;

@Service
public class PaginaUserProjeto {

    @Autowired
    RepositoryProjetos repositoryProjetos;

    public List<RetornoProjetosDTO> getProjetos() {
        List<ClassProjetos> projetos = repositoryProjetos.findAll();

        List<RetornoProjetosDTO> dados = projetos.stream().map(projeto -> new RetornoProjetosDTO(
                projeto.getCodigoProjeto(),
                projeto.getNomeDoProjeto(),
                projeto.getDescricaoDoProjeto(),
                projeto.getAreaDeConhecimento(),
                projeto.getAlunosParticipantesDoProjeto().stream().map(aluno -> new RetornoAlunosDTO(
                        aluno.getRa(),
                        aluno.getNome(),
                        aluno.getEmailInstitucional(),
                        aluno.getProjetoSelecionado().getCodigoProjeto(),
                        aluno.getCurso())).toList(),
                projeto.getProfesorOrientador().stream().map(gestor -> gestor.getCodigoGestor()).toList(),
                projeto.getLinkGit(),
                projeto.getLinkImage())).toList();

        return dados;
    }

    public RetornoProjetosDTO getProjeto(String codigoProjeto) {
        Optional<ClassProjetos> projetoSelecionado = repositoryProjetos.findByCodigoProjeto(codigoProjeto);

        if (projetoSelecionado.isPresent()) {
            ClassProjetos projeto = projetoSelecionado.get();
            return new RetornoProjetosDTO(
                    projeto.getCodigoProjeto(),
                    projeto.getNomeDoProjeto(),
                    projeto.getDescricaoDoProjeto(),
                    projeto.getAreaDeConhecimento(),
                    projeto.getAlunosParticipantesDoProjeto().stream().map(aluno -> new RetornoAlunosDTO(
                            aluno.getRa(),
                            aluno.getNome(),
                            aluno.getEmailInstitucional(),
                            aluno.getProjetoSelecionado().getCodigoProjeto(),
                            aluno.getCurso())).toList(),
                    projeto.getProfesorOrientador().stream().map(gestor -> gestor.getCodigoGestor()).toList(),
                    projeto.getLinkGit(),
                    projeto.getLinkImage());
        }
        return null;
    }
}
