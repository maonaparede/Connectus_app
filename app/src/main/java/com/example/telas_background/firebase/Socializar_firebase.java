package com.example.telas_background.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Socializar_firebase {

    private Classe_user_tela classUser;
    private String requestId;


    public Socializar_firebase(String requestId){
        this.requestId = requestId;
    }

    public Boolean enviar_request(){

        final Boolean[] verificar = new Boolean[1];

        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        //
        final DocumentReference docRefRequest = FirebaseFirestore.getInstance().collection("request").document(requestId)
                .collection("request").document(idUser);


        DocumentReference docRefUser = FirebaseFirestore.getInstance().collection("user").document(idUser);

        docRefUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    classUser = new Classe_user_tela(documentSnapshot.get("foto").toString(),
                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                    Map<String, Object> userSend = new HashMap<>();
                    userSend.put("foto", classUser.getFoto());
                    userSend.put("id", classUser.getId());
                    userSend.put("nome", classUser.getNome());

                    docRefRequest.set(userSend)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i("Socializar", "Pasta :success");

                                    verificar[0] = true;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Socializar", "Pasta :Failuire");

                                    verificar[0] = false;
                                }
                            });
                }
            }
        });





        return verificar[0];

    }

}
