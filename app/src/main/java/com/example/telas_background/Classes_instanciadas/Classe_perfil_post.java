package com.example.telas_background.Classes_instanciadas;

public class Classe_perfil_post {

    private String imagemPost;
    private String descricao;
    private String comentarios;

    public Classe_perfil_post(String imagemPost, String descricao, String comentarios) {
        this.imagemPost = imagemPost;
        this.descricao = descricao;
        this.comentarios = comentarios;
    }

    public String getImagemPost() {
        return imagemPost;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getComentarios() {
        return comentarios;
    }
}
