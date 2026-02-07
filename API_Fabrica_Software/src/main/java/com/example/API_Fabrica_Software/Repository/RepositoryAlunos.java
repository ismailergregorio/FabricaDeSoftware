package com.example.API_Fabrica_Software.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.API_Fabrica_Software.Model.ClassAlunos;
import com.example.API_Fabrica_Software.Model.ClassProjetos;

// CRIAÇÃO DO REPOSITORIO DE ALUNOS

@Repository
public interface RepositoryAlunos extends JpaRepository<ClassAlunos, Long> {
 Optional<ClassAlunos> findByRa(String ra);
 boolean existsByRaAndProjetoSelecionadoIsNotNull(String ra);
 
 Optional<ClassAlunos> findByProjetoSelecionado(ClassProjetos projetoSelecionado);
}
