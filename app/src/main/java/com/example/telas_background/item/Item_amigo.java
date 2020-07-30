package com.example.telas_background.item;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.Friends;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_amigo extends Item<ViewHolder> implements View.OnClickListener {

    public final Classe_user_tela user;
    private String path;

    private Integer positionRequest;
    private Item item;

    public Item_amigo(Classe_user_tela user , String path) {
        this.user = user;
        this.path = path;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_amigo);
        nome1.setText(user.getNome());


        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_amigo);
        Picasso.get().load(user.getFoto()).into(foto);

        positionRequest = viewHolder.getAdapterPosition();
        item = viewHolder.getItem();

        viewHolder.itemView.findViewById(R.id.socializar_amigo).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.rejeitar_amigo).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.nome_amigo).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.foto_amigo).setOnClickListener(this);
    }

    @NonNull
    @Override
    public Item getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.socializar_amigo:
                Friends.botaoItemRecycler(item, 0 , positionRequest);
                Log.d("amigo" , "Socializar");
                break;

            case R.id.rejeitar_amigo:
                Friends.botaoItemRecycler(item, 1, positionRequest);
                Log.d("amigo" , "Rejeitar");
                break;
            default:
                Friends.botaoItemRecycler(item, 2, positionRequest);
                Log.d("amigo" , "Default");
                break;
        }
    }

    @Override
    public int getLayout() {

        return R.layout.item_amigo;
    }


}
