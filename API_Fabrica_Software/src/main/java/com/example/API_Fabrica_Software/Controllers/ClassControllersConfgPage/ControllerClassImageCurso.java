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

import com.example.API_Fabrica_Software.DTO.ConfgDTOPage.dtoClassImageCursoPost;
import com.example.API_Fabrica_Software.DTO.ConfgDTOPage.dtoClassImageCursoResp;
import com.example.API_Fabrica_Software.Exception.ApiError;
import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassImageCursos;
import com.example.API_Fabrica_Software.Repository.RepositoryConfgSite.RepositoryImagensCurso;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/imagemcurso")
@Tag(
    name = "Imagens dos Cursos",
    description = "Endpoints para cadastro, listagem, consulta e exclusão de imagens dos cursos"
)
public class ControllerClassImageCurso {

    @Autowired
    RepositoryImagensCurso crepositoryImagensCurso;

    @Operation(
        summary = "Cadastrar imagem do curso",
        description = "Cria uma nova imagem para curso informando linkImagemCurso e altImagem."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Imagem cadastrada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @PostMapping("/imagemcurso")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
    public ResponseEntity<dtoClassImageCursoResp> addImagemCurso(@RequestBody dtoClassImageCursoPost dto) {

        ClassImageCursos imagem = new ClassImageCursos();
        imagem.setLinkImagemCurso(dto.getLinkImagemCurso());
        imagem.setAltImagem(dto.getAltImagem());

        crepositoryImagensCurso.save(imagem);

        dtoClassImageCursoResp dtoResposta = new dtoClassImageCursoResp();
        dtoResposta.setCodigoImagem(imagem.getCodigoImagem());
        dtoResposta.setAltImagem(imagem.getAltImagem());
        dtoResposta.setLinkImagemCurso(imagem.getLinkImagemCurso());

        return ResponseEntity.ok(dtoResposta);
    }

    @Operation(
        summary = "Listar imagens dos cursos",
        description = "Retorna todas as imagens cadastradas para cursos."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Imagens retornadas com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @GetMapping("/imagemcurso")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
    public List<dtoClassImageCursoResp> listarImagensCursos() {

        List<ClassImageCursos> dados = crepositoryImagensCurso.findAll();
        return dados.stream().map(dtoClassImageCursoResp::new).toList();
    }

    @Operation(
        summary = "Buscar imagem do curso por código",
        description = "Retorna uma imagem específica com base no codigoImagem."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Imagem encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @GetMapping("/imagemcurso/{codigoImagem}")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
    public ResponseEntity<?> buscarImagemCurso(@PathVariable String codigoImagem, HttpServletRequest request) {

        Optional<ClassImageCursos> imagens = crepositoryImagensCurso.findByCodigoImagem(codigoImagem);

        if (imagens.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Imagem do curso não encontrada",
                            request.getRequestURI()
                    )
            );
        }

        ClassImageCursos imagem = imagens.get();

        dtoClassImageCursoResp repostaDto = new dtoClassImageCursoResp();
        repostaDto.setCodigoImagem(imagem.getCodigoImagem());
        repostaDto.setLinkImagemCurso(imagem.getLinkImagemCurso());
        repostaDto.setAltImagem(imagem.getAltImagem());

        return ResponseEntity.ok(repostaDto);
    }

    @Operation(
        summary = "Excluir imagem do curso",
        description = "Remove uma imagem do curso com base no codigoImagem."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Imagem removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @DeleteMapping("/imagemcurso/{codigoImagem}")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
    public ResponseEntity<?> deletarImagemCurso(@PathVariable String codigoImagem, HttpServletRequest request) {

        Optional<ClassImageCursos> itenSelecionado = crepositoryImagensCurso.findByCodigoImagem(codigoImagem);

        if (itenSelecionado.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Imagem do curso não encontrada",
                            request.getRequestURI()
                    )
            );
        }

        ClassImageCursos iten = itenSelecionado.get();
        crepositoryImagensCurso.delete(iten);

        dtoClassImageCursoResp itensDeletado = new dtoClassImageCursoResp();
        itensDeletado.setCodigoImagem(iten.getCodigoImagem());
        itensDeletado.setLinkImagemCurso(iten.getLinkImagemCurso());
        itensDeletado.setAltImagem(iten.getAltImagem());

        return ResponseEntity.ok(itensDeletado);
    }
}
