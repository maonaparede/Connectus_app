package com.example.telas_background.item;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.R;
import com.example.telas_background.utils_helper.DialogEncontroAddRemove;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_add_user_encontro extends Item<ViewHolder> implements View.OnClickListener {

    public final Classe_user_tela user;
    private Integer positionRequest;
    private Item item;

    public Item_add_user_encontro(Classe_user_tela user) {
        this.user = user;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_add_user_encontro);
        nome1.setText(user.getNome());


        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_add_user_encontro);
        Picasso.get().load(user.getFoto()).into(foto);

        positionRequest = viewHolder.getAdapterPosition();
        item = viewHolder.getItem();

        viewHolder.itemView.findViewById(R.id.socializar_add_user_encontro).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.nome_add_user_encontro).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.foto_add_user_encontro).setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.socializar_add_user_encontro:
               DialogEncontroAddRemove.botaoItemRecyclerEncontroAdd(item, 0 , positionRequest);
                Log.d("Add encontro" , "Enviar " + item);
                break;

            default:
                DialogEncontroAddRemove.botaoItemRecyclerEncontroAdd(item, 2, positionRequest);
                Log.d("Add encontro" , "Default");
                break;
        }
    }

    @Override
    public int getLayout() {

        return R.layout.item_add_user_encontro;
    }

    public String getNome(){
        return user.getNome();
    }

}
