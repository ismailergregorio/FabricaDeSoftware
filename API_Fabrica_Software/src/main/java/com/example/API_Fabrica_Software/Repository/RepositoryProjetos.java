package com.example.API_Fabrica_Software.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.API_Fabrica_Software.Model.ClassProjetos;
// CRIAÇÃO DO REPOSITORIO DE PROJETOS


@Repository
public interface RepositoryProjetos extends JpaRepository<ClassProjetos,Long>{
  Optional<ClassProjetos> findByCodigoProjeto(String codigoProjeto);
}
