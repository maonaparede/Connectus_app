package com.example.telas_background;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes_instanciadas.Classe_perfil_post;
import com.example.telas_background.Classes_instanciadas.Classe_user;
import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.item.Item_friend_request;
import com.example.telas_background.item.Item_post;
import com.example.telas_background.notificaHelper.NotificaHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class Friend_Request extends AppCompatActivity {

    private RecyclerView requestRecycler;
    private GroupAdapter requestAdapter;
    private String idUser;
    private Context context;
    private Button socializar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend__request);

        requestRecycler = findViewById(R.id.recycler_request);

        requestAdapter = new GroupAdapter();

        requestRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL , false));

        requestRecycler.setAdapter(requestAdapter);

        pegarRequest();

        context = this;


    }


    public void click(View view){
       // a requestAdapter.getItem()
    }


    private void pegarRequest(){

        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        FirebaseFirestore.getInstance().collection("request").
                document(idUser).collection("request").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                Classe_user_tela user = new Classe_user_tela(document.get("foto").toString()
                                        , document.get("id").toString(), document.get("nome").toString());

                                requestAdapter.add(new Item_friend_request(user));
                                // Log.d("Perfil" ,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("FriendRequest ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}