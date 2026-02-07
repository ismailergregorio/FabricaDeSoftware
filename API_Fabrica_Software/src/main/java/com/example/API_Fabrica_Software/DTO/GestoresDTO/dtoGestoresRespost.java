package com.example.API_Fabrica_Software.DTO.GestoresDTO;

import java.util.List;
import java.util.stream.Collectors;


import com.example.API_Fabrica_Software.Model.ClassGestores;
import com.example.API_Fabrica_Software.Model.ClassProjetos;

public class dtoGestoresRespost {
    private String codigoGestor;
    private String name;
    private String descricao;
    private String cursoResposavel;
    private String linkImagenGestor;
    private List<String> projetos;

    public dtoGestoresRespost() {
    }

    public dtoGestoresRespost(String codigoGestor, String name, String descricao, String cursoResposavel,
            String linkImagenGestor, List<String> projetos) {
        this.codigoGestor = codigoGestor;
        this.name = name;
        this.descricao = descricao;
        this.cursoResposavel = cursoResposavel;
        this.linkImagenGestor = linkImagenGestor;
        this.projetos = projetos;
    }

    public dtoGestoresRespost(ClassGestores gestor) {
        this.codigoGestor = gestor.getCodigoGestor();
        this.name = gestor.getName();
        this.descricao = gestor.getDescricao();
        this.cursoResposavel = gestor.getCursoResposavel();
        this.linkImagenGestor = gestor.getLinkImagenGestor();
        this.projetos = gestor.getProjetos().stream()
                .map(ClassProjetos::getCodigoProjeto)
                .collect(Collectors.toList());
    }

    public String getCodigoGestor() {
        return codigoGestor;
    }

    public void setCodigoGestor(String codigoGestor) {
        this.codigoGestor = codigoGestor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCursoResposavel() {
        return cursoResposavel;
    }

    public void setCursoResposavel(String cursoResposavel) {
        this.cursoResposavel = cursoResposavel;
    }

    public String getLinkImagenGestor() {
        return linkImagenGestor;
    }

    public void setLinkImagenGestor(String linkImagenGestor) {
        this.linkImagenGestor = linkImagenGestor;
    }

    public List<String> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<String> projetos) {
        this.projetos = projetos;
    }

}
