package com.example.API_Fabrica_Software.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.API_Fabrica_Software.Model.ClassGestores;
@Repository
public interface RepositoryGestores extends JpaRepository<ClassGestores,Long>{
    Optional<ClassGestores> findByCodigoGestor(String codigoGestor);  
}
