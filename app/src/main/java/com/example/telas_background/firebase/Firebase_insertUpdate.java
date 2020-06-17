package com.example.telas_background.firebase;

import android.net.Uri;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.UUID;

public class Firebase_insertUpdate {

    private FirebaseAuth mAuth;
    private Uri uri;
    private String urlfoto;
    private String uidUser;
    private String pasta;
    private String subPasta;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    private Map<String, Object> userSend;

    public Firebase_insertUpdate(Uri uri, String pasta, String subPasta, Map<String, Object> userSend) {
        this.uri = uri;
        this.pasta = pasta;
        this.subPasta = subPasta;
        this.userSend = userSend;
    }




}
