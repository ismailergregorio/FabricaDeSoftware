package com.example.API_Fabrica_Software.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.API_Fabrica_Software.DTO.ProjetoDTO.dtoProjetoAtulizacaoInfomacao;
import com.example.API_Fabrica_Software.DTO.ProjetoDTO.dtoProjetoPost;
import com.example.API_Fabrica_Software.DTO.ProjetoDTO.dtoProjetoResp;
import com.example.API_Fabrica_Software.Repository.RepositoryAlunos;
import com.example.API_Fabrica_Software.Repository.RepositoryProjetos;
import com.example.API_Fabrica_Software.Service.ProjetoService.ServiceDeleteProjetos;
import com.example.API_Fabrica_Software.Service.ProjetoService.ServiceGetProjeto;
import com.example.API_Fabrica_Software.Service.ProjetoService.ServiceGetProjetos;
import com.example.API_Fabrica_Software.Service.ProjetoService.ServicePostProjetos;
import com.example.API_Fabrica_Software.Service.ProjetoService.ServicePutProjetos;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Tag(name = "Projetos", description = "Endpoints para gerenciamento de projetos")
@RestController
@RequestMapping("/projetos")
public class ControllesProjetos {

  @Autowired
  RepositoryProjetos repositorioProjetos;

  @Autowired
  RepositoryAlunos repositoryAlunos;

  @Autowired
  ServicePostProjetos servicePostProjetos;

  @Autowired
  ServicePutProjetos servicePutProjetos;

  @Autowired
  ServiceGetProjetos serviceGetProjetos;

  @Autowired
  ServiceDeleteProjetos serviceDeleteProjetos;

  @Autowired
  ServiceGetProjeto serviceGetProjeto;

  // ==========================================================
  // POST - Criar Projeto
  // ==========================================================

  @Operation(
      summary = "Cadastrar projeto",
      description = "Cria um novo projeto no sistema.",
      security = { @SecurityRequirement(name = "bearerAuth") }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Projeto criado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos"),
      @ApiResponse(responseCode = "401", description = "Não autenticado"),
      @ApiResponse(responseCode = "403", description = "Sem permissão"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\")")
  @PostMapping("/addprojetos")
  public ResponseEntity<dtoProjetoResp> CreatinProjeto(@Valid @RequestBody dtoProjetoPost DTO) {

    ResponseEntity<dtoProjetoResp> gestore = servicePostProjetos.postProjeto(DTO);
    return gestore;
  }

  // ==========================================================
  // GET - Listar Projetos
  // ==========================================================

  @Operation(
      summary = "Listar projetos",
      description = "Retorna a lista de todos os projetos cadastrados.",
      security = { @SecurityRequirement(name = "bearerAuth") }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
      @ApiResponse(responseCode = "401", description = "Não autenticado"),
      @ApiResponse(responseCode = "403", description = "Sem permissão"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
  @GetMapping("/getprojetos")
  public List<dtoProjetoResp> GetProjeto() {

    return serviceGetProjetos.getProjetos();
  }

  // ==========================================================
  // GET - Buscar Projeto por ID
  // ==========================================================

  @Operation(
      summary = "Buscar projeto por identificador",
      description = "Retorna os dados de um projeto com base no identificador informado.",
      security = { @SecurityRequirement(name = "bearerAuth") }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
      @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
      @ApiResponse(responseCode = "401", description = "Não autenticado"),
      @ApiResponse(responseCode = "403", description = "Sem permissão"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
  @GetMapping("/getprojetos/{identicadorProjetos}")
  public ResponseEntity<?> GetProjetoId(
      @PathVariable String identicadorProjetos,
      HttpServletRequest request) {

    ResponseEntity<?> service = serviceGetProjeto.getProjeto(identicadorProjetos, request);
    return service;
  }

  // ==========================================================
  // PUT - Atualizar Projeto
  // ==========================================================

  @Operation(
      summary = "Atualizar projeto",
      description = "Atualiza as informações de um projeto existente pelo identificador.",
      security = { @SecurityRequirement(name = "bearerAuth") }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos"),
      @ApiResponse(responseCode = "401", description = "Não autenticado"),
      @ApiResponse(responseCode = "403", description = "Sem permissão"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\")")
  @PutMapping("/{identicadorProjetos}")
  @Transactional
  public ResponseEntity<dtoProjetoAtulizacaoInfomacao> updateProjetos(
      @PathVariable String identicadorProjetos,
      @RequestBody dtoProjetoAtulizacaoInfomacao projeto) {

    ResponseEntity<dtoProjetoAtulizacaoInfomacao> dtoProjetoRes =
        servicePutProjetos.putProjeto(identicadorProjetos, projeto);

    return dtoProjetoRes;
  }

  // ==========================================================
  // DELETE - Deletar Projeto
  // ==========================================================

  @Operation(
      summary = "Deletar projeto",
      description = "Remove um projeto do sistema pelo identificador informado.",
      security = { @SecurityRequirement(name = "bearerAuth") }
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Projeto deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
      @ApiResponse(responseCode = "401", description = "Não autenticado"),
      @ApiResponse(responseCode = "403", description = "Sem permissão"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\")")
  @DeleteMapping("/{identicadorProjetos}")
  public ResponseEntity<?> DeleteProduct(
      @PathVariable String identicadorProjetos,
      HttpServletRequest request) {

    ResponseEntity<?> serveice = serviceDeleteProjetos.deleteProjeto(identicadorProjetos, request);
    return serveice;
  }
}
