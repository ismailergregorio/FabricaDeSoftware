package com.example.API_Fabrica_Software.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//CLASSE DE INSTANCIA DE PROJETOS E A CRIAÇÃO DE TABELA DE PROJETOS

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_projetos")
public class ClassProjetos {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String codigoProjeto;

  @Column(unique = true, nullable = false)
  private String nomeDoProjeto;
  @Column(columnDefinition = "TEXT", nullable = false)
  private String descricaoDoProjeto;

  @Column(nullable = false)
  private String areaDeConhecimento;

  @Column(nullable = false)
  private LocalDate dataDeInicioDoProjeto;

  @Column(nullable = false)
  private LocalDate dataDoFimDoProjeto;

  @OneToMany(mappedBy = "projetoSelecionado", orphanRemoval = true)
  @JsonManagedReference
  @Column(nullable = true)
  private List<ClassAlunos> alunosParticipantesDoProjeto = new ArrayList<>();

  @ManyToMany(mappedBy = "projetos")
  private Set<ClassGestores> profesorOrientador = new LinkedHashSet<>();

  @Column(unique = true, nullable = false, columnDefinition = "TEXT")
  private String linkGit;
  private String linkImage;

  @PrePersist
  public void geraCodigoProjeto() {
    String prefixo = "PROJ";
    int numero = (int) (Math.random() * 9000) + 1000;
    this.codigoProjeto = prefixo + numero;
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
