package com.example.API_Fabrica_Software.Service.UsuarioService.implementacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.API_Fabrica_Software.DTO.UsersDTO.criarUsuarioDTO;
import com.example.API_Fabrica_Software.DTO.UsersDTO.repostaUsuarioDTO;
import com.example.API_Fabrica_Software.DTO.UsersDTO.updateUsuarioDTO;
import com.example.API_Fabrica_Software.Enun.NivelUsuario;
import com.example.API_Fabrica_Software.Exception.ApiError;
import com.example.API_Fabrica_Software.Exception.SenhaInvalidaException;
import com.example.API_Fabrica_Software.Exception.UsuarioNaoEncontradoException;
import com.example.API_Fabrica_Software.Model.ClassUsuario;
import com.example.API_Fabrica_Software.Repository.RepositoryUsuario;
import com.example.API_Fabrica_Software.Service.UsuarioService.UsuarioServices;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsuarioService implements UsuarioServices {

  @Autowired
  RepositoryUsuario repositoryUsuario;
  @Autowired
  PasswordEncoder encoder;

  @Override
  public repostaUsuarioDTO salvar(criarUsuarioDTO usuario) {

    var senhaCriptografada = encoder.encode(usuario.senha());
    ClassUsuario user = new ClassUsuario(usuario.nome(), usuario.login(), senhaCriptografada, usuario.roles());

    repositoryUsuario.save(user);
    return new repostaUsuarioDTO(
        user.getId(),
        user.getNome(),
        user.getLogin(),
        user.getRoles(),
        user.getDataDeCriacao(),
        user.getDataDeAtulizacao());
  }

  @Override
  public List<repostaUsuarioDTO> usuarios() {
    List<ClassUsuario> users = repositoryUsuario.findAll();
    return users.stream().map(
        user -> new repostaUsuarioDTO(
            user.getId(),
            user.getNome(),
            user.getLogin(),
            user.getRoles(),
            user.getDataDeCriacao(),
            user.getDataDeAtulizacao()))
        .toList();
  }

  @Override
  public repostaUsuarioDTO getUser(Long id) {
    Optional<ClassUsuario> user = repositoryUsuario.findById(id);

    if (!user.isPresent()) {
      return null;
    }

    ClassUsuario u = user.get();
    return new repostaUsuarioDTO(
        u.getId(),
        u.getNome(),
        u.getLogin(),
        u.getRoles(),
        u.getDataDeCriacao(),
        u.getDataDeAtulizacao());
  }

  @Override
  public ResponseEntity<?> deletaUsuario(Long id) {
    Optional<ClassUsuario> user = repositoryUsuario.findById(id);

    if (user.isPresent()) {
      repositoryUsuario.delete(user.get());
      return ResponseEntity.ok().body("Usuario deletado");
    }
    return ResponseEntity.badRequest().build();
  }

  @Override
  public ResponseEntity<?> updateUsuario(updateUsuarioDTO user, ClassUsuario us) {
    ClassUsuario userUpdate = repositoryUsuario.findByLogin(user.login());

    if (userUpdate == null) {

      return ResponseEntity.badRequest().body(new ApiError(LocalDateTime.now(),
          400,
          "Usuario invalido",
          null,
          null));
    }

    if (user.novaSenha() != null && !user.novaSenha().isBlank()) {
      if (!encoder.matches(user.senhaAtual(), userUpdate.getPassword())) {
        return ResponseEntity.badRequest().body(new ApiError(LocalDateTime.now(),
            400,
            "Usuario invalido",
            null,
            null));
      }

      userUpdate.setSenha(encoder.encode(user.novaSenha()));
    }

    userUpdate.setNome(user.nome());
    userUpdate.setLogin(user.login());

    if (us.getRoles().equals(NivelUsuario.ADMIN)) {
      userUpdate.setRoles(user.roles());
    }

    repositoryUsuario.save(userUpdate);

    return ResponseEntity.ok().body(new repostaUsuarioDTO(
        userUpdate.getId(),
        userUpdate.getNome(),
        userUpdate.getLogin(),
        userUpdate.getRoles(),
        userUpdate.getDataDeCriacao(),
        userUpdate.getDataDeAtulizacao()));
  }

}
