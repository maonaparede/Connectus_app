package com.example.telas_background.Classes_instanciadas;

public class Classe_encontro_request {

    private String titulo;
    private String dono;
    private String local;
    private String data;
    private String horario;
    private String imagem;
    private String nome;

    public Classe_encontro_request(String titulo, String dono, String local, String data, String horario, String imagem, String nome) {
        this.titulo = titulo;
        this.dono = dono;
        this.local = local;
        this.data = data;
        this.horario = horario;
        this.imagem = imagem;
        this.nome = nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDono() {
        return dono;
    }

    public String getLocal() {
        return local;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }

    public String getImagem() {
        return imagem;
    }

    public String getNome() {
        return nome;
    }
}
