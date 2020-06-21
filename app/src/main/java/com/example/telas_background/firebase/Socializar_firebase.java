package com.example.telas_background.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes.Classe_user;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Socializar_firebase {

    private Classe_user classUser;
    private String uidUser;

    public Socializar_firebase(String uidUser){
        this.uidUser = uidUser;
    }

    public  void enviar_request(){

        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        DocumentReference docRef1 = FirebaseFirestore.getInstance().collection("user").document(idUser);
        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                 classUser = new Classe_user(documentSnapshot.get("foto").toString() ,
                        documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("socializar").document(uidUser)
                .collection("post").document(idUser);

        Map<String, Object> userSend = new HashMap<>();
        userSend.put("foto", classUser.getFoto());
        userSend.put("id", classUser.getId());
        userSend.put("nome", classUser.getNome());

        documentReference.set(userSend)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Socializar", "Pasta :success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Socializar", "Pasta :Failuire");
                    }
                });
    }

}
