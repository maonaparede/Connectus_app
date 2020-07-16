package com.example.telas_background.Classes_instanciadas;

public class teste_conversa extends  Classe_user_tela {

    //teste para fazer Filtro para pesquisar contatos

    private String path;
    private String ultimaMsg;

    public teste_conversa(String foto, String id, String nome) {
        super(foto, id, nome);
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
