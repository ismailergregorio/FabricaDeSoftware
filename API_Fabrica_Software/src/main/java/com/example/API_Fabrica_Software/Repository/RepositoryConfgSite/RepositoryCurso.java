package com.example.API_Fabrica_Software.Repository.RepositoryConfgSite;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassCursos;

public interface RepositoryCurso extends JpaRepository<ClassCursos,Long>{
    Optional<ClassCursos> findByCodigoDoCurso(String codigoDoCurso);
}
