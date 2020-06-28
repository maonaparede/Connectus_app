package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_perfil_perfil;
import com.example.telas_background.Classes_instanciadas.Classe_user;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_perfil_perfil extends Item<ViewHolder> {

    private final Classe_perfil_perfil perfil;
    private final Classe_user user;

    public Item_perfil_perfil(Classe_user user, Classe_perfil_perfil perfil) {
        this.perfil = perfil;
        this.user = user;
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView descricao1 = viewHolder.itemView.findViewById(R.id.descricao_perfil);
       descricao1.setText(perfil.getDescricao());

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_perfil);
        nome1.setText(user.getNome());

        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_perfil);
        Picasso.get().load(user.getFoto()).into(foto);

        //hobbie

        TextView hobbie = viewHolder.itemView.findViewById(R.id.hobbieP);
        hobbie.setText(perfil.getH1());

        TextView Hhobbie2 = viewHolder.itemView.findViewById(R.id.hobbieP2);
        Hhobbie2.setText(perfil.getH2());

        TextView Hhobbie3 = viewHolder.itemView.findViewById(R.id.hobbieP3);
        Hhobbie3.setText(perfil.getH3());

        TextView Hhobbie4 = viewHolder.itemView.findViewById(R.id.hobbieP4);
        Hhobbie4.setText(perfil.getH4());

        TextView Hhobbie5 = viewHolder.itemView.findViewById(R.id.hobbieP5);
        Hhobbie5.setText(perfil.getH5());

        TextView Hhobbie6 = viewHolder.itemView.findViewById(R.id.hobbieP6);
        Hhobbie6.setText(perfil.getH6());

    }

    @Override
    public int getLayout() {

        return R.layout.item_perfil_perfil;
    }
}