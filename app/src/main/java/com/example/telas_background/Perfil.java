package com.example.telas_background;

import android.app.Dialog;
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
import com.example.telas_background.firebase.Socializar_firebase;
import com.example.telas_background.item.Item_perfil_perfil;
import com.example.telas_background.item.Item_post;
import com.example.telas_background.notificaHelper.NotificaHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private Button botaoSocializar;
    private Dialog dialog;

    private Classe_perfil_perfil perfil;
    private Classe_user classUser;
    private String idPerfil;
    private Integer estado_botao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfilRecycler = findViewById(R.id.recyclerP);
        botaoSocializar = findViewById(R.id.socializar_botao_perfil);

        perfilAdapter = new GroupAdapter();
        perfilRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        perfilRecycler.setAdapter(perfilAdapter);

        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            idPerfil = bundle.getString("user");
            estado_botao = bundle.getInt("estado");
        }else{
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

    public void verificarBotao(View v){
        if (estado_botao == 0){ //CriarPost
            startActivity(new Intent( this , CriarPost.class));
        }

        if (estado_botao == 1){ //Enviar Friend Request
            Socializar_firebase socializarFirebase = new Socializar_firebase(idPerfil);

            if(socializarFirebase.enviar_request()){
                NotificaHelper.mostrarToast(this , "Solicitação enviada");
                startActivity(new Intent( this , Home.class));
            }else{
                NotificaHelper.mostrarToast(this , "Solicitação não pode ser enviada");
                startActivity(new Intent( this , Home.class));
            }
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
