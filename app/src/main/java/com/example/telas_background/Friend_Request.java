package com.example.telas_background;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.firebase.Friend_request_firebase;
import com.example.telas_background.item.Item_friend_request;
import com.example.telas_background.utils_helper.MakeToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;

public class Friend_Request extends AppCompatActivity{

    private RecyclerView requestRecycler;
    private static GroupAdapter requestAdapter;
    private String idUser;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend__request);

        requestRecycler = findViewById(R.id.recycler_request);

        requestAdapter = new GroupAdapter();

        requestRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL , false));

        requestRecycler.setAdapter(requestAdapter);


       /* Classe_user_tela user = new Classe_user_tela( "https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327"
                , "30FT22MK5dVcJhEqYuBFUqNnPoo1", "teste ee");

        requestAdapter.add(new Item_friend_request(user)); */

        pegarRequest();

        context = this;


    }


    public static void botaoItemRecycler(Item item , Integer estado , final Integer positon){

        Item_friend_request pessoa = (Item_friend_request) item;
        switch (estado){
            case 0:
                MakeToast.makeToast(context , "Socializar");
                Friend_request_firebase.friendAddFirebaseFunctionCall(pessoa.user.getId());
                requestAdapter.removeGroup(positon);
                break;
            case 1:
                MakeToast.makeToast(context , "Rejeitar");
                Friend_request_firebase.denyRequest(pessoa.user.getId());
                requestAdapter.removeGroup(positon);
                break;
            default:
                MakeToast.makeToast(context , "Perfil");
                Intent intent = new Intent(context, Perfil.class);
                Bundle bundle = new Bundle();


                bundle.putString("user", pessoa.user.getId());
                bundle.putInt("estado", 3);

                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
        }
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