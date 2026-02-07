package com.example.API_Fabrica_Software.Exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
 private LocalDateTime timestemp;
 private int status;
 private String error;
 private String messagem;
 private String path;
}
