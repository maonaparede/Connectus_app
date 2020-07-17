package com.example.telas_background;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes_estaticas.User_principal;
import com.example.telas_background.Classes_instanciadas.Classe_conversa;
import com.example.telas_background.item.Item_conversas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.ArrayList;

public class Conversas extends AppCompatActivity {

    private ArrayList<Item_conversas> mExampleList;

    private RecyclerView conversasRecycler;
    private GroupAdapter conversasAdapter;
    private SearchView filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        conversasRecycler = findViewById(R.id.recycler_conversas);
        filter = findViewById(R.id.buscar_conversas);

        mExampleList = new ArrayList<Item_conversas>();

        conversasAdapter = new GroupAdapter();

        conversasRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL , false));

        conversasRecycler.setAdapter(conversasAdapter);

        conversasAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                Intent intent = new Intent(Conversas.this, Chat.class);
                Bundle bundle = new Bundle();

                Item_conversas conversas= (Item_conversas) item;
                bundle.putString("id", conversas.conversa.getId());
                bundle.putString("nome", conversas.conversa.getNome());
                bundle.putString("foto", conversas.conversa.getFoto());
                bundle.putString("path", conversas.conversa.getPath());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.trim().isEmpty()) {
                    filter(newText);
                }else{
                    filterReset();
                }
                return false;
            }
        });


        pegarTodosAmigos();
    }




    private void pegarTodosAmigos(){

        FirebaseFirestore.getInstance().collection("amigo").
                document(User_principal.getId()).collection("amigo").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                    pegarAmigo(document);
                                // Log.d("Perfil" ,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void pegarAmigo(final QueryDocumentSnapshot docAmigo) {
        DocumentReference docRef1 = FirebaseFirestore.getInstance().collection("user")
                .document(docAmigo.get("id").toString());

        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    Log.d("foto" , documentSnapshot.get("foto").toString());
                    pegarLastMsg( docAmigo.get("path").toString() , documentSnapshot);
                }
            }
        });
    }

    private void pegarLastMsg(final String path , final DocumentSnapshot document1){

        FirebaseFirestore.getInstance().collection(path+"/mensagens")
                .orderBy("tempo" , Query.Direction.DESCENDING).limit(1).get()

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                String msg = null;

                                for (QueryDocumentSnapshot document : task.getResult()){
                                    if ( document.get("msg") != null){
                                        msg = document.get("msg").toString();



                                    }else{
                                        msg = "...";
                                    }
                                }

                                final Classe_conversa classUser = new Classe_conversa(document1.get("foto").toString(),
                                        document1.get("id").toString(), document1.get("nome").toString() , path , msg);

                                mExampleList.add(new Item_conversas(classUser));

                                conversasAdapter.add(new Item_conversas(classUser));
                            }
                    }
                });

}


    private void filter(String text) {

        if (!text.isEmpty()) {

            ArrayList<Item_conversas> filteredList = new ArrayList<>();
            for (Item_conversas item : mExampleList) {
                if (item.getNome().toLowerCase().contains(text.toLowerCase())) {
                    Log.d("lista item", "top "+ item.getNome());
                    filteredList.add(item);
                }
            }
               conversasAdapter.update(filteredList);

        }
    }

    private void filterReset() {
        conversasAdapter.update(mExampleList);
    }

}