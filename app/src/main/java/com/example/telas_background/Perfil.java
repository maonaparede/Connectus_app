package com.example.telas_background;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes_instanciadas.Classe_perfil_perfil;
import com.example.telas_background.Classes_instanciadas.Classe_perfil_post;
import com.example.telas_background.Classes_instanciadas.Classe_user;
import com.example.telas_background.item.Item_perfil_perfil;
import com.example.telas_background.item.Item_post;
import com.example.telas_background.utils_helper.MakeToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;

public class Perfil extends AppCompatActivity {

    private RecyclerView perfilRecycler;
    private GroupAdapter perfilAdapter;
    private String idUser;
    private Uri uri;

    public Button botaoSocializar;
    private Dialog dialog;

    private Classe_perfil_perfil perfil;
    private Classe_user classUser;
    private String idPerfil;
    private Integer estado_botao = 0;
    private static Context context1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfilRecycler = findViewById(R.id.recyclerP);
        botaoSocializar = findViewById(R.id.socializar_botao_perfil);

        perfilAdapter = new GroupAdapter();
        perfilRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        perfilRecycler.setAdapter(perfilAdapter);

        context1 = this;

        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            idPerfil = bundle.getString("user");
            estado_botao = bundle.getInt("estado");
            
        }

        if(idUser.equals(idPerfil)){
            estado_botao = 0;
            idPerfil = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        }

         switch (estado_botao){
             case 0:
                 botaoSocializar.setText("Criar Post");
                 break;
             case 1:
                 botaoSocializar.setText("Socializar");
                 break;
             default:
                 botaoSocializar.setVisibility(View.INVISIBLE);
                 break;
         }
        //perfil
       pegarPerfil();
    }

    public void verificarBotao(View v) {

        switch (estado_botao) {
            case 0:
                //Caso for dono do perfil CriarPost
                startActivity(new Intent(this, CriarPost.class));
                break;
            case 1:
                //Caso não for amigo Enviar Friend Request
                //botaoSocializar.setClickable(false);
                botaoSocializar.setVisibility(View.INVISIBLE);
                //Friend_request_firebase.sendFriendRequest(idPerfil);
                MakeToast.makeToast(this , "Teste");
                break;
            default:
                botaoSocializar.setVisibility(View.INVISIBLE);
                break;
            }
        }


    private void pegarPerfil(){

        Log.d("IDzao" , "id " + idPerfil);

        //user
        DocumentReference docRef1 = FirebaseFirestore.getInstance().collection("user").document(idPerfil);
        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()) {
                    classUser = new Classe_user(documentSnapshot.get("foto").toString(),
                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                    //perfil
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("perfil").document(idPerfil);
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            perfil = documentSnapshot.toObject(Classe_perfil_perfil.class);
                            perfilAdapter.add( new Item_perfil_perfil( classUser , perfil));
                            pegarPosts();
                        }
                    });
                }
                }
        });




    }

    private void pegarPosts(){

        FirebaseFirestore.getInstance().collection("post").
                document(idPerfil).collection("post").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                Classe_perfil_post post = new Classe_perfil_post(document.get("imagemPost").toString()
                                        , document.get("descricao").toString(), document.get("comentarios").toString());

                                perfilAdapter.add(new Item_post(classUser.getNome() , classUser.getFoto(), post));
                               // Log.d("Perfil" ,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
