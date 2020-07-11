package com.example.telas_background.Classes_estaticas;

import com.google.firebase.auth.FirebaseAuth;

public class User_principal {

    private static String foto;
    private static String nome;


    public static String getFoto() {
        return foto;
    }

    public static String getId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }

    public static String getNome() {
        return nome;
    }

    public static void setFoto(String foto) {
        User_principal.foto = foto;
    }


    public static void setNome(String nome) {
        User_principal.nome = nome;
    }
}
