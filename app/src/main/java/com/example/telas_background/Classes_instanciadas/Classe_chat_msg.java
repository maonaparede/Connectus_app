package com.example.telas_background.Classes_instanciadas;

public class Classe_chat_msg {

    private String nome;
    private String mensagem;
    private String id;

    public Classe_chat_msg(String nome, String mensagem, String id) {
        this.nome = nome;
        this.mensagem = mensagem;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getId() {
        return id;
    }
}
