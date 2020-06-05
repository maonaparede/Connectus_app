package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class item_post extends Item<ViewHolder> {

    private final String imagemPerfil;
    private final String imagemPost;
    private final String nome;
    private final String descricao;
    private final String numComentario;

    public item_post(String nome ,String descricao, String numComentario, String imagemPerfil, String imagemPost) {
        this.nome = nome;
        this.imagemPerfil = imagemPerfil;
        this.descricao = descricao;
        this.numComentario = numComentario;
        this.imagemPost = imagemPost;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_post);
        nome1.setText(nome);

        TextView descricao1 = viewHolder.itemView.findViewById(R.id.descricao_post);
        descricao1.setText(descricao);

        TextView numComentario1 = viewHolder.itemView.findViewById(R.id.comentarios_post);
        numComentario1.setText(numComentario);

        ImageView imagemPerfil1 = viewHolder.itemView.findViewById(R.id.foto_perfil_post);
        Picasso.get().load(imagemPerfil).into(imagemPerfil1);

        ImageView imagemPost1 = viewHolder.itemView.findViewById(R.id.img_post);
        Picasso.get().load(imagemPost).into(imagemPost1);



    }

    @Override
    public int getLayout() {

        return R.layout.item_post;
    }
}
