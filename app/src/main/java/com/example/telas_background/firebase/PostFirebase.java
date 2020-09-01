package com.example.telas_background.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassPerfilPost;
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

public class PostFirebase {

    private FirebaseAuth mAuth;
    private String urlImage;
    private ClassPerfilPost post;
    private String id;
    private Uri uri;

    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    public PostFirebase(ClassPerfilPost post, Uri uri) {
        this.post = post;
        this.uri = uri;

        mAuth = FirebaseAuth.getInstance();
        id = mAuth.getCurrentUser().getUid();
    }


    public void uploadImagePost(){
        //se a foto for Nula cria o post sem ft
        if(uri == null){
            urlImage = "v";
            createPost();
        }else{
            String nameImage = String.valueOf(System.currentTimeMillis());

            StorageReference mStorageRef;
            mStorageRef = FirebaseStorage.getInstance().getReference("/post/" + id);
            final StorageReference reference = mStorageRef.child(nameImage);

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
                        createPost();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
    }


    private void createPost(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("post").document(id)
                .collection("post").document();

        Map<String, Object> userSend = new HashMap<>();
        userSend.put("descricao", post.getDescription());
        userSend.put("imagemPost", urlImage);

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
