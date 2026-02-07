package com.example.API_Fabrica_Software.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API_Fabrica_Software.DTO.UsersDTO.criarUsuarioDTO;
import com.example.API_Fabrica_Software.DTO.UsersDTO.repostaUsuarioDTO;
import com.example.API_Fabrica_Software.DTO.UsersDTO.updateUsuarioDTO;
import com.example.API_Fabrica_Software.Enun.NivelUsuario;
import com.example.API_Fabrica_Software.Model.ClassUsuario;
import com.example.API_Fabrica_Software.Service.UsuarioService.UsuarioServices;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

// SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuários", description = "Endpoints para cadastro, listagem, consulta, atualização e remoção de usuários.")
public class ControllersUsers {

    @Autowired
    UsuarioServices usuarioServices;

    // @PreAuthorize("hasAnyRole(\"ADMIN\")")
    @PostMapping()
    @Operation(
        summary = "Criar usuário",
        description = "Cria um novo usuário no sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public repostaUsuarioDTO postMethodName(@RequestBody criarUsuarioDTO entity) {
        return usuarioServices.salvar(entity);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER\",\"USER_N1\",\"USER_N2\")")
    @Operation(
        summary = "Listar usuários",
        description = "Retorna todos os usuários (somente ADMIN). Caso não seja ADMIN, retorna apenas o usuário logado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public List<repostaUsuarioDTO> getMethodName(Authentication auth) {

        ClassUsuario usuarioLogado = (ClassUsuario) auth.getPrincipal();
        System.out.println(usuarioLogado.getRoles());

        if (!usuarioLogado.getRoles().equals(NivelUsuario.ADMIN)) {
            return List.of(
                    usuarioServices.getUser(usuarioLogado.getId()));
        }

        return usuarioServices.usuarios();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER\",\"USER_N1\",\"USER_N2\")")
    @Operation(
        summary = "Buscar usuário por ID",
        description = "Retorna os dados de um usuário específico. Se não for ADMIN, sempre retorna o próprio usuário logado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "400", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<repostaUsuarioDTO> getMethodName(@PathVariable Long id, Authentication auth) {

        ClassUsuario usuarioLogado = (ClassUsuario) auth.getPrincipal();
        var user = usuarioServices.getUser(id);

        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (!usuarioLogado.getRoles().equals(NivelUsuario.ADMIN)) {
            return ResponseEntity.ok().body(usuarioServices.getUser(usuarioLogado.getId()));
        }

        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER\",\"USER_N1\",\"USER_N2\")")
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar usuário",
        description = "ADMIN pode deletar qualquer usuário. Usuários comuns só podem deletar o próprio usuário."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> deletar(@PathVariable Long id, Authentication auth) {

        ClassUsuario usuarioLogado = (ClassUsuario) auth.getPrincipal();

        if (usuarioLogado.getRoles().equals(NivelUsuario.ADMIN)) {
            return usuarioServices.deletaUsuario(id);
        }

        if (usuarioLogado.getId().equals(id)) {
            return usuarioServices.deletaUsuario(id);
        }

        return null;
    }

    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER\",\"USER_N1\",\"USER_N2\")")
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar usuário",
        description = "ADMIN pode atualizar qualquer usuário. Usuários comuns só podem atualizar o próprio usuário."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<?> putMethodName(@PathVariable Long id,
            @RequestBody updateUsuarioDTO entity, Authentication auth) {

        ClassUsuario usuarioLogado = (ClassUsuario) auth.getPrincipal();

        if (usuarioLogado.getRoles().equals(NivelUsuario.ADMIN)) {
            return usuarioServices.updateUsuario(entity, usuarioLogado);
        }

        if (usuarioLogado.getId().equals(id)) {
            return usuarioServices.updateUsuario(entity, usuarioLogado);
        }

        return null;
    }

}
