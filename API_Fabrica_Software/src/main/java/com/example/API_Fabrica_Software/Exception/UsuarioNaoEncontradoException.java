package com.example.API_Fabrica_Software.Exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

 public UsuarioNaoEncontradoException() {
  super("Usuário não encontrado");
 }
}