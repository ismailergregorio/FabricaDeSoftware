package com.example.API_Fabrica_Software.Repository.RepositoryConfgSite;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassConfigSite;

@Repository
public interface RepositoryConfigSite extends JpaRepository<ClassConfigSite, Long>{
 Optional<ClassConfigSite> findByCodigoDaConfguracao(String codigoDaConfguracao);
 Optional<ClassConfigSite> findByNomeConfig(String nomeConfig);

}