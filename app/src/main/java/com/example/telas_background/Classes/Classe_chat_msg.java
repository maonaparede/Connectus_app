package com.example.telas_background.Classes;

public class Classe_chat_msg {

    private String nome;
    private String mensagem;
    private String uid;

    public Classe_chat_msg(String nome, String mensagem, String uid) {
        this.nome = nome;
        this.mensagem = mensagem;
        this.uid = uid;
    }


    public String getNome() {
        return nome;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getUid() {
        return uid;
    }
}
