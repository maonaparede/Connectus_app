package com.example.telas_background.Classes_instanciadas;

public class Classe_conversa extends Classe_user_tela {

    //teste para fazer Filtro para pesquisar contatos

    private String path;
    private String ultimaMsg;

    public Classe_conversa(String foto, String id, String nome, String path, String ultimaMsg) {
        super(foto, id, nome);
        this.path = path;
        this.ultimaMsg = ultimaMsg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUltimaMsg() {
        return ultimaMsg;
    }

    public void setUltimaMsg(String ultimaMsg) {
        this.ultimaMsg = ultimaMsg;
    }
}
