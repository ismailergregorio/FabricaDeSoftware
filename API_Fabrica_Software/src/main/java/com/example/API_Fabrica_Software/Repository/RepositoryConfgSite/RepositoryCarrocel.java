package com.example.API_Fabrica_Software.Repository.RepositoryConfgSite;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassCarrocel;

@Repository
public interface RepositoryCarrocel extends JpaRepository<ClassCarrocel, Long> {
 Optional<ClassCarrocel> findByCodigoImagem(String codigoImagem);
}
