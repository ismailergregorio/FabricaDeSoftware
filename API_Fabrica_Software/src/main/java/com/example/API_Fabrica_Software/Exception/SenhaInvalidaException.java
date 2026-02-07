package com.example.API_Fabrica_Software.Exception;

public class SenhaInvalidaException extends RuntimeException {

 public SenhaInvalidaException() {
  super("Senha atual inválida");
 }
}