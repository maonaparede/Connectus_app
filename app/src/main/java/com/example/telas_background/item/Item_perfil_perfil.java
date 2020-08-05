package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassPerfilPerfil;
import com.example.telas_background.instanceClasses.ClassUser;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_perfil_perfil extends Item<ViewHolder> {

    private final ClassPerfilPerfil perfil;
    private final ClassUser user;

    public Item_perfil_perfil(ClassUser user, ClassPerfilPerfil perfil) {
        this.perfil = perfil;
        this.user = user;
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView description = viewHolder.itemView.findViewById(R.id.item_perfil_perfil_textview_description);
       description.setText(perfil.getDescription());

        TextView name = viewHolder.itemView.findViewById(R.id.item_perfil_perfil_textview_name);
        name.setText(user.getName());

        ImageView imageView = viewHolder.itemView.findViewById(R.id.item_perfil_perfil_imageview_image);
        Picasso.get().load(user.getImage()).into(imageView);

        //hobbie

        TextView hobbie = viewHolder.itemView.findViewById(R.id.item_perfil_perfil_textview_hobbieP);
        hobbie.setText(perfil.getH1());

        TextView Hhobbie2 = viewHolder.itemView.findViewById(R.id.item_perfil_perfil_textview_hobbieP2);
        Hhobbie2.setText(perfil.getH2());

        TextView Hhobbie3 = viewHolder.itemView.findViewById(R.id.item_perfil_perfil_textview_hobbieP3);
        Hhobbie3.setText(perfil.getH3());

        TextView Hhobbie4 = viewHolder.itemView.findViewById(R.id.item_perfil_perfil_textview_hobbieP4);
        Hhobbie4.setText(perfil.getH4());

        TextView Hhobbie5 = viewHolder.itemView.findViewById(R.id.item_perfil_perfil_textview_hobbieP5);
        Hhobbie5.setText(perfil.getH5());

        TextView Hhobbie6 = viewHolder.itemView.findViewById(R.id.item_perfil_perfil_textview_hobbieP6);
        Hhobbie6.setText(perfil.getH6());

    }

    @Override
    public int getLayout() {

        return R.layout.item_perfil_perfil;
    }
}