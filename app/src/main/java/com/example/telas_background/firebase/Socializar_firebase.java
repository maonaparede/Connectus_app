package com.example.telas_background.firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.notificaHelper.NotificaHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Socializar_firebase {

    private Classe_user_tela classUser;
    private String requestId;
    public Boolean verificar;
    final Map<String, Object> userSend = new HashMap<>();

    public Socializar_firebase(String requestId){
        this.requestId = requestId;
    }

    public Boolean enviar_request(final Context context){

        getPerfilSendRequest().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                NotificaHelper.mostrarToast(context , "Solicitação enviada");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                NotificaHelper.mostrarToast(context , "Solicitação Falhou");
            }
        })
        ;

        Log.d("verificarV" , String.valueOf(verificar));

        return verificar;
    }


    public Task<DocumentSnapshot> getPerfilSendRequest(){
        final String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DocumentReference docRefUser = FirebaseFirestore.getInstance().collection("user").document(idUser);

        return docRefUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    classUser = new Classe_user_tela(documentSnapshot.get("foto").toString(),
                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());


                    userSend.put("foto", classUser.getFoto());
                    userSend.put("id", classUser.getId());
                    userSend.put("nome", classUser.getNome());

                    final DocumentReference docRefRequest = FirebaseFirestore.getInstance().collection("request")
                            .document(requestId)
                            .collection("request").document(idUser);

                    docRefRequest.set(userSend).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            verificar = true;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            verificar = false;
                        }
                    });
                }
            }
        });
    }

}
