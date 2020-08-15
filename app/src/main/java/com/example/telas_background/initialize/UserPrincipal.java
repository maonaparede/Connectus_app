package com.example.telas_background.initialize;

import com.google.firebase.auth.FirebaseAuth;

public class UserPrincipal {

    private static String foto;
    private static String nome;


    public static String getFoto() {
        return foto;
    }

    public static String getId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static String getNome() {
        return nome;
    }

    public static void setFoto(String foto){
        UserPrincipal.foto = foto;
    }


    public static void setNome(String nome) {
        UserPrincipal.nome = nome;
    }
}
