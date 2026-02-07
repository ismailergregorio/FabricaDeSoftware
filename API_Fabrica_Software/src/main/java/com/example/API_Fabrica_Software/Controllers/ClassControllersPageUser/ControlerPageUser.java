package com.example.API_Fabrica_Software.Controllers.ClassControllersPageUser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.API_Fabrica_Software.DTO.RetornoDTOpeges.RetornoAlunosDTO;
import com.example.API_Fabrica_Software.DTO.RetornoDTOpeges.RetornoGestorDTO;
import com.example.API_Fabrica_Software.DTO.RetornoDTOpeges.RetornoProjetosDTO;
import com.example.API_Fabrica_Software.Service.PaginaUserService.PaginaUserAluno;
import com.example.API_Fabrica_Software.Service.PaginaUserService.PaginaUserGestores;
import com.example.API_Fabrica_Software.Service.PaginaUserService.PaginaUserProjeto;

// SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(
    name = "Página Pública",
    description = "Endpoints públicos para a página do site (alunos, projetos e gestores)."
)
@RestController
@RequestMapping("/page")
public class ControlerPageUser {

    @Autowired
    PaginaUserAluno paginaUserAluno;

    @Autowired
    PaginaUserProjeto paginaUserProjeto;

    @Autowired
    PaginaUserGestores paginaUserGestores;

    // ==========================================================
    // ALUNOS
    // ==========================================================

    @Operation(
        summary = "Listar alunos (página pública)",
        description = "Retorna a lista de alunos para exibição na página pública."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/alunos")
    public ResponseEntity<List<RetornoAlunosDTO>> getAlunosPage() {
        System.out.println("Chegou");
        return ResponseEntity.ok().body(paginaUserAluno.getAlunosPeges());
    }

    @Operation(
        summary = "Buscar aluno por RA (página pública)",
        description = "Retorna os dados de um aluno específico usando o RA."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/aluno/{ra}")
    public ResponseEntity<RetornoAlunosDTO> getAlunoPage(@PathVariable String ra) {
        return ResponseEntity.ok().body(paginaUserAluno.getAlunoPege(ra));
    }

    // ==========================================================
    // PROJETOS
    // ==========================================================

    @Operation(
        summary = "Listar projetos (página pública)",
        description = "Retorna a lista de projetos para exibição na página pública."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/projetos")
    public ResponseEntity<List<RetornoProjetosDTO>> getProjetosPage() {
        return ResponseEntity.ok().body(paginaUserProjeto.getProjetos());
    }

    @Operation(
        summary = "Buscar projeto por código (página pública)",
        description = "Retorna os dados de um projeto específico pelo código."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/projeto/{codigoProjeto}")
    public ResponseEntity<RetornoProjetosDTO> getProjetoPage(@PathVariable String codigoProjeto) {
        return ResponseEntity.ok().body(paginaUserProjeto.getProjeto(codigoProjeto));
    }

    // ==========================================================
    // GESTORES
    // ==========================================================

    @Operation(
        summary = "Listar gestores (página pública)",
        description = "Retorna a lista de gestores para exibição na página pública."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/gestores")
    public ResponseEntity<List<RetornoGestorDTO>> getGestoresPage() {
        return ResponseEntity.ok().body(paginaUserGestores.getGestoresPeges());
    }

    @Operation(
        summary = "Buscar gestor por código (página pública)",
        description = "Retorna os dados de um gestor específico pelo código."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gestor encontrado"),
        @ApiResponse(responseCode = "404", description = "Gestor não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/gestor/{co}")
    public ResponseEntity<RetornoGestorDTO> getGestorPage(@PathVariable String co) {
        return ResponseEntity.ok().body(paginaUserGestores.getGestorPege(co));
    }
}
