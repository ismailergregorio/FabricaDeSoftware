package com.example.API_Fabrica_Software.Service.PaginaUserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API_Fabrica_Software.DTO.RetornoDTOpeges.RetornoAlunosDTO;
import com.example.API_Fabrica_Software.DTO.RetornoDTOpeges.RetornoGestorDTO;
import com.example.API_Fabrica_Software.DTO.RetornoDTOpeges.RetornoProjetosDTO;
import com.example.API_Fabrica_Software.Model.ClassGestores;
import com.example.API_Fabrica_Software.Repository.RepositoryGestores;

@Service
public class PaginaUserGestores {
    @Autowired
    RepositoryGestores repositoryGestores;

    public List<RetornoGestorDTO> getGestoresPeges() {
        List<ClassGestores> gestores = repositoryGestores.findAll();

        List<RetornoGestorDTO> dados = gestores.stream()
                .map(gestor -> new RetornoGestorDTO(
                        gestor.getCodigoGestor(),
                        gestor.getName(),

                        gestor.getProjetos().stream()
                                .map(projeto -> new RetornoProjetosDTO(
                                        projeto.getCodigoProjeto(),
                                        projeto.getNomeDoProjeto(),
                                        projeto.getDescricaoDoProjeto(),
                                        projeto.getAreaDeConhecimento(),

                                        projeto.getAlunosParticipantesDoProjeto().stream()
                                                .map(aluno -> new RetornoAlunosDTO(
                                                        aluno.getRa(),
                                                        aluno.getNome(),
                                                        aluno.getEmailInstitucional(),
                                                        aluno.getProjetoSelecionado().getCodigoProjeto(),
                                                        aluno.getCurso()))
                                                .toList(),

                                        projeto.getProfesorOrientador().stream()
                                                .map(g -> g.getCodigoGestor())
                                                .toList(),

                                        projeto.getLinkGit(),
                                        projeto.getLinkImage()))
                                .toList()))
                .toList();

        System.out.println(dados);
        return dados;
    }

    public RetornoGestorDTO getGestorPege(String co) {
        Optional<ClassGestores> gestorSelecionado = repositoryGestores.findByCodigoGestor(co);

        if (gestorSelecionado.isPresent()) {
            ClassGestores gestor = gestorSelecionado.get();
            return new RetornoGestorDTO(
                    gestor.getCodigoGestor(),
                    gestor.getName(),
                    gestor.getProjetos().stream()
                            .map(projeto -> new RetornoProjetosDTO(
                                    projeto.getCodigoProjeto(),
                                    projeto.getNomeDoProjeto(),
                                    projeto.getDescricaoDoProjeto(),
                                    projeto.getAreaDeConhecimento(),

                                    projeto.getAlunosParticipantesDoProjeto().stream()
                                            .map(aluno -> new RetornoAlunosDTO(
                                                    aluno.getRa(),
                                                    aluno.getNome(),
                                                    aluno.getEmailInstitucional(),
                                                    aluno.getProjetoSelecionado().getCodigoProjeto(),
                                                    aluno.getCurso()))
                                            .toList(),

                                    projeto.getProfesorOrientador().stream()
                                            .map(g -> g.getCodigoGestor())
                                            .toList(),

                                    projeto.getLinkGit(),
                                    projeto.getLinkImage()))
                            .toList());
        }
        return null;
    }
}
