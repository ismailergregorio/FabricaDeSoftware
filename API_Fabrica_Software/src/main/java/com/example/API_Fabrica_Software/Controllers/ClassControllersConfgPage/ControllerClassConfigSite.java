package com.example.API_Fabrica_Software.Controllers.ClassControllersConfgPage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Fabrica_Software.DTO.ConfgDTOPage.dtoClassConfgSiteResp;
import com.example.API_Fabrica_Software.DTO.ConfgDTOPage.dtoClassConfigSitePost;
import com.example.API_Fabrica_Software.DTO.ConfgDTOPage.dtoClassConfigSitePut;
import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassConfigSite;
import com.example.API_Fabrica_Software.Repository.RepositoryConfgSite.RepositoryConfigSite;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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

@RestController
@RequestMapping("/config")
@Tag(name = "Configuração do Site", description = "Endpoints para gerenciar configurações do site")
public class ControllerClassConfigSite {

  @Autowired
  RepositoryConfigSite repositoryConfigSite;

  @Operation(
      summary = "Listar configurações do site",
      description = "Retorna todas as configurações cadastradas para o site."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Configurações retornadas com sucesso"),
      @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
      @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
  })
  @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\",\"USER_N2\",\"USER\")")
  @GetMapping("/config")
  public List<dtoClassConfgSiteResp> getConfigSite() {
    List<ClassConfigSite> itens = repositoryConfigSite.findAll();
    return itens.stream().map(dtoClassConfgSiteResp::new).toList();
  }

  @Operation(
      summary = "Criar configuração do site",
      description = "Cria uma nova configuração do site informando nomeConfig e valorSalvo."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Configuração criada com sucesso"),
      @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
      @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
  })
  @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
  @PostMapping("/config")
  public ResponseEntity<dtoClassConfgSiteResp> criarConfig(@RequestBody dtoClassConfigSitePost entity) {

    ClassConfigSite configuracao = new ClassConfigSite();
    configuracao.setNomeConfig(entity.getNomeConfig());
    configuracao.setValorSalvo(entity.getValorSalvo());

    repositoryConfigSite.save(configuracao);

    dtoClassConfgSiteResp dtoResp = new dtoClassConfgSiteResp();
    dtoResp.setCodigoDaConfguracao(configuracao.getCodigoDaConfguracao());
    dtoResp.setNomeConfig(configuracao.getNomeConfig());
    dtoResp.setValorSalvo(configuracao.getValorSalvo());

    return ResponseEntity.ok(dtoResp);
  }

  @Operation(
      summary = "Atualizar configuração do site",
      description = "Atualiza o valorSalvo de uma configuração com base no nomeConfig."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Configuração atualizada com sucesso"),
      @ApiResponse(responseCode = "404", description = "Configuração não encontrada"),
      @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
      @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
  })
  @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
  @PutMapping("/config/{nomeConfig}")
  public ResponseEntity<dtoClassConfgSiteResp> atualizarConfig(
      @PathVariable String nomeConfig,
      @RequestBody dtoClassConfigSitePut dto
  ) {

    Optional<ClassConfigSite> itens = repositoryConfigSite.findByNomeConfig(nomeConfig);

    if (itens.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    ClassConfigSite itenSelecionado = itens.get();
    itenSelecionado.setValorSalvo(dto.getValorSalvo());

    repositoryConfigSite.save(itenSelecionado);

    dtoClassConfgSiteResp dtoResp = new dtoClassConfgSiteResp();
    dtoResp.setCodigoDaConfguracao(itenSelecionado.getCodigoDaConfguracao());
    dtoResp.setNomeConfig(itenSelecionado.getNomeConfig());
    dtoResp.setValorSalvo(itenSelecionado.getValorSalvo());

    return ResponseEntity.ok(dtoResp);
  }

  @Operation(
      summary = "Excluir configuração do site",
      description = "Remove uma configuração do site com base no nomeConfig."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Configuração removida com sucesso"),
      @ApiResponse(responseCode = "404", description = "Configuração não encontrada"),
      @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
      @ApiResponse(responseCode = "500", description = "Erro inesperado do servidor")
  })
  @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER_N1\")")
  @DeleteMapping("/config/{nomeConfig}")
  public ResponseEntity<dtoClassConfgSiteResp> deletarConfig(@PathVariable String nomeConfig) {

    Optional<ClassConfigSite> itens = repositoryConfigSite.findByNomeConfig(nomeConfig);

    if (itens.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    ClassConfigSite itenSelecionado = itens.get();
    repositoryConfigSite.delete(itenSelecionado);

    dtoClassConfgSiteResp dtoResp = new dtoClassConfgSiteResp();
    dtoResp.setCodigoDaConfguracao(itenSelecionado.getCodigoDaConfguracao());
    dtoResp.setNomeConfig(itenSelecionado.getNomeConfig());
    dtoResp.setValorSalvo(itenSelecionado.getValorSalvo());

    return ResponseEntity.ok(dtoResp);
  }
}
