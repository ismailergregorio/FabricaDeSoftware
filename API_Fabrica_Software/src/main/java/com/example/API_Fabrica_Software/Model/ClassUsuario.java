package com.example.API_Fabrica_Software.Model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.API_Fabrica_Software.Enun.NivelUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_usuario")
public class ClassUsuario implements UserDetails {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(nullable = false)
   private String nome;
   @Column(nullable = false, unique = true)
   private String login;
   @Column(nullable = false)
   private String senha;
   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private NivelUsuario roles;
   @CreatedDate
   private LocalDateTime dataDeCriacao;
   @LastModifiedDate
   private LocalDateTime dataDeAtulizacao;

   public ClassUsuario(String nome, String login, String senha, NivelUsuario roles) {
      this.nome = nome;
      this.login = login;
      this.senha = senha;
      this.roles = roles;
   }

   // ADMIN("admin"),
   // USER_N1("usuario nivel 1"),
   // USER_N2("usuario nivel 2"),
   // USER("user");
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      // TODO Auto-generated method stub
      if (this.roles == NivelUsuario.ADMIN) {
         return List.of(
               new SimpleGrantedAuthority("ROLE_ADMIN"),
               new SimpleGrantedAuthority("ROLE_USER_N1"),
               new SimpleGrantedAuthority("ROLE_USER_N2"),
               new SimpleGrantedAuthority("ROLE_USER"));
      }
      if (this.roles == NivelUsuario.USER_N1) {
         return List.of(
               new SimpleGrantedAuthority("ROLE_USER_N1"),
               new SimpleGrantedAuthority("ROLE_USER_N2"),
               new SimpleGrantedAuthority("ROLE_USER"));
      }
      if (this.roles == NivelUsuario.USER_N2) {
         return List.of(
               new SimpleGrantedAuthority("ROLE_USER_N2"),
               new SimpleGrantedAuthority("ROLE_USER"));
      }
      return List.of(
            new SimpleGrantedAuthority("ROLE_USER"));

   }

   @Override
   public String getPassword() {
      return this.senha;
   }

   @Override
   public String getUsername() {
      return this.login;
   }

   @Override
   public boolean isAccountNonExpired() {
      // TODO Auto-generated method stub
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      // TODO Auto-generated method stub
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      // TODO Auto-generated method stub
      return true;
   }

   @Override
   public boolean isEnabled() {
      // TODO Auto-generated method stub
      return true;
   }
}
