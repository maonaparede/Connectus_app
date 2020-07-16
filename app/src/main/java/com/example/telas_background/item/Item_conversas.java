package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_estaticas.User_principal;
import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_conversas extends Item<ViewHolder> {

    public Classe_user_tela user;
  private String ultimaMsg;
  public String path;

    public Item_conversas(Classe_user_tela user, String ultimaMsg, String path) {
        this.user = user;
        this.ultimaMsg = ultimaMsg;
        this.path = path;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_conversa);
        nome1.setText(user.getNome());

        TextView ultimaMsg1 = viewHolder.itemView.findViewById(R.id.ultima_conversa);
        ultimaMsg1.setText(ultimaMsg);

        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_conversa);
        Picasso.get().load(user.getFoto()).into(foto);


    }

    @Override
    public int getLayout() {

        return R.layout.item_conversas;
    }
}

