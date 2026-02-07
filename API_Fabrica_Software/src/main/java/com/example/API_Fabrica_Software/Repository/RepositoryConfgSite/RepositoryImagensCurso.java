package com.example.API_Fabrica_Software.Repository.RepositoryConfgSite;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassImageCursos;

@Repository
public interface RepositoryImagensCurso extends JpaRepository<ClassImageCursos,Long> {
    Optional<ClassImageCursos> findByCodigoImagem(String codigoImagem);
}
