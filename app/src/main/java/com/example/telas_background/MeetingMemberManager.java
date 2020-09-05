package com.example.telas_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;

import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.item.Item_contact;
import com.example.telas_background.item.Item_meeting_add_user;
import com.example.telas_background.item.Item_meeting_remove_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;

public class MeetingMemberManager extends AppCompatActivity {

    private RecyclerView recycler;
    private GroupAdapter adapter;
    private Button cancel;
    private SearchView filter;

    private Integer state;
    private ArrayList<Item_meeting_add_user> listAdd;
    private ArrayList<Item_meeting_remove_user> listRem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_member_manager);

        recycler = findViewById(R.id.meember_manager_recyclerview);
        filter = findViewById(R.id.meember_manager_searchview);
        cancel = findViewById(R.id.meember_manager_button_cancel);

        listAdd = new ArrayList<Item_meeting_add_user>();
        listRem = new ArrayList<Item_meeting_remove_user>();

        adapter = new GroupAdapter();
        recycler.setLayoutManager(new LinearLayoutManager( this, LinearLayoutManager.VERTICAL , false));
        recycler.setAdapter(adapter);


        //Procurar usuarios por nome
        filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.trim().isEmpty()) {
                    switch (state) {
                        case 0:
                            filterDialogAdd(newText);
                            break;
                        case 1:
                            filterDialogRemove(newText);
                            break;
                    }
                }else{
                    filterResetAdd();
                    filterResetRemove();
                }
                return false;
            }
        });

        switch (state) {
            case 0:
                //pega amigos
                getfFriends();
                break;

            case 1:
                //pega membros do encontro
                getfMembersMeeting();
                break;
            default:
                break;
        }

    }


    private void filterDialogAdd(String text) {
        if (!text.isEmpty()){
            ArrayList<Item_meeting_add_user> filteredList = new ArrayList<>();
            for (Item_meeting_add_user item : listAdd) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {

                    filteredList.add(item);
                }
            }
            adapter.update(filteredList);
        }
    }

    private void filterDialogRemove(String text) {
        if (!text.isEmpty()){
            ArrayList<Item_meeting_remove_user> filteredList = new ArrayList<>();
            for (Item_meeting_remove_user item : listRem) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {

                    filteredList.add(item);
                }
            }
            adapter.update(filteredList);
        }
    }

    private void filterResetAdd() {
        adapter.update(listAdd);
    }

    private void filterResetRemove(){
        adapter.update(listRem);
    }


    //Read BD

    private void getfFriends() {
        FirebaseFirestore.getInstance().collection("amigo").
                document(UserPrincipal.getId()).collection("amigo").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                getfUser(document);
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getfMembersMeeting() {
        FirebaseFirestore.getInstance().collection("encontro").
                document(UserPrincipal.getId()).collection("membros").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                getfUser(document);
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getfUser(final QueryDocumentSnapshot docAmigo) {
        FirebaseFirestore.getInstance().collection("user").document(docAmigo.get("id").toString())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    ClassUserScreen userScreen = new ClassUserScreen(documentSnapshot.get("foto").toString(),
                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                    if(state == 0) {
                        listAdd.add(new Item_meeting_add_user(userScreen));
                        adapter.add(new Item_meeting_add_user(userScreen));
                    }else {
                        listRem.add(new Item_meeting_remove_user(userScreen));
                        adapter.add(new Item_meeting_remove_user(userScreen));
                    }
                }
            }
        });
    }
}