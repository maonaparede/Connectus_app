package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class item_perfil_perfil extends Item<ViewHolder> {

    private final String imagem;
    private final String nome;
    private final String descricao;
    private final String hobbie1;

    public item_perfil_perfil(String nome ,String imagem, String descricao, String hobbie1) {
        this.nome = nome;
        this.imagem = imagem;
        this.descricao = descricao;
        this.hobbie1 = hobbie1;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

       TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_perfil);
        nome1.setText(nome);

        TextView descricao1 = viewHolder.itemView.findViewById(R.id.descricao_perfil);
       descricao1.setText(descricao);

        TextView hobbie = viewHolder.itemView.findViewById(R.id.hobbieP);
        hobbie.setText(hobbie1);

        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_perfil);
        Picasso.get().load(imagem).into(foto);



    }

    @Override
    public int getLayout() {

        return R.layout.item_perfil_perfil;
    }
}