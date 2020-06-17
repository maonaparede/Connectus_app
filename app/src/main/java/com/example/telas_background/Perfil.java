package com.example.telas_background;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes.Classe_perfil_perfil;
import com.example.telas_background.Classes.Classe_perfil_post;
import com.example.telas_background.item.item_perfil_perfil;
import com.example.telas_background.item.item_post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;

import java.util.List;

public class Perfil extends AppCompatActivity {

    private RecyclerView perfilRecycler;
    private GroupAdapter perfilAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button botaoSocializar;
    private String idUser;
    private Classe_perfil_perfil perfil;
    private Integer estado_botao = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfilRecycler = findViewById(R.id.recyclerP);
        botaoSocializar = findViewById(R.id.socializar_botao_perfil);

        perfilAdapter = new GroupAdapter();
        perfilRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        perfilRecycler.setAdapter(perfilAdapter);


        //idUser = "7n7N1guVTYcQOAlHuqrbKyUYCDi2";
        idUser = "OTt1kXDrYBNGoAvLIN5xToGDYZH2";

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

         if(user.getUid().equals(idUser)){
            botaoSocializar.setText("Criar Post");
            estado_botao = 0;
         }

        //perfil
        pegarPerfil();


        //post
        /*
        perfilAdapter.add(new item_post("Meu pai",
                "ft de Balada 44"
                , "5 Comentários"
                , "https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318"
                , "https://cdna.artstation.com/p/assets/images/images/025/734/778/large/z-w-gu-thronef3handfixweb.jpg?1586776245"
        ));

         */
    }

    public void verificarBotao(View v){
        if (estado_botao == 0){ //CriarPost

        }
        if (estado_botao == 1){ //Enviar Friend Request

        }

    }


    private void pegarPerfil(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Perfil").document(idUser);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                perfil = documentSnapshot.toObject(Classe_perfil_perfil.class);
                perfilAdapter.add( new item_perfil_perfil (perfil));
            }
        });

        pegarPosts();
    }

    private void pegarPosts() {


        FirebaseFirestore.getInstance().collection("post").
                document(idUser).collection("post").get()

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                Classe_perfil_post post = new Classe_perfil_post(document.get("imagemPost").toString()
                                        , document.get("descricao").toString(), document.get("comentarios").toString());

                                perfilAdapter.add(new item_post(perfil.getNome() , perfil.getFoto(), post));
                                Log.d("TAG," ,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
