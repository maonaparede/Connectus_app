package com.example.telas_background;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.instanceClasses.ClassChatMsg;
import com.example.telas_background.item.Item_chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {

    private RecyclerView recycler;
    private GroupAdapter adapter;
    private ImageView imageView;
    private TextView nameChat;
    private EditText msg;
    private String idUser;
    private String imageUser;
    private String nameUser;
    private String pathUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        imageView = findViewById(R.id.chat_imageview_user);
        nameChat = findViewById(R.id.chat_textview_name);
        msg = findViewById(R.id.chat_edittext_msg);
        idUser = UserPrincipal.getId();


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            imageUser = bundle.getString("foto");
            nameUser = bundle.getString("nome");
            pathUser = bundle.getString("path");

            if(!imageUser.isEmpty()) {
                Picasso.get().load(imageUser).into(imageView);
            }else{
                Picasso.get().load(R.drawable.connect_us_icon).into(imageView);
            }
            nameChat.setText(nameUser);
        }

        recycler = findViewById(R.id.chat_recyclerview);
        adapter = new GroupAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);

        getfMsgRealtime();
    }


    private void getfMsgRealtime(){
            FirebaseFirestore.getInstance().collection(pathUser+"/mensagens")
                .orderBy("tempo" , Query.Direction.ASCENDING)
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
                                    ClassChatMsg classChatMsg = new ClassChatMsg(dc.getDocument().get("nome").toString()
                                            , dc.getDocument().get("msg").toString(),
                                            dc.getDocument().get("id").toString());

                                    adapter.add(new Item_chat(classChatMsg));
                                    scrollRecyclerView();
                                    break;
                                case MODIFIED:
                                    Log.d("TAG", "Modified city: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:
                                    Log.d("TAG", "Removed city: " + dc.getDocument().getData());
                                    break;
                            }
                        }
                    }
                });
    }

    public void sendMsg(View v){
        String msg = this.msg.getText().toString();

        if(!msg.trim().equals("")) {

            Long time = System.currentTimeMillis();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference documentReference = db.collection(pathUser + "/mensagens").document();

            Map<String, Object> userSend = new HashMap<>();
            userSend.put("id", idUser);
            userSend.put("nome", UserPrincipal.getName());
            userSend.put("msg", msg);
            userSend.put("tempo", time);

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

            this.msg.setText("");
        }
    }


    private void scrollRecyclerView(){
        recycler.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    public void backScreen(View view){
        finish();
    }
}