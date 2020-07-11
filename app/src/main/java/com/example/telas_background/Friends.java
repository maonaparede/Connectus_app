package com.example.telas_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.telas_background.Classes_estaticas.User_principal;
import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.firebase.Friend_request_firebase;
import com.example.telas_background.item.Item_amigo;
import com.example.telas_background.notificaHelper.NotificaHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;

public class Friends extends AppCompatActivity {

    private RecyclerView friendRecycler;
    private static GroupAdapter friendAdapter;
    private String idUser;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        friendRecycler = findViewById(R.id.recycler_amigo);

        friendAdapter = new GroupAdapter();

        friendRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL , false));

        friendRecycler.setAdapter(friendAdapter);

/*
        Classe_user_tela user = new Classe_user_tela( "https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327"
                , "30FT22MK5dVcJhEqYuBFUqNnPoo1", "teste ee");

        friendAdapter.add(new Item_friend_request(user));

 */

        pegarTodosAmigos();

        context = this;


    }

    public static void botaoItemRecycler(Item item , Integer estado , final Integer positon){

        Item_amigo pessoa = (Item_amigo) item;

        switch (estado){
            case 0:
                NotificaHelper.mostrarToast(context , "Socializar");

                break;
            case 1:
                NotificaHelper.mostrarToast(context , "Rejeitar");
                Friend_request_firebase.removeFriend(pessoa.user.getId());
                friendAdapter.removeGroup(positon);
                break;
            default:
                NotificaHelper.mostrarToast(context , "Perfil");
                Intent intent = new Intent(context, Perfil.class);
                Bundle bundle = new Bundle();


                bundle.putString("user", pessoa.user.getId());
                bundle.putInt("estado", 3);

                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
        }
    }


    private void pegarTodosAmigos(){

        FirebaseFirestore.getInstance().collection("amigo").
                document(User_principal.getId()).collection("amigo").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                               addItemAdapter(document);

                            }
                        } else {
                            Log.d("Amigo ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    private void addItemAdapter(final QueryDocumentSnapshot docpath){

        FirebaseFirestore.getInstance().collection("user").
                document(docpath.getId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Classe_user_tela user1 = new Classe_user_tela(documentSnapshot.get("foto").toString()
                                    ,documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                            friendAdapter.add(new Item_amigo(user1 , docpath.get("path").toString()));
                        }
                    }
                });
    }
}