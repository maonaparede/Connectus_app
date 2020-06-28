package com.example.telas_background.Classes_instanciadas;

public class Classe_user {

    private String foto;
    private String id;
    private String nome;

    public Classe_user(String foto, String id, String nome) {
        this.foto = foto;
        this.id = id;
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
