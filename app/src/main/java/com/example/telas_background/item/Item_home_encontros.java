package com.example.telas_background.item;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Home;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_home_encontros extends Item<ViewHolder> implements View.OnClickListener {

    private Integer position;
    private Item item;

    private final String nome;
    private final String imagem;
    private String dono;

    public Item_home_encontros(String nome , String imagem, String path, String dono) {
        this.nome = nome;
        this.imagem = imagem;
        this.dono = dono;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        ImageButton foto = viewHolder.itemView.findViewById(R.id.imageButtonEhome);
        TextView encontro = viewHolder.itemView.findViewById(R.id.tituloEhome);

        if(imagem.isEmpty()){
            Picasso.get().load(R.drawable.linear_background).into(foto);
        }else{
        Picasso.get().load(imagem).into(foto);
        }

        this.position = viewHolder.getAdapterPosition();
        item = viewHolder.getItem();

        foto.setOnClickListener(this);
        encontro.setOnClickListener(this);

        encontro.setText(nome);

    }


    public String getDono() {return dono; }

    @Override
    public void onClick(View v) {
        Home.telaEncontro(item);
    }

    @Override
    public int getLayout() {

        return R.layout.item_home_encontros;
    }
}
