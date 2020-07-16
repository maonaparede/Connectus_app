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

import com.example.telas_background.Classes_estaticas.User_principal;
import com.example.telas_background.Classes_instanciadas.Classe_chat_msg;
import com.example.telas_background.item.Item_chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {

    private RecyclerView chatRecycler;
    private GroupAdapter chatAdapter;
    private ImageView fotoChat;
    private TextView nomeChat;
    private EditText mensagem;
    private String idFriend;
    private String idUser;
    private String fotoUser;
    private String nomeUser;
    private String pathUser;

    private ListenerRegistration listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fotoChat = findViewById(R.id.fotoChat);
        nomeChat = findViewById(R.id.nomeChat);
        mensagem = findViewById(R.id.editText_chat);
        idUser = User_principal.getId();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            idFriend = bundle.getString("id");
            fotoUser = bundle.getString("foto");
            nomeUser = bundle.getString("nome");
            pathUser = bundle.getString("path");
        }

            Picasso.get().load(fotoUser).into(fotoChat);
            nomeChat.setText(nomeUser);

        chatRecycler = findViewById(R.id.recycler_chat);

        chatAdapter = new GroupAdapter();

        chatRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        chatRecycler.setAdapter(chatAdapter);

         //

       pegarMsgRealtime();
    }

    //liestener OnStart


    private void pegarMsgRealtime(){
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
                                    String nome;
                                    if(dc.getDocument().get("id").toString().equals(idUser)){
                                        nome = nomeUser;
                                    }else {
                                        nome = getString(R.string.voce);
                                    }
                                    Classe_chat_msg classeChatMsg = new Classe_chat_msg(nome , dc.getDocument().get("msg").toString(),
                                            dc.getDocument().get("id").toString());

                                    chatAdapter.add(new Item_chat(classeChatMsg));
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

    public void enviar(View v){

        String msg = mensagem.getText().toString();

        if(!msg.trim().equals("")) {

            Long time = System.currentTimeMillis();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference documentReference = db.collection(pathUser + "/mensagens").document();


            Map<String, Object> userSend = new HashMap<>();
            userSend.put("id", idFriend);
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

            mensagem.setText("");
        }
    }


    private void scrollRecyclerView(){
        chatRecycler.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
    }

    // trigger no app pra atualizar as 2 pastas dos users
}