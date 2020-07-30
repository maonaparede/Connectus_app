package com.example.telas_background.firebase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Get_user_by_id {

    private static String name;

    public static void getUserNameById(String id){

        DocumentReference docRef1 = FirebaseFirestore.getInstance().collection("user").document(id);
        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    setName(documentSnapshot.get("nome").toString());
                }
            }
        });
    }


    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Get_user_by_id.name = name;
    }
}
