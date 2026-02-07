package com.example.API_Fabrica_Software.Model.ClassConfigPage;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_configuracao_site")
public class ClassConfigSite {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(nullable = false)
 private String codigoDaConfguracao;

 @Column(nullable = false)
 private String nomeConfig;

 @Column(columnDefinition = "TEXT")
 private String valorSalvo;

 @PrePersist
 public void GeradorDeCodigoConfiguracao() {
  String prefixo = "CONF";
  int numero = (int) (Math.random() * 9000) + 1000;
  this.codigoDaConfguracao = prefixo + numero;
 }

 @CreatedDate
 @Column(name = "datadecriacao")
 private LocalDateTime dataDeCriacao;

 @LastModifiedDate
 @Column(name = "datadeatualizacao")
 private LocalDateTime dataDeAtualizacao;

 @Column(name = "idusuario")
 private Long idUsuario;
}
