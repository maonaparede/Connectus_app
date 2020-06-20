package com.example.telas_background.item;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes.Classe_user;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class item_home_pessoas extends Item<ViewHolder> {

    public final Classe_user user;

    public item_home_pessoas(Classe_user user) {
        this.user = user;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        ImageView foto = viewHolder.itemView.findViewById(R.id.pessoahome);

        Picasso.get().load(user.getFoto()).into(foto);

    }

    @Override
    public int getLayout() {

        return R.layout.item_home_pessoas;
    }
}

