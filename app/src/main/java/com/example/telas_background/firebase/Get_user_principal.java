package com.example.telas_background.firebase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.telas_background.Classes_estaticas.User_principal.setFoto;
import static com.example.telas_background.Classes_estaticas.User_principal.setNome;

public class Get_user_principal {

    public Get_user_principal() {
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
}
