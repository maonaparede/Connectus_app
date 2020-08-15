package com.example.telas_background.initialize;

import android.content.Context;
import android.util.Log;

import com.example.telas_background.sqlite.FriendsSqlController;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Initialize {

    private Context context;


    public Initialize Initialize(Context context1) {


        return this;
    }

    public Initialize sql(Context context1) {
        context = context1;
        return this;
    }

    public void friendListener(){
        final FriendsSqlController friendsSqlController = new FriendsSqlController(context);

        Integer rows = friendsSqlController.excludeAll();
        Log.d("DB exclude rows " , rows + "");
        //logica

        FirebaseFirestore.getInstance().collection( "amigo/" + UserPrincipal.getId() + "/amigo")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "listen:error", e);
                            return;
                        }
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:

                                   String idFriend = dc.getDocument().get("id").toString();
                                    friendsSqlController.insert(idFriend);
                                    break;
                                case MODIFIED:
                                    Log.d("TAG", "Modified city: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:

                                    String idFriend1 = dc.getDocument().get("id").toString();
                                    friendsSqlController.excludeById(idFriend1);
                                    break;
                            }
                        }
                    }
                });

    }

    public void meetingListener(){

    }
}
