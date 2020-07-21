package com.example.telas_background.utils_helper;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.R;
import com.example.telas_background.firebase.Friend_request_firebase;
import com.example.telas_background.item.Item_home_pessoas;
import com.xwray.groupie.GroupAdapter;

public class DialogEncontroAddRemove {

    public static Button ok;
    public static Button cancel;
    public static TextView title;
    public static RecyclerView pessoasRecycler;
    public static GroupAdapter pessoasAdapter;

    public static void createDialogOkAddRemove(final Context context, Integer estado){


        LayoutInflater inflater =  LayoutInflater.from(context);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view1 = inflater.inflate(R.layout.dialog_recyclerview_encontro_add, null);

        cancel = (Button) view1.findViewById(R.id.cancel_dialog_encontro_add);
        title = (TextView) view1.findViewById(R.id.titulo_dialog_recycler);
        pessoasRecycler = (RecyclerView) view1.findViewById(R.id.recyclerView_encontro_add);

        title.setText(R.string.enviar_request_membro);

        pessoasAdapter = new GroupAdapter();

        pessoasRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        pessoasRecycler.setAdapter(pessoasAdapter);


        pessoasAdapter.add(new Item_home_pessoas(
                new Classe_user_tela("https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327" , "07NBvaK87iO20JvVnWEqTE0AOSB2" , "")));


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

}
