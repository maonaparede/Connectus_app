package com.example.telas_background.Classes;

import com.example.telas_background.Classes_instanciadas.Classe_perfil_perfil;
import com.example.telas_background.Classes_instanciadas.Classe_user;
import com.example.telas_background.item.Item_perfil_perfil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class User_principal {

    private static String foto;
    private static String id;
    private static String nome;


    public static void userPrincipal_user(){
        final String idPerfil = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

    DocumentReference docRef1 = FirebaseFirestore.getInstance().collection("user").document(idPerfil);
        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                        setFoto(documentSnapshot.get("foto").toString());
                         setNome(documentSnapshot.get("nome").toString());

                }
            }
        });
    }

    public static String getFoto() {
        return foto;
    }

    public static String getId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }

    public static String getNome() {
        return nome;
    }

    private static void setFoto(String foto) {
        User_principal.foto = foto;
    }

    private static void setId(String id) {
        User_principal.id = id;
    }

    private static void setNome(String nome) {
        User_principal.nome = nome;
    }
}
