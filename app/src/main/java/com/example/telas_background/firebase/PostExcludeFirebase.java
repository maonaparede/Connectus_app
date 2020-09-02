package com.example.telas_background.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.telas_background.initialize.UserPrincipal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostExcludeFirebase {
    public void excludePost(String post){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("post").document(UserPrincipal.getId())
                .collection("post").document(post);

        documentReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("denyRequest", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("denyRequest", "Error deleting document", e);
                    }
                });

    }
}
