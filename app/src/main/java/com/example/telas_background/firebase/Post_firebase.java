package com.example.telas_background.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_perfil_post;
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

public class Post_firebase {

    private FirebaseAuth mAuth;
    private String urlfoto;
    private Classe_perfil_post post;
    private String uidUser;
    private Uri uri;

    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    public Post_firebase(Classe_perfil_post post, Uri uri) {
        this.post = post;
        this.uri = uri;

        mAuth = FirebaseAuth.getInstance();
        uidUser = mAuth.getCurrentUser().getUid().toString();
    }


    public void uparFotoCriarPost(){
        //se a foto for Nula cria o post sem ft
        if(uri == null){
            criarPost();
        }else{
            StorageReference mStorageRef;
            mStorageRef = FirebaseStorage.getInstance().getReference("/post/" + uidUser);
            final StorageReference reference = mStorageRef.child(uidUser);

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
                        criarPost();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
    }


    private void criarPost(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("post").document(uidUser)
                .collection("post").document();

        Map<String, Object> userSend = new HashMap<>();
        userSend.put("descricao", post.getDescricao());
        userSend.put("imagemPost", urlfoto);
        userSend.put("comentarios", post.getComentarios());

        documentReference.set(userSend)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Post", "Pasta :success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Post", "Pasta :Failuire");
                    }
                });
    }
}
