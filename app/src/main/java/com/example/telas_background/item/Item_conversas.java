package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_conversas extends Item<ViewHolder> {

    private final String imagem;
    private final String nome;
    private final String ultimaMsg;

    public Item_conversas(String nome , String imagem , String ultimaMsg) {
        this.nome = nome;
        this.imagem = imagem;
        this.ultimaMsg = ultimaMsg;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_conversa);
        nome1.setText(nome);

        TextView ultimaMsg1 = viewHolder.itemView.findViewById(R.id.ultima_conversa);
        ultimaMsg1.setText(ultimaMsg);

        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_conversa);
        Picasso.get().load(imagem).into(foto);

    }

    @Override
    public int getLayout() {

        return R.layout.item_conversas;
    }
}

