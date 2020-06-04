package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class item_comentarios_posts extends Item<ViewHolder> {

    private final String imagem;
    private final String nome;
    private final String comentario;

    public item_comentarios_posts(String nome ,String imagem , String comentario) {
        this.nome = nome;
        this.imagem = imagem;
        this.comentario = comentario;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_post);
        nome1.setText(nome);

        TextView comentario1 = viewHolder.itemView.findViewById(R.id.comentario_post);
        comentario1.setText(comentario);

        ImageView foto = viewHolder.itemView.findViewById(R.id.fotoP);
        Picasso.get().load(imagem).into(foto);

    }

    @Override
    public int getLayout() {

        return R.layout.item_comentarios_post;
    }
}
