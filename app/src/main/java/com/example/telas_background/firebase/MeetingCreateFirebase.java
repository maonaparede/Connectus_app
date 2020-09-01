package com.example.telas_background.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.telas_background.initialize.UserPrincipal;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class MeetingCreateFirebase {

    private String name;
    private String description;
    private String day;
    private String local;
    private String hour;
    private Uri uri;
    private String urlImage;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    public MeetingCreateFirebase(String name, String description, String day, String local, String hour, Uri uri) {
        this.name = name;
        this.description = description;
        this.day = day;
        this.local = local;
        this.hour = hour;
        this.uri = uri;
    }


    public void uploadImageMeeting(){

        if(uri == null){
            createMeeting();
        }else {
            StorageReference mStorageRef;
            mStorageRef = FirebaseStorage.getInstance().getReference("/encontro/" + UserPrincipal.getId());
            final StorageReference reference = mStorageRef.child(UserPrincipal.getId());

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
                        createMeeting();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
    }


    private void createMeeting() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("encontro").document(UserPrincipal.getId());


        DocumentReference atributos = documentReference.collection("atributos").document("atributos");


        Map<String, Object> userSend = new HashMap<>();
        userSend.put("nome", name);
        userSend.put("descricao", description);
        userSend.put("dia", day);
        userSend.put("local", local);
        userSend.put("horario", hour);
        if(urlImage != null) {
            userSend.put("foto", urlImage);
        }else{
            userSend.put("foto", "");
        }
        userSend.put("dono", UserPrincipal.getId());


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

