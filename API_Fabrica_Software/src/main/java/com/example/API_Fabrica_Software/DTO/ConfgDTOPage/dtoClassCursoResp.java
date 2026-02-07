package com.example.API_Fabrica_Software.DTO.ConfgDTOPage;

import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassCursos;

public class dtoClassCursoResp {

    private String codigoDoCurso;
    private String nomeDoCurso;

    public dtoClassCursoResp(String codigoDoCurso, String nomeDoCurso) {
        this.codigoDoCurso = codigoDoCurso;
        this.nomeDoCurso = nomeDoCurso;
    }

    public dtoClassCursoResp() {

    }

    public dtoClassCursoResp(ClassCursos curso) {
        this.codigoDoCurso = curso.getCodigoDoCurso();
        this.nomeDoCurso = curso.getNomeDoCurso();
    }

    public String getCodigoDoCurso() {
        return codigoDoCurso;
    }

    public void setCodigoDoCurso(String codigoDoCurso) {
        this.codigoDoCurso = codigoDoCurso;
    }

    public String getNomeDoCurso() {
        return nomeDoCurso;
    }

    public void setNomeDoCurso(String nomeDoCurso) {
        this.nomeDoCurso = nomeDoCurso;
    }
}
