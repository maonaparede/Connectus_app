package com.example.telas_background.dialog_toast;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.R;
import com.example.telas_background.firebase.MeetingFirebase;
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
import com.xwray.groupie.Item;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DialogMeeting {

    private static Integer state;
    private static Context context;
    private static GroupAdapter adapter;
    private static String name;
    private static ArrayList<Item_meeting_add_user> listAdd;
    private static ArrayList<Item_meeting_remove_user> listRem;

    public void createDialogOkAddRemove(final Context context , final Integer state, String name) {

        DialogMeeting.name = name;
        DialogMeeting.context = context;
        listAdd = new ArrayList<Item_meeting_add_user>();
        listRem = new ArrayList<Item_meeting_remove_user>();
        DialogMeeting.state = state;

        LayoutInflater inflater = LayoutInflater.from(context);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view1 = inflater.inflate(R.layout.dialog_meeting, null);

        SearchView filter = view1.findViewById(R.id.dialog_meeting_searchview);
        Button button = view1.findViewById(R.id.dialog_meeting_button_cancel);
        TextView title = view1.findViewById(R.id.dialog_meeting_title);
        RecyclerView pessoasRecycler = view1.findViewById(R.id.dialog_meeting_recyclerview);
        title.setText(R.string.enviar_request_membro);

        adapter = new GroupAdapter();
        pessoasRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        pessoasRecycler.setAdapter(adapter);

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

        mBuilder.setView(view1);
        final AlertDialog dialog = mBuilder.create();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //tratamento de clicks dentro dos Itens

    public void botaoItemRecyclerMeeting(Item item, Integer estado, final Integer positon) {
        switch (estado) {
            case 0:

                Item_meeting_add_user userAdd = (Item_meeting_add_user) item;
                int a = userAdd.getPosition(item);
                MakeToast.makeToast(context, "Request Enviada");
                MeetingFirebase.sendRequestMeeting(userAdd.user.getId() , name);
                adapter.removeGroup(positon);
                adapter.notifyItemRemoved(positon);
                listAdd.remove(a);
                break;

            case 1:
                Item_meeting_remove_user userRem = (Item_meeting_remove_user) item;
                int b = userRem.getPosition(item);
                MakeToast.makeToast(context, "Membro Removido");
                MeetingFirebase.removeMemberMeeting(userRem.user.getId());
                adapter.removeGroup(positon);
                adapter.notifyItemRemoved(positon);
                listRem.remove(b);
                break;
            default:
                break;
        }
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



    //filtros para Buscar usuário por nome

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


}