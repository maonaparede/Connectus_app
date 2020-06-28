package com.example.telas_background.item;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_friend_request extends Item<ViewHolder> {

    public final Classe_user_tela user;

    public Item_friend_request(Classe_user_tela user) {
        this.user = user;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_friend_request);
        nome1.setText(user.getNome());


        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_friend_request);
        Picasso.get().load(user.getFoto()).into(foto);

    }

    @Override
    public int getLayout() {

        return R.layout.item_friend_request;
    }
}
