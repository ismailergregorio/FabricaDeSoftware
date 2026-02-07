package com.example.API_Fabrica_Software.DTO.ConfgDTOPage;

import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassImageCursos;

public class dtoClassImageCursoPost {
    private String linkImagemCurso;
    private String altImagem;

    public dtoClassImageCursoPost(ClassImageCursos imagenCurso){
        this.linkImagemCurso = imagenCurso.getLinkImagemCurso();
        this.altImagem = imagenCurso.getAltImagem();
    }

    public dtoClassImageCursoPost(){

    }

    public dtoClassImageCursoPost(String linkImagemCurso, String altImagem) {
        this.linkImagemCurso = linkImagemCurso;
        this.altImagem = altImagem;
    }
    public String getLinkImagemCurso() {
        return linkImagemCurso;
    }
    public void setLinkImagemCurso(String linkImagemCurso) {
        this.linkImagemCurso = linkImagemCurso;
    }
    public String getAltImagem() {
        return altImagem;
    }
    public void setAltImagem(String altImagem) {
        this.altImagem = altImagem;
    }
}
