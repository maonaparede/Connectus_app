package com.example.telas_background;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes_estaticas.UserPrincipal;
import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.item.Item_home_meeting;
import com.example.telas_background.item.Item_home_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;

public class Home extends AppCompatActivity {

    private RecyclerView encontrosRecycler;
    private GroupAdapter encontrosAdapter;
    private RecyclerView pessoasRecycler;
    private GroupAdapter pessoasAdapter;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pessoasRecycler = findViewById(R.id.home_recyclerview_user);
        pessoasAdapter = new GroupAdapter();
        pessoasRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.HORIZONTAL , false));
        pessoasRecycler.setAdapter(pessoasAdapter);

        encontrosRecycler = findViewById(R.id.home_recyclerview_meeting);
        encontrosAdapter = new GroupAdapter();
        encontrosRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.HORIZONTAL , false));
        encontrosRecycler.setAdapter(encontrosAdapter);


        pessoasAdapter.add(new Item_home_user(
                new ClassUserScreen("https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327" , "sPDdPbX7juTtGUDTN95uMlTEjes1" , "")));

        context = this;
        getfMeetings();
    }

    public void toMeetingEdit(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("estado" , 0);
        Intent intent = new Intent(this, MeetingEdit.class);
        intent.putExtras(bundle);
        startActivity(intent);

        startActivity(intent);
    }



    public void quebraGalho(View view){


        startActivity(new Intent(this , Perfil.class));
    }

    private void getfMeetings(){
        FirebaseFirestore.getInstance().collection("caminho_encontro").
                document(UserPrincipal.getId()).collection("encontro").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                getMeeting(document);
                            }
                        } else {
                            Log.d("Home ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getMeeting(final QueryDocumentSnapshot docA) {
        FirebaseFirestore.getInstance().collection("encontro")
                .document(docA.get("dono").toString()).collection("atributos")
                .document("atributos").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                   Item_home_meeting item = new Item_home_meeting(documentSnapshot.get("nome").toString()
                                    , documentSnapshot.get("foto").toString(),
                           documentSnapshot.get("dono").toString());

                    encontrosAdapter.add(item);
                }
            }
        });
    }


    public static void toMeeting(Item item){
        Intent intent = new Intent(context, Meeting.class);
        Bundle bundle = new Bundle();

        Item_home_meeting meeting = (Item_home_meeting) item;
        bundle.putString("dono" , meeting.getOwnerId());

        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void telaPerfil(Item item){
        Intent intent = new Intent(context, Request.class);
        Bundle bundle = new Bundle();

        Item_home_user pessoas = (Item_home_user) item;
        bundle.putString("user", pessoas.user.getId().toString());
        bundle.putInt("estado", 1);

        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
