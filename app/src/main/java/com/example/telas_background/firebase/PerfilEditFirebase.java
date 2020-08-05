package com.example.telas_background.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class PerfilEditFirebase {

    private FirebaseAuth mAuth;
    private Uri uri;
    private String urlImage;
    private String id;
    private String name;
    private String description;
    private String hobbie1;
    private String hobbie2;
    private String hobbie3;
    private String hobbie4;
    private String hobbie5;
    private String hobbie6;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    public PerfilEditFirebase(Uri uri, String name, String description, String hobbie1,
                              String hobbie2, String hobbie3, String hobbie4, String hobbie5, String hobbie6) {
        this.uri = uri;
        this.name = name;
        this.description = description;
        this.hobbie1 = hobbie1;
        this.hobbie2 = hobbie2;
        this.hobbie3 = hobbie3;
        this.hobbie4 = hobbie4;
        this.hobbie5 = hobbie5;
        this.hobbie6 = hobbie6;

        mAuth = FirebaseAuth.getInstance();
        id = mAuth.getCurrentUser().getUid().toString();
    }

    public void uploadPerfilImage(){

        if(uri == null){
            urlImage = "v";
            createUserFirestore();
        }else {

            StorageReference mStorageRef;
            mStorageRef = FirebaseStorage.getInstance().getReference("/user/" + id);
            final StorageReference reference = mStorageRef.child(id);

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
                        urlImage = task.getResult().toString();
                        createUserFirestore();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
    }


    private void createUserFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("perfil").document(id);


        Map<String, Object> userSend = new HashMap<>();
        userSend.put("descricao", description);
        userSend.put("h1", hobbie1);
        userSend.put("h2", hobbie2);
        userSend.put("h3", hobbie3);
        userSend.put("h4", hobbie4);
        userSend.put("h5", hobbie5);
        userSend.put("h6", hobbie6);

        documentReference.set(userSend)
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


        DocumentReference documentReference1 = db.collection("user").document(id);

        Map<String, Object> userSend1 = new HashMap<>();
        userSend1.put("id", id);
        userSend1.put("nome", name);
        userSend1.put("foto", urlImage);

        documentReference1.set(userSend1)
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
