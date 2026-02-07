package com.example.API_Fabrica_Software.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.API_Fabrica_Software.Model.ClassUsuario;

public interface RepositoryUsuario extends JpaRepository<ClassUsuario, Long>{
 Optional<ClassUsuario> existsByLogin(String login);
 ClassUsuario findByLogin(String login);
}
