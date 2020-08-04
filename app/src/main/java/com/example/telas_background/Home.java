package com.example.telas_background;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes_estaticas.User_principal;
import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.item.Item_home_encontros;
import com.example.telas_background.item.Item_home_pessoas;
import com.example.telas_background.utils_helper.DialogEncontroAddRemove;
import com.example.telas_background.utils_helper.DialogRemoveConfirmation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class Home extends AppCompatActivity {

    private RecyclerView encontrosRecycler;
    private GroupAdapter encontrosAdapter;

    private RecyclerView pessoasRecycler;
    private GroupAdapter pessoasAdapter;
    private static Context context;
    Uri uri;
    String urlFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        encontrosRecycler = findViewById(R.id.recyclerViewEncontro);
        pessoasRecycler = findViewById(R.id.recyclerViewPessoas);


        encontrosAdapter = new GroupAdapter();
        pessoasAdapter = new GroupAdapter();

        encontrosRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.HORIZONTAL , false));
        pessoasRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.HORIZONTAL , false));

        encontrosRecycler.setAdapter(encontrosAdapter);
        pessoasRecycler.setAdapter(pessoasAdapter);

        pessoasAdapter.add(new Item_home_pessoas(
                new Classe_user_tela("https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327" , "sPDdPbX7juTtGUDTN95uMlTEjes1" , "")));

        context = this;
        pegarTodosEncontros();
    }

    public void criarEncontro(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("estado" , 0);
        Intent intent = new Intent(this, Editar_encontro.class);
        intent.putExtras(bundle);
        startActivity(intent);

        startActivity(intent);
    }



    public void perfil(View view){

        //PopUp.createDialogOkCancel(this, "Porque?", "Você vai cortar relações com esse usuário")

        //DialogEncontroAddRemove.createDialogOkAddRemove(context , "a " , 0);
        startActivity(new Intent(this , Editar_encontro.class));
    }

    private void pegarTodosEncontros(){


        FirebaseFirestore.getInstance().collection("caminho_encontro").
                document(User_principal.getId()).collection("encontro").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                pegarEncontro(document);
                              //   Log.d("Perfil" ,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("Home ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void pegarEncontro(final QueryDocumentSnapshot docA) {

        DocumentReference docRef1 = FirebaseFirestore.getInstance().collection("encontro")
                .document(docA.get("dono").toString()).collection("atributos")
                .document("atributos");

        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                //    Log.d("foto" , documentSnapshot.get("foto").toString());

                   Item_home_encontros item = new Item_home_encontros(documentSnapshot.get("nome").toString()
                                    , documentSnapshot.get("foto").toString(), docA.get("path").toString(),
                           documentSnapshot.get("dono").toString());

                    encontrosAdapter.add(item);



                   // pegarLastMsg( docAmigo.get("path").toString() , documentSnapshot);
                }
            }
        });
    }


    public static void telaEncontro(Item item){
        Intent intent = new Intent(context, Encontro.class);
        Bundle bundle = new Bundle();

        Item_home_encontros encontro = (Item_home_encontros) item;
        bundle.putString("dono" , encontro.getDono());

        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void telaPerfil(Item item){
        Intent intent = new Intent(context, Request.class);
        Bundle bundle = new Bundle();

        Item_home_pessoas pessoas = (Item_home_pessoas) item;
        bundle.putString("user", pessoas.user.getId().toString());
        bundle.putInt("estado", 1);

        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
