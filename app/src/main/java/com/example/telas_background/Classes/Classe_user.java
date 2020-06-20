package com.example.telas_background.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Classe_user implements Parcelable{

    private String foto;
    private String id;
    private String nome;

    public Classe_user(String foto, String id, String nome) {
        this.foto = foto;
        this.id = id;
        this.nome = nome;
    }

    private Classe_user(Parcel in) {
        foto = in.readString();
        id = in.readString();
        nome = in.readString();
    }

    public static final Parcelable.Creator<Classe_user> CREATOR = new Parcelable.Creator<Classe_user>() {
        @Override
        public Classe_user createFromParcel(Parcel in) {
            return new Classe_user(in);
        }

        @Override
        public Classe_user[] newArray(int size) {
            return new Classe_user[size];
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
