package com.example.telas_background.Classes_instanciadas;

import android.widget.ImageView;
import android.widget.TextView;

public class Classe_encontro_request {

    private String nome;
    private String dono;
    private String local;
    private String data;
    private String horario;
    private String imagem;

    public Classe_encontro_request(String nome, String dono, String local, String data, String horario, String imagem) {
        this.nome = nome;
        this.dono = dono;
        this.local = local;
        this.data = data;
        this.horario = horario;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
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
}
