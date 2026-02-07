package com.example.API_Fabrica_Software.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

//CLASSE DE INSTANCIA DE ALUNOS E A CRIAÇÃO DE TABELA DE ALUNOS

@Entity
@Table(name = "db_alunos")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ClassAlunos {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String ra;

  @Column(unique = true, nullable = false)
  private String emailInstitucional;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String curso;

  @ManyToOne(optional = true)
  @JoinColumn(name = "projeto", referencedColumnName = "codigoProjeto", nullable = true)
  @JsonBackReference
  private ClassProjetos projetoSelecionado;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String motivoDaInscricao;

  @Column(nullable = false, columnDefinition = "TEXT")
  private LocalDate dataInscricao;

  @ColumnDefault("false")
  private boolean status;

  @CreatedDate
  @Column(name = "datadecriacao")
  private LocalDateTime dataDeCriacao;

  @LastModifiedDate
  @Column(name = "datadeatualizacao")
  private LocalDateTime dataDeAtualizacao;

  @Column(name = "idusuario")
  private Long idUsuario;

}