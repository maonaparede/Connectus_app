package com.example.telas_background;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.instanceClasses.ClassPerfilPerfil;
import com.example.telas_background.instanceClasses.ClassPerfilPost;
import com.example.telas_background.instanceClasses.ClassUser;
import com.example.telas_background.firebase.FriendRequestFirebase;
import com.example.telas_background.item.Item_perfil_perfil;
import com.example.telas_background.item.Item_post;
import com.example.telas_background.dialog_toast.MakeToast;
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

    private RecyclerView recycler;
    private GroupAdapter adapter;
    public Button buttonConnect;

    private String idUser;
    private ClassPerfilPerfil perfil;
    private ClassUser classUser;
    private String idPerfil;
    private Integer buttonConnectstate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        recycler = findViewById(R.id.recyclerP);
        buttonConnect = findViewById(R.id.socializar_botao_perfil);

        adapter = new GroupAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);

        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
         Bundle bundle = getIntent().getExtras();

         if(idUser.equals(idPerfil)){
            buttonConnectstate = 0;
            idPerfil = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
         }else
        if(bundle != null) {
            idPerfil = bundle.getString("user");
            buttonConnectstate = bundle.getInt("estado");

        }else{
            idPerfil = idUser;
        }


         switch (buttonConnectstate){
             case 0:
                 buttonConnect.setText("Criar Post");
                 break;
             case 1:
                 buttonConnect.setText("Socializar");
                 break;
             default:
                 buttonConnect.setVisibility(View.INVISIBLE);
                 break;
         }

       getfPerfil();
    }


    private void getfPerfil(){
        FirebaseFirestore.getInstance().collection("user").document(idPerfil).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()) {
                    classUser = new ClassUser(documentSnapshot.get("foto").toString(),
                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                    //perfil
                    FirebaseFirestore.getInstance().collection("perfil").document(idPerfil)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            perfil = documentSnapshot.toObject(ClassPerfilPerfil.class);
                            adapter.add( new Item_perfil_perfil( classUser , perfil));
                            getfPosts();
                        }
                    });
                }
                }
        });
    }

    private void getfPosts(){
        FirebaseFirestore.getInstance().collection("post").
                document(idPerfil).collection("post").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ClassPerfilPost post = new ClassPerfilPost(document.get("imagemPost").toString()
                                        , document.get("descricao").toString());

                                adapter.add(new Item_post(classUser.getName() , classUser.getImage(), post));
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void verifyButton(View v) {
        switch (buttonConnectstate) {
            //Caso for dono do perfil CriarPost
            case 0:
                startActivity(new Intent(this, PostCreate.class));
                break;
            //Caso não for amigo Enviar Friend Request
            case 1:
                buttonConnect.setVisibility(View.INVISIBLE);
                FriendRequestFirebase.sendFriendRequest(idPerfil);
                MakeToast.makeToast(this , "Teste");
                break;
            default:
                buttonConnect.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
