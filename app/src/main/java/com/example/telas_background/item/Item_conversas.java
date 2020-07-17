package com.example.telas_background.item;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.Classes_instanciadas.Classe_conversa;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;

public class Item_conversas extends Item<ViewHolder> {


  public Classe_conversa conversa;

    public Item_conversas(Classe_conversa conversa) {
        this.conversa = conversa;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_conversa);
        nome1.setText(conversa.getNome());

        TextView ultimaMsg1 = viewHolder.itemView.findViewById(R.id.ultima_conversa);
        ultimaMsg1.setText(conversa.getUltimaMsg());

        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_conversa);
        Picasso.get().load(conversa.getFoto()).into(foto);


    }


    @Override
    public int getLayout() {

        return R.layout.item_conversas;
    }

    public String getNome() {
        return conversa.getNome();
    }
}

