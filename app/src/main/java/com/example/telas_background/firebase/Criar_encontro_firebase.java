package com.example.telas_background.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_estaticas.User_principal;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Criar_encontro_firebase {

    private String nome;
    private String descricao;
    private String data;
    private String local;
    private String horario;
    private Uri uri;
    private String urlfoto;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    public Criar_encontro_firebase(String nome, String descricao, String data, String local, String horario, Uri uri) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.horario = horario;
        this.uri = uri;
    }


    public void uparfotoEncontro(){

        if(uri == null){
            //v de vazio, void
            urlfoto = "v";
            createEncontro();
        }else {
            StorageReference mStorageRef;
            mStorageRef = FirebaseStorage.getInstance().getReference("/encontro/" + User_principal.getId());
            final StorageReference reference = mStorageRef.child(User_principal.getId());

            uploadTask = reference.putFile(uri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        urlfoto = task.getResult().toString();
                        createEncontro();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
    }


    private void createEncontro() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("encontro").document(User_principal.getId());


        DocumentReference atributos = documentReference.collection("atributos").document("atributos");


        Map<String, Object> userSend = new HashMap<>();
        userSend.put("nome", nome);
        userSend.put("descricao", descricao);
        userSend.put("dia", data);
        userSend.put("local", local);
        userSend.put("horario", horario);
        if(!urlfoto.equals("v")) {
            userSend.put("foto", urlfoto);
        }
        userSend.put("dono", User_principal.getId());


        atributos.set(userSend)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("EditarPerfil", "Pasta :success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("EditarPerfil", "Pasta :Failuire");
                    }
                });


    }



    }

