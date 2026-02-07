package com.example.API_Fabrica_Software.Exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> itemJaExiste(Exception ex, HttpServletRequest request) {
        ApiError erro = new ApiError(
                LocalDateTime.now(),
                409,
                "Erro Conflito",
                "Item já existe no banco de dados",
                request.getRequestURI());
        return ResponseEntity.status(409).body(erro);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> acessoNegado(Exception ex, HttpServletRequest request) {
        ApiError erro = new ApiError(
                LocalDateTime.now(),
                403,
                "Acesso negado",
                "Você não possui acesso a esta requisição",
                request.getRequestURI());
        return ResponseEntity.status(403).body(erro);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiError> semAcesso(Exception ex, HttpServletRequest request) {
        ApiError erro = new ApiError(
                LocalDateTime.now(),
                401,
                "Não autenticado",
                "Você não está autenticado",
                request.getRequestURI());
        return ResponseEntity.status(401).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeral(Exception ex, HttpServletRequest request) {
        ApiError erro = new ApiError(
                LocalDateTime.now(),
                500,
                "Erro interno",
                "Erro inesperado no servidor",
                request.getRequestURI());
        return ResponseEntity.status(500).body(erro);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ApiError> usuarioNaoEncontrado(
            UsuarioNaoEncontradoException ex,
            HttpServletRequest request) {

        ApiError erro = new ApiError(
                LocalDateTime.now(),
                404,
                "Usuário não encontrado",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(404).body(erro);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<ApiError> senhaInvalida(
            SenhaInvalidaException ex,
            HttpServletRequest request) {

        ApiError erro = new ApiError(
                LocalDateTime.now(),
                400,
                "Senha inválida",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(401).body(erro);
    }

}