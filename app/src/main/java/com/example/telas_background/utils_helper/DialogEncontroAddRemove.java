package com.example.telas_background.utils_helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes_estaticas.User_principal;
import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.Perfil;
import com.example.telas_background.R;
import com.example.telas_background.firebase.Encontro_firebase;
import com.example.telas_background.item.Item_add_user_encontro;
import com.example.telas_background.item.Item_rem_user_encontro;
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

import java.util.ArrayList;

public class DialogEncontroAddRemove {

    public static Button ok;
    private static Integer estado;
    public static Button cancel;
    public static String path;
    public static TextView title;
    private static Context context;
    public static RecyclerView pessoasRecycler;
    public static GroupAdapter pessoasAdapter;

    private static ArrayList<Item_add_user_encontro> listAdd;
    private static ArrayList<Item_rem_user_encontro> listRem;

    private static SearchView filter;

    public static void createDialogOkAddRemove(final Context context1, String path1 , final Integer estado1) {

        context = context1;
        listAdd = new ArrayList<Item_add_user_encontro>();
        listRem = new ArrayList<Item_rem_user_encontro>();
        path = path1;
        estado = estado1;


        LayoutInflater inflater = LayoutInflater.from(context1);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context1);
        View view1 = inflater.inflate(R.layout.dialog_recyclerview_encontro_add, null);

        filter = (SearchView) view1.findViewById(R.id.buscar_conversas_dialog_add);
        cancel = (Button) view1.findViewById(R.id.cancel_dialog_encontro_add);
        title = (TextView) view1.findViewById(R.id.titulo_dialog_recycler);
        pessoasRecycler = (RecyclerView) view1.findViewById(R.id.recyclerView_encontro_add);

        title.setText(R.string.enviar_request_membro);

        pessoasAdapter = new GroupAdapter();

        pessoasRecycler.setLayoutManager(new LinearLayoutManager(context1, LinearLayoutManager.VERTICAL, false));

        pessoasRecycler.setAdapter(pessoasAdapter);

        filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.trim().isEmpty()) {
                    switch (estado1) {
                        case 0:
                        filterDialogAdd(newText);
                        break;
                        case 1:
                        filterDialogRem(newText);
                            break;
                    }
                }else{
                    filterResetAdd();
                    filterResetRem();
                }
                return false;
            }
        });

        switch (estado1) {
            case 0:
            pegarTodosAmigosDialog();
            break;

            case 1:
             //paga membros do encontro
             pegarTodosMembrosDialog();
                break;

            default:
                break;
        }

        mBuilder.setView(view1);
        final AlertDialog dialog = mBuilder.create();


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    public static void botaoItemRecyclerEncontroAdd(Item item, Integer estado, final Integer positon) {




        switch (estado) {
            case 0:
                Item_add_user_encontro userAdd = (Item_add_user_encontro) item;
                int a = userAdd.getPosition(item);
                MakeToast.makeToast(context, "Request Enviada");
                Encontro_firebase.sendRequestEncontro(userAdd.user.getId());
                pessoasAdapter.removeGroup(positon);
                pessoasAdapter.notifyItemRemoved(positon);
                listAdd.remove(a);
                break;

            case 1:
                Item_rem_user_encontro userRem = (Item_rem_user_encontro) item;
                int b = userRem.getPosition(item);
                MakeToast.makeToast(context, "Membro Removido");
                Encontro_firebase.removeMemberEncontro(userRem.user.getId());
                pessoasAdapter.removeGroup(positon);
                pessoasAdapter.notifyItemRemoved(positon);
                listRem.remove(b);
                break;
            default:

                break;
        }
    }


    private static void pegarTodosAmigosDialog() {

        FirebaseFirestore.getInstance().collection("amigo").
                document(User_principal.getId()).collection("amigo").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                pegarUserAddDialog(document);

                                // Log.d("Perfil" ,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private static void pegarUserAddDialog(final QueryDocumentSnapshot docAmigo) {
        DocumentReference docRef1 = FirebaseFirestore.getInstance().collection("user")
                .document(docAmigo.get("id").toString());

        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    Classe_user_tela userTela = new Classe_user_tela(documentSnapshot.get("foto").toString(),
                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                        listAdd.add(new Item_add_user_encontro(userTela));
                        pessoasAdapter.add(new Item_add_user_encontro(userTela));
                }
            }
        });
    }

    private static void pegarTodosMembrosDialog() {

        FirebaseFirestore.getInstance().collection("encontro").
                document(User_principal.getId()).collection("membros").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                pegarUserRemDialog(document);

                                // Log.d("Perfil" ,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private static void pegarUserRemDialog(final QueryDocumentSnapshot docAmigo) {
        DocumentReference docRef1 = FirebaseFirestore.getInstance().collection("user")
                .document(docAmigo.get("id").toString());

        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    Classe_user_tela userTela = new Classe_user_tela(documentSnapshot.get("foto").toString(),
                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());


                            listRem.add(new Item_rem_user_encontro(userTela));
                            pessoasAdapter.add(new Item_rem_user_encontro(userTela));

                }
            }
        });
    }

    private static void filterDialogAdd(String text) {
        if (!text.isEmpty()){
            ArrayList<Item_add_user_encontro> filteredList = new ArrayList<>();
            for (Item_add_user_encontro item : listAdd) {
                if (item.getNome().toLowerCase().contains(text.toLowerCase())) {
                    Log.d("lista item", "top "+ item.getNome());
                    filteredList.add(item);
                }
            }
            pessoasAdapter.update(filteredList);
        }
    }

    private static void filterResetAdd() {
        pessoasAdapter.update(listAdd);
    }

    private static void filterDialogRem(String text) {
        if (!text.isEmpty()){
            ArrayList<Item_rem_user_encontro> filteredList = new ArrayList<>();
            for (Item_rem_user_encontro item : listRem) {
                if (item.getNome().toLowerCase().contains(text.toLowerCase())) {
                    Log.d("lista item", "top "+ item.getNome());
                    filteredList.add(item);
                }
            }
            pessoasAdapter.update(filteredList);
        }
    }

    private static void filterResetRem() {
        pessoasAdapter.update(listAdd);
    }


}