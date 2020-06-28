package com.example.telas_background.item;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_home_encontros extends Item<ViewHolder> {

    private final String nome;
    private final String imagem;

    public Item_home_encontros(String nome , String imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        ImageButton foto = viewHolder.itemView.findViewById(R.id.imageButtonEhome);
        TextView encontro = viewHolder.itemView.findViewById(R.id.tituloEhome);

        Picasso.get().load(imagem).into(foto);

        encontro.setText(nome);

    }

    @Override
    public int getLayout() {

        return R.layout.item_home_encontros;
    }
}
