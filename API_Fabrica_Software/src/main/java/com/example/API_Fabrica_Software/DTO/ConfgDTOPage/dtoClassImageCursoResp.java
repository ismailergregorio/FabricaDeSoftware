package com.example.API_Fabrica_Software.DTO.ConfgDTOPage;

import com.example.API_Fabrica_Software.Model.ClassConfigPage.ClassImageCursos;

public class dtoClassImageCursoResp {
    private String codigoImagem;
    private String linkImagemCurso;
    private String altImagem;

    public dtoClassImageCursoResp(ClassImageCursos imagenCurso) {
        this.codigoImagem = imagenCurso.getCodigoImagem();
        this.linkImagemCurso = imagenCurso.getLinkImagemCurso();
        this.altImagem = imagenCurso.getAltImagem();
    }

    public dtoClassImageCursoResp() {

    }

    public String getCodigoImagem() {
        return codigoImagem;
    }

    public void setCodigoImagem(String codigoImagem) {
        this.codigoImagem = codigoImagem;
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
