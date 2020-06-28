package com.example.telas_background.Classes_instanciadas;

import android.os.Parcel;
import android.os.Parcelable;

public class Classe_user_tela implements Parcelable{

    private String foto;
    private String id;
    private String nome;

    public Classe_user_tela(String foto, String id, String nome) {
        this.foto = foto;
        this.id = id;
        this.nome = nome;
    }

    private Classe_user_tela(Parcel in) {
        foto = in.readString();
        id = in.readString();
        nome = in.readString();
    }

    public static final Parcelable.Creator<Classe_user_tela> CREATOR = new Parcelable.Creator<Classe_user_tela>() {
        @Override
        public Classe_user_tela createFromParcel(Parcel in) {
            return new Classe_user_tela(in);
        }

        @Override
        public Classe_user_tela[] newArray(int size) {
            return new Classe_user_tela[size];
        }
    };

    public String getFoto() {
        return foto;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foto);
        dest.writeString(id);
        dest.writeString(nome);
    }
}
