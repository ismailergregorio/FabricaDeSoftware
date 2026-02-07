package com.example.API_Fabrica_Software.Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Fabrica_Software.DTO.GestoresDTO.dtoGestoresPost;
import com.example.API_Fabrica_Software.DTO.GestoresDTO.dtoGestoresRespost;
import com.example.API_Fabrica_Software.Exception.ApiError;
import com.example.API_Fabrica_Software.Model.ClassGestores;
import com.example.API_Fabrica_Software.Model.ClassProjetos;
import com.example.API_Fabrica_Software.Repository.RepositoryProjetos;

import jakarta.servlet.http.HttpServletRequest;

import com.example.API_Fabrica_Software.Repository.RepositoryGestores;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/gestores")
@CrossOrigin(origins = "*")
@Tag(name = "Gestores", description = "Endpoints para cadastro, listagem, atualização e remoção de gestores.")
public class ControllersGestores {

    @Autowired
    RepositoryGestores repositoryGestores;

    @Autowired
    RepositoryProjetos repositoryProjetos;

    @PostMapping("/addgestores")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\")")
    @Operation(
        summary = "Cadastrar gestor",
        description = "Cria um novo gestor e vincula os projetos informados (caso existam)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gestor cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação (projeto inválido ou inexistente)"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<dtoGestoresRespost> addGestores(@RequestBody dtoGestoresPost dtogestor) {

        ClassGestores gestor = new ClassGestores();
        List<ClassProjetos> listaDeProjetos = new ArrayList<>();

        if (dtogestor.getProjetos() != null) {
            for (String projetos : dtogestor.getProjetos()) {
                Optional<ClassProjetos> projeto = repositoryProjetos.findByCodigoProjeto(projetos);

                if (projeto.isPresent()) {
                    listaDeProjetos.add(projeto.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
        }

        gestor.setName(dtogestor.getName());
        gestor.setCursoResposavel(dtogestor.getCursoResposavel());
        gestor.setDescricao(dtogestor.getDescricao());
        gestor.setLinkImagenGestor(dtogestor.getLinkImagenGestor());
        gestor.setProjetos((listaDeProjetos != null) ? listaDeProjetos : null);

        repositoryGestores.save(gestor);

        dtoGestoresRespost dtoreposta = new dtoGestoresRespost();

        dtoreposta.setCodigoGestor(gestor.getCodigoGestor());
        dtoreposta.setName(gestor.getName());
        dtoreposta.setCursoResposavel(gestor.getCursoResposavel());
        dtoreposta.setDescricao(gestor.getDescricao());
        dtoreposta.setLinkImagenGestor(gestor.getLinkImagenGestor());
        dtoreposta.setProjetos(listaDeProjetos.stream()
                .map(ClassProjetos::getCodigoProjeto)
                .toList());

        return ResponseEntity.ok(dtoreposta);
    }

    @GetMapping("/gestores")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
    @Operation(
        summary = "Listar gestores",
        description = "Retorna todos os gestores cadastrados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public List<dtoGestoresRespost> GetGestores() {
        List<ClassGestores> gestores = repositoryGestores.findAll();
        return gestores.stream().map(dtoGestoresRespost::new).toList();
    }

    @GetMapping("/gestor/{CodigoGestor}")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
    @Operation(
        summary = "Buscar gestor por código",
        description = "Retorna os dados de um gestor específico pelo código."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gestor encontrado"),
        @ApiResponse(responseCode = "404", description = "Gestor não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> getMethodName(@PathVariable String CodigoGestor, HttpServletRequest request) {

        Optional<ClassGestores> gestoresselecionados = repositoryGestores.findByCodigoGestor(CodigoGestor);

        if (!gestoresselecionados.isPresent()) {
            System.out.println("Iten não encontardor");
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Projeto nao encontrado",
                            request.getRequestURI()));
        }

        ClassGestores gestor = gestoresselecionados.get();

        dtoGestoresRespost dtoRes = new dtoGestoresRespost();

        dtoRes.setCodigoGestor(gestor.getCodigoGestor());
        dtoRes.setName(gestor.getName());
        dtoRes.setCursoResposavel(gestor.getCursoResposavel());
        dtoRes.setDescricao(gestor.getDescricao());
        dtoRes.setLinkImagenGestor(gestor.getLinkImagenGestor());
        dtoRes.setProjetos(gestor.getProjetos().stream().map(ClassProjetos::getCodigoProjeto).toList());

        return ResponseEntity.ok(dtoRes);
    }

    @PutMapping("/gestor/{CodigoGestor}")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\")")
    @Operation(
        summary = "Atualizar gestor",
        description = "Atualiza os dados do gestor e seus projetos vinculados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gestor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Gestor não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> putGestores(@PathVariable String CodigoGestor,
            @RequestBody dtoGestoresPost gestorAtulisado, HttpServletRequest request) {

        Optional<ClassGestores> itenOptional = repositoryGestores.findByCodigoGestor(CodigoGestor);

        if (!itenOptional.isPresent()) {
            System.out.println("Iten não encontardor");
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Projeto nao encontrado",
                            request.getRequestURI()));
        }

        List<ClassProjetos> listaAtualisada = new ArrayList<>();

        if (gestorAtulisado.getProjetos() != null) {
            for (String codigoProjeto : gestorAtulisado.getProjetos()) {
                Optional<ClassProjetos> progetoselecionado = repositoryProjetos.findByCodigoProjeto(codigoProjeto);

                if (progetoselecionado.isPresent()) {
                    listaAtualisada.add(progetoselecionado.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
            }
        }

        ClassGestores gestor = itenOptional.get();

        gestor.setName(gestorAtulisado.getName());
        gestor.setCursoResposavel(gestorAtulisado.getCursoResposavel());
        gestor.setDescricao(gestorAtulisado.getDescricao());
        gestor.setLinkImagenGestor(gestorAtulisado.getLinkImagenGestor());
        gestor.setProjetos((listaAtualisada != null) ? listaAtualisada : null);

        repositoryGestores.save(gestor);

        dtoGestoresRespost dtoRes = new dtoGestoresRespost();

        dtoRes.setCodigoGestor(gestor.getCodigoGestor());
        dtoRes.setName(gestor.getName());
        dtoRes.setCursoResposavel(gestor.getCursoResposavel());
        dtoRes.setDescricao(gestor.getDescricao());
        dtoRes.setLinkImagenGestor(gestor.getLinkImagenGestor());
        dtoRes.setProjetos((gestor.getProjetos() != null)
                ? gestor.getProjetos().stream().map(ClassProjetos::getCodigoProjeto).toList()
                : null);

        return ResponseEntity.ok(dtoRes);
    }

    @DeleteMapping("/gestor/{CodigoGestor}")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\")")
    @Operation(
        summary = "Deletar gestor",
        description = "Remove um gestor do sistema pelo código."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gestor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Gestor não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> deleteGestores(@PathVariable String CodigoGestor, HttpServletRequest request) {

        Optional<ClassGestores> itenOptional = repositoryGestores.findByCodigoGestor(CodigoGestor);

        if (!itenOptional.isPresent()) {
            System.out.println("Iten não encontardor");
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Projeto nao encontrado",
                            request.getRequestURI()));
        }

        ClassGestores gestorDeletado = itenOptional.get();

        dtoGestoresRespost dtoRes = new dtoGestoresRespost();

        dtoRes.setCodigoGestor(gestorDeletado.getCodigoGestor());
        dtoRes.setName(gestorDeletado.getName());
        dtoRes.setCursoResposavel(gestorDeletado.getCursoResposavel());
        dtoRes.setDescricao(gestorDeletado.getDescricao());
        dtoRes.setLinkImagenGestor(gestorDeletado.getLinkImagenGestor());
        dtoRes.setProjetos((gestorDeletado.getProjetos() != null)
                ? gestorDeletado.getProjetos().stream().map(ClassProjetos::getCodigoProjeto).toList()
                : null);

        repositoryGestores.delete(gestorDeletado);

        return ResponseEntity.ok(dtoRes);
    }

}
