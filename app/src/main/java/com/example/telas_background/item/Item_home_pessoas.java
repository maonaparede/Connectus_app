package com.example.telas_background.item;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.Home;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_home_pessoas extends Item<ViewHolder> implements View.OnClickListener {

    private Integer position;

    public final Classe_user_tela user;

    public Item_home_pessoas(Classe_user_tela user) {
        this.user = user;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        ImageView foto = viewHolder.itemView.findViewById(R.id.pessoahome);

        this.position = position;

        foto.setOnClickListener(this);

        Picasso.get().load(user.getFoto()).into(foto);

    }

    @NonNull
    @Override
    public Item getItem(int position) {
        return super.getItem(position);
    }


    @Override
    public void onClick(View v) {
        Home.telaPerfil(getItem(position));
    }

    @Override
    public int getLayout() {

        return R.layout.item_home_pessoas;
    }
}

