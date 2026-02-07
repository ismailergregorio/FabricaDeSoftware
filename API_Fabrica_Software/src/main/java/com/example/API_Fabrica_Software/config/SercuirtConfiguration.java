package com.example.API_Fabrica_Software.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SercuirtConfiguration {
  @Autowired
  SercurityFilter sercurityFilter;
  // @Autowired
  CorsConfigurationSource configurationSource;

  @Bean
  public SecurityFilterChain securitFilterChan(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .cors(cors -> cors.configurationSource(configurationSource))
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authoriza -> authoriza
            .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/user").permitAll()
            .requestMatchers( "/page/**").permitAll()
            // .requestMatchers(HttpMethod.GET, "/user").hasAnyRole
            .anyRequest().authenticated())
        .addFilterBefore(sercurityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer(){
    return web -> web.ignoring().requestMatchers(
      "/v2/api-docs/**",
      "/v3/api-docs/**",
      "/swagger-resoucers/**",
      "/swagger-ui.html",
      "/swagger-ui/**",
      "/webjars/**"
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
