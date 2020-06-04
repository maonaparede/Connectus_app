package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class item_friend_request extends Item<ViewHolder> {

    private final String imagem;
    private final String nome;

    public item_friend_request(String nome ,String imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_friend_request);
        nome1.setText(nome);


        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_friend_request);
        Picasso.get().load(imagem).into(foto);

    }

    @Override
    public int getLayout() {

        return R.layout.item_friend_request;
    }
}
