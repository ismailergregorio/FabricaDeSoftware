package com.example.API_Fabrica_Software.Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Fabrica_Software.DTO.AlunosDTO.dtoAlunoAtulizarInfomacao;
import com.example.API_Fabrica_Software.DTO.AlunosDTO.dtoAlunosPost;
import com.example.API_Fabrica_Software.DTO.AlunosDTO.dtoAlunosRespost;
import com.example.API_Fabrica_Software.Exception.ApiError;
import com.example.API_Fabrica_Software.Model.ClassAlunos;
import com.example.API_Fabrica_Software.Model.ClassProjetos;
import com.example.API_Fabrica_Software.Repository.RepositoryAlunos;
import com.example.API_Fabrica_Software.Repository.RepositoryProjetos;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

// SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Endpoints para gerenciamento de alunos cadastrados na Fábrica de Software.")
@SecurityRequirement(name = "bearerAuth")
public class ControllersAlunos {

    @Autowired
    RepositoryAlunos repositoryAlunos;

    @Autowired
    RepositoryProjetos repositoryProjetos;

    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\")")
    @PostMapping("/addalunos")
    @Operation(
        summary = "Cadastrar aluno",
        description = "Cadastra um novo aluno no sistema. Caso o código do projeto seja informado, o aluno será vinculado ao projeto."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Aluno cadastrado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso")
    })
    public ResponseEntity<dtoAlunosRespost> postAlunos(
            @RequestBody dtoAlunosPost dto) {

        ClassAlunos aluno = new ClassAlunos();
        ClassProjetos projetoParaSalvar = new ClassProjetos();

        if (dto.getProjetoSelecionado() != null) {
            Optional<ClassProjetos> projeto = repositoryProjetos.findByCodigoProjeto(dto.getProjetoSelecionado());
            projetoParaSalvar = projeto.get();

            if (projeto.isPresent()) {
                aluno.setProjetoSelecionado(projetoParaSalvar);
            }
        } else {
            aluno.setProjetoSelecionado(null);
        }

        aluno.setRa(dto.getRa());
        aluno.setNome(dto.getNome());
        aluno.setEmailInstitucional(dto.getEmailInstitucional());
        aluno.setCurso(dto.getCurso());
        aluno.setDataInscricao(dto.getDataInscricao());
        aluno.setMotivoDaInscricao(dto.getMotivoDaInscricao());

        ClassAlunos salvo = repositoryAlunos.save(aluno);

        dtoAlunosRespost dtoResposta = new dtoAlunosRespost();

        dtoResposta.setRa(salvo.getRa());
        dtoResposta.setNome(salvo.getNome());
        dtoResposta.setEmailInstitucional(salvo.getEmailInstitucional());
        dtoResposta.setCurso(salvo.getCurso());
        dtoResposta.setProjetoSelecionado(
                salvo.getProjetoSelecionado() != null ? salvo.getProjetoSelecionado().getNomeDoProjeto() : null);

        dtoResposta.setMotivoDaInscricao(salvo.getMotivoDaInscricao());

        return ResponseEntity.ok(dtoResposta);
    }

    @GetMapping("/alunos")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
    @Operation(
        summary = "Listar alunos",
        description = "Retorna uma lista com todos os alunos cadastrados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso")
    })
    public List<dtoAlunosRespost> getAlunos() {
        List<ClassAlunos> alunos = repositoryAlunos.findAll();
        return alunos.stream().map(dtoAlunosRespost::new).toList();
    }

    @GetMapping("/aluno/{ra}")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
    @Operation(
        summary = "Buscar aluno por RA",
        description = "Retorna os dados do aluno correspondente ao RA informado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso")
    })
    public ResponseEntity<?> getAluno(
            @Parameter(description = "RA do aluno", example = "20240001")
            @PathVariable String ra,
            HttpServletRequest request) {

        Optional<ClassAlunos> alunoOptional = repositoryAlunos.findByRa(ra);

        if (!alunoOptional.isPresent()) {
            System.out.println("Iten não encontardor");
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Projeto nao encontrado",
                            request.getRequestURI()));
        }

        ClassAlunos projeto = alunoOptional.get();

        dtoAlunosRespost dtoSelecionado = new dtoAlunosRespost();

        dtoSelecionado.setRa(projeto.getRa());
        dtoSelecionado.setNome(projeto.getNome());
        dtoSelecionado.setEmailInstitucional(projeto.getEmailInstitucional());
        dtoSelecionado.setCurso(projeto.getCurso());
        dtoSelecionado.setProjetoSelecionado(
                projeto.getProjetoSelecionado() != null ? projeto.getProjetoSelecionado().getCodigoProjeto() : null);
        dtoSelecionado.setMotivoDaInscricao(projeto.getMotivoDaInscricao());

        return ResponseEntity.ok(dtoSelecionado);
    }

    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\")")
    @DeleteMapping("/aluno/{ra}")
    @Operation(
        summary = "Deletar aluno",
        description = "Remove um aluno do sistema pelo RA."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Aluno deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso")
    })
    public ResponseEntity<?> delAluno(
            @Parameter(description = "RA do aluno", example = "20240001")
            @PathVariable String ra,
            HttpServletRequest request) {

        Optional<ClassAlunos> deletAluno = repositoryAlunos.findByRa(ra);

        if (!deletAluno.isPresent()) {
            System.out.println("Iten não encontardor");
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Projeto nao encontrado",
                            request.getRequestURI()));
        }

        ClassAlunos alunoDeletado = deletAluno.get();

        dtoAlunosRespost dtoAlunoResp = new dtoAlunosRespost();

        dtoAlunoResp.setRa(alunoDeletado.getRa());
        dtoAlunoResp.setNome(alunoDeletado.getNome());
        dtoAlunoResp.setEmailInstitucional(alunoDeletado.getEmailInstitucional());
        dtoAlunoResp.setCurso(alunoDeletado.getCurso());
        dtoAlunoResp.setMotivoDaInscricao(alunoDeletado.getMotivoDaInscricao());

        repositoryAlunos.delete(alunoDeletado);

        return ResponseEntity.ok(dtoAlunoResp);
    }

    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\")")
    @PutMapping("/aluno/{ra}")
    @Operation(
        summary = "Atualizar aluno",
        description = "Atualiza as informações do aluno pelo RA. Também permite vincular ou remover o projeto selecionado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para acessar este recurso")
    })
    public ResponseEntity<?> putMethodName(
            @Parameter(description = "RA do aluno", example = "20240001")
            @PathVariable String ra,
            @RequestBody dtoAlunoAtulizarInfomacao dtoAluno,
            HttpServletRequest request) {

        Optional<ClassAlunos> alunoSelecionado = repositoryAlunos.findByRa(ra);

        if (!alunoSelecionado.isPresent()) {
            System.out.println("Iten não encontardor");
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Projeto nao encontrado",
                            request.getRequestURI()));
        }

        ClassAlunos alunoEncontrado = alunoSelecionado.get();

        if (dtoAluno.getProjetoSelecionado() != null) {
            Optional<ClassProjetos> projeto = repositoryProjetos
                    .findByCodigoProjeto(dtoAluno.getProjetoSelecionado());
            if (projeto.isPresent()) {
                alunoEncontrado.setProjetoSelecionado(projeto.get());
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            alunoEncontrado.setProjetoSelecionado(null);
        }

        alunoEncontrado.setNome(dtoAluno.getNome());
        alunoEncontrado.setEmailInstitucional(dtoAluno.getEmailInstitucional());
        alunoEncontrado.setCurso(dtoAluno.getCurso());
        alunoEncontrado.setMotivoDaInscricao(dtoAluno.getMotivoDaInscricao());
        alunoEncontrado.setStatus(dtoAluno.isStatus());

        ClassAlunos novoAluno = repositoryAlunos.save(alunoEncontrado);

        dtoAlunosRespost dtoResposta = new dtoAlunosRespost(novoAluno);

        return ResponseEntity.ok(dtoResposta);
    }
}
