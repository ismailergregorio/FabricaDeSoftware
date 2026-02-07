package com.example.API_Fabrica_Software.Controllers.ClassControllersConfgPage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Fabrica_Software.DTO.ConfgDTOPage.dtoClassCursoPost;
import com.example.API_Fabrica_Software.DTO.ConfgDTOPage.dtoClassCursoResp;
import com.example.API_Fabrica_Software.Exception.ApiError;
import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassCursos;
import com.example.API_Fabrica_Software.Repository.RepositoryConfgSite.RepositoryCurso;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/curso")
@Tag(name = "Cursos", description = "Endpoints para cadastro, listagem, consulta e exclusão de cursos")
public class ControllerClassCurso {

    @Autowired
    RepositoryCurso repositoryCurso;

    @Operation(
        summary = "Cadastrar curso",
        description = "Cria um novo curso informando o nome do curso."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Curso cadastrado com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @PostMapping("/curso")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
    public ResponseEntity<dtoClassCursoResp> addCurso(@RequestBody dtoClassCursoPost dto) {

        ClassCursos curso = new ClassCursos();
        curso.setNomeDoCurso(dto.getNomeDoCurso());

        repositoryCurso.save(curso);

        dtoClassCursoResp dtoResposta = new dtoClassCursoResp();
        dtoResposta.setCodigoDoCurso(curso.getCodigoDoCurso());
        dtoResposta.setNomeDoCurso(curso.getNomeDoCurso());

        return ResponseEntity.ok(dtoResposta);
    }

    @Operation(
        summary = "Listar cursos",
        description = "Retorna todos os cursos cadastrados."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cursos retornados com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @GetMapping("/curso")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
    public List<dtoClassCursoResp> listarCursos() {

        List<ClassCursos> dados = repositoryCurso.findAll();
        return dados.stream().map(dtoClassCursoResp::new).toList();
    }

    @Operation(
        summary = "Buscar curso por código",
        description = "Retorna um curso específico com base no códigoDoCurso."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Curso não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @GetMapping("/curso/{codigoDoCurso}")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
    public ResponseEntity<dtoClassCursoResp> buscarCurso(@PathVariable String codigoDoCurso) {

        Optional<ClassCursos> curso = repositoryCurso.findByCodigoDoCurso(codigoDoCurso);

        if (curso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ClassCursos cursos = curso.get();

        dtoClassCursoResp repostaDto = new dtoClassCursoResp();
        repostaDto.setCodigoDoCurso(cursos.getCodigoDoCurso());
        repostaDto.setNomeDoCurso(cursos.getNomeDoCurso());

        return ResponseEntity.ok(repostaDto);
    }

    @Operation(
        summary = "Excluir curso",
        description = "Remove um curso do sistema com base no códigoDoCurso."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Curso removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Curso não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @DeleteMapping("/curso/{codigoDoCurso}")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
    public ResponseEntity<?> deletarCurso(
            @PathVariable String codigoDoCurso,
            HttpServletRequest request
    ) {

        Optional<ClassCursos> itenSelecionado = repositoryCurso.findByCodigoDoCurso(codigoDoCurso);

        if (itenSelecionado.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Curso não encontrado",
                            request.getRequestURI()
                    )
            );
        }

        ClassCursos iten = itenSelecionado.get();
        repositoryCurso.delete(iten);

        dtoClassCursoResp repostaDto = new dtoClassCursoResp();
        repostaDto.setCodigoDoCurso(iten.getCodigoDoCurso());
        repostaDto.setNomeDoCurso(iten.getNomeDoCurso());

        return ResponseEntity.ok(repostaDto);
    }
}
