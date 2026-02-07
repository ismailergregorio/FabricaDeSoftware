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
@Table(name = "db_carrocel")
public class ClassCarrocel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String codigoImagem;
    @Column(columnDefinition = "TEXT")
    private String linkImagenCarrocel;
    private Boolean imagenAtivadaDesativada;

    @PrePersist
    public void GeradorDeCodigoImg() {
        String prefixo = "IMG";
        int numero = (int) (Math.random() * 9000) + 1000;
        this.codigoImagem = prefixo + numero;
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
