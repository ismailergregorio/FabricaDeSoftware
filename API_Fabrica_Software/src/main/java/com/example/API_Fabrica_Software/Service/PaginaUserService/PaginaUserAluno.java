package com.example.API_Fabrica_Software.Service.PaginaUserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.API_Fabrica_Software.DTO.RetornoDTOpeges.RetornoAlunosDTO;
import com.example.API_Fabrica_Software.Model.ClassAlunos;
import com.example.API_Fabrica_Software.Repository.RepositoryAlunos;

@Service
public class PaginaUserAluno {
    @Autowired
    RepositoryAlunos repositoryAlunos;

    public List<RetornoAlunosDTO> getAlunosPeges() {
        List<ClassAlunos> alunos = repositoryAlunos.findAll();

        List<RetornoAlunosDTO> dados = alunos.stream().map(aluno -> new RetornoAlunosDTO(
                aluno.getRa(),
                aluno.getNome(),
                aluno.getEmailInstitucional(),
                aluno.getProjetoSelecionado() != null ? aluno.getProjetoSelecionado().getCodigoProjeto() : null,
                aluno.getCurso())).toList();
        System.out.println(dados);
        return dados;
    }

    public RetornoAlunosDTO getAlunoPege(String ra) {
        Optional<ClassAlunos> alunos = repositoryAlunos.findByRa(ra);

        if (alunos.isPresent()) {
            ClassAlunos aluno = alunos.get();
            return new RetornoAlunosDTO(
                    aluno.getRa(),
                    aluno.getNome(),
                    aluno.getEmailInstitucional(),
                    aluno.getProjetoSelecionado()!= null ? aluno.getProjetoSelecionado().getCodigoProjeto() : null,
                    aluno.getCurso());
        }
        return null;
    }
}
