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
import com.example.telas_background.item.Item_friend_request;
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
    public static Button cancel;
    public static TextView title;
    private static Context context;
    public static RecyclerView pessoasRecycler;
    public static GroupAdapter pessoasAdapter;

    private static ArrayList<Item_add_user_encontro> mExampleList;
    private static SearchView filter;

    public static void createDialogOkAddRemove(final Context context1, Integer estado) {

        context = context1;
        mExampleList = new ArrayList<Item_add_user_encontro>();


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
                    filterDialog(newText);
                }else{
                    filterReset();
                }
                return false;
            }
        });

        pegarTodosAmigosDialog();


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

        Item_add_user_encontro pessoa = (Item_add_user_encontro) item;
        switch (estado) {
            case 0:
                MakeToast.makeToast(context, "Enviar Request");
                Encontro_firebase.sendRequestEncontro(pessoa.user.getId());
                pessoasAdapter.removeGroup(positon);
                break;

            default:
                MakeToast.makeToast(context, "Perfil");
                Intent intent = new Intent(context, Perfil.class);
                Bundle bundle = new Bundle();

                bundle.putString("user", pessoa.user.getId());
                bundle.putInt("estado", 3);

                intent.putExtras(bundle);
                context.startActivity(intent);
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

                                pegarAmigoDialog(document);

                                // Log.d("Perfil" ,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private static void pegarAmigoDialog(final QueryDocumentSnapshot docAmigo) {
        DocumentReference docRef1 = FirebaseFirestore.getInstance().collection("user")
                .document(docAmigo.get("id").toString());

        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    Classe_user_tela userTela = new Classe_user_tela(documentSnapshot.get("foto").toString(),
                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                    Log.d("foto", documentSnapshot.get("foto").toString());

                    mExampleList.add(new Item_add_user_encontro(userTela));
                    pessoasAdapter.add(new Item_add_user_encontro(userTela));

                }
            }
        });
    }


    private static void filterDialog(String text) {

        if (!text.isEmpty()){

            ArrayList<Item_add_user_encontro> filteredList = new ArrayList<>();
            for (Item_add_user_encontro item : mExampleList) {
                if (item.getNome().toLowerCase().contains(text.toLowerCase())) {
                    Log.d("lista item", "top "+ item.getNome());
                    filteredList.add(item);
                }
            }
            pessoasAdapter.update(filteredList);

        }
    }

    private static void filterReset() {
        pessoasAdapter.update(mExampleList);
    }


}