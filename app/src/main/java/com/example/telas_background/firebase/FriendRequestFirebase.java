package com.example.telas_background.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.telas_background.initialize.UserPrincipal;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class FriendRequestFirebase {

    public static Task<String> friendAddFirebaseFunctionCall(String user2) {

        FirebaseFunctions mFunctions;
        mFunctions = FirebaseFunctions.getInstance();

        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("user", user2);

        return mFunctions
                .getHttpsCallable("acceptFriendRequest")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }

    public static void denyRequest(String user){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("request").document(UserPrincipal.getId())
                .collection("request").document(user);

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

    public static Task<String> sendFriendRequest(String user) {

       FirebaseFunctions mFunctions;
       mFunctions = FirebaseFunctions.getInstance();

       // Create the arguments to the callable function.
       Map<String, Object> data = new HashMap<>();
       data.put("user", user);

       return mFunctions
               .getHttpsCallable("sendFriendRequest")
               .call(data)
               .continueWith(new Continuation<HttpsCallableResult, String>() {
                   @Override
                   public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                       // This continuation runs on either success or failure, but if the task
                       // has failed then getResult() will throw an Exception which will be
                       // propagated down.
                       String result = (String) task.getResult().getData();
                       return result;
                   }
               });
   }

    public static Task<String> removeFriend(String user){
        Log.d("send", "entrou");

        FirebaseFunctions mFunctions;
        mFunctions = FirebaseFunctions.getInstance();

        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);

        return mFunctions
                .getHttpsCallable("removeFriend")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }

    }
