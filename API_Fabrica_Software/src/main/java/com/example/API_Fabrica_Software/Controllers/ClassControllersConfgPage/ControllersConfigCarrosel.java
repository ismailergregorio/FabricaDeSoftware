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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Fabrica_Software.DTO.ConfgDTOPage.dtoConfgCarrocelPost;
import com.example.API_Fabrica_Software.DTO.ConfgDTOPage.dtoConfigCarrocelResp;
import com.example.API_Fabrica_Software.Exception.ApiError;
import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassCarrocel;
import com.example.API_Fabrica_Software.Repository.RepositoryConfgSite.RepositoryCarrocel;

import jakarta.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/carrocel")
@Tag(name = "Carrossel", description = "Endpoints para gerenciamento de imagens do carrossel do site")
public class ControllersConfigCarrosel {

    @Autowired
    RepositoryCarrocel repositorioImagens;

    @Operation(
        summary = "Cadastrar imagem do carrossel",
        description = "Cria uma nova imagem do carrossel informando o link da imagem e o status (ativada/desativada)."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Imagem cadastrada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
    @PostMapping("/addimagen")
    public ResponseEntity<dtoConfigCarrocelResp> PostImagens(@RequestBody dtoConfgCarrocelPost dtoCarrocel) {

        ClassCarrocel imagem = new ClassCarrocel();

        imagem.setLinkImagenCarrocel(dtoCarrocel.getLinkImagenCarrocel());
        imagem.setImagenAtivadaDesativada(dtoCarrocel.getImagenAtivadaDesativada());

        repositorioImagens.save(imagem);

        dtoConfigCarrocelResp dtoRepost = new dtoConfigCarrocelResp();

        dtoRepost.setcodigoImagem(imagem.getCodigoImagem());
        dtoRepost.setLinkImagenCarrocel(imagem.getLinkImagenCarrocel());
        dtoRepost.setImagenAtivadaDesativada(imagem.getImagenAtivadaDesativada());

        return ResponseEntity.ok(dtoRepost);
    }

    @Operation(
        summary = "Listar imagens do carrossel",
        description = "Retorna todas as imagens cadastradas no carrossel."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Imagens retornadas com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @GetMapping("/carrocel_imagens")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
    public List<dtoConfigCarrocelResp> getImagens() {
        List<ClassCarrocel> imagens = repositorioImagens.findAll();

        return imagens.stream().map(dtoConfigCarrocelResp::new).toList();
    }

    @Operation(
        summary = "Atualizar imagem do carrossel",
        description = "Atualiza os dados de uma imagem do carrossel com base no codigoImagem."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Imagem atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
    @PutMapping("path/{codigoImagem}")
    public ResponseEntity<?> putAlizarImg(@PathVariable String codigoImagem,
            @RequestBody dtoConfgCarrocelPost dto, HttpServletRequest request) {
        // TODO: process PUT request

        Optional<ClassCarrocel> imagem = repositorioImagens.findByCodigoImagem(codigoImagem);
        if (!imagem.isPresent()) {
            System.out.println("Iten não encontardor");
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Projeto nao encontrado",
                            request.getRequestURI()));
        }

        ClassCarrocel imgEncontrada = imagem.get();

        imgEncontrada.setLinkImagenCarrocel(dto.getLinkImagenCarrocel());
        imgEncontrada.setImagenAtivadaDesativada(dto.getImagenAtivadaDesativada());

        repositorioImagens.save(imgEncontrada);

        dtoConfgCarrocelPost dtoResposta = new dtoConfgCarrocelPost();

        dtoResposta.setLinkImagenCarrocel(imgEncontrada.getLinkImagenCarrocel());
        dtoResposta.setImagenAtivadaDesativada(imgEncontrada.getImagenAtivadaDesativada());

        return ResponseEntity.ok(dtoResposta);
    }

    @Operation(
        summary = "Excluir imagem do carrossel",
        description = "Remove uma imagem do carrossel com base no codigoImagem."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Imagem removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
        @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
    })
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
    @DeleteMapping("path/{codigoImagem}")
    public ResponseEntity<?> putAlizarImg(@PathVariable String codigoImagem, HttpServletRequest request) {

        Optional<ClassCarrocel> imagem = repositorioImagens.findByCodigoImagem(codigoImagem);
        if (!imagem.isPresent()) {
            System.out.println("Iten não encontardor");
            return ResponseEntity.status(404).body(
                    new ApiError(
                            LocalDateTime.now(),
                            404,
                            "NOT_FOUND",
                            "Projeto nao encontrado",
                            request.getRequestURI()));
        }

        ClassCarrocel imgEncontrada = imagem.get();

        repositorioImagens.delete(imgEncontrada);

        dtoConfgCarrocelPost dtoResposta = new dtoConfgCarrocelPost();

        dtoResposta.setLinkImagenCarrocel(imgEncontrada.getLinkImagenCarrocel());
        dtoResposta.setImagenAtivadaDesativada(imgEncontrada.getImagenAtivadaDesativada());

        return ResponseEntity.ok(dtoResposta);
    }
}
