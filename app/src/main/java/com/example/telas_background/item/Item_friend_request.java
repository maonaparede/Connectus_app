package com.example.telas_background.item;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.Request;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_friend_request extends Item<ViewHolder> implements View.OnClickListener {

    public final Classe_user_tela user;
    private Integer positionRequest;
    private Item item;

    public Item_friend_request(Classe_user_tela user) {
        this.user = user;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_friend_request);
        nome1.setText(user.getNome());


        ImageView foto = viewHolder.itemView.findViewById(R.id.foto_friend_request);
        Picasso.get().load(user.getFoto()).into(foto);

        positionRequest = position;
        item = viewHolder.getItem();

        viewHolder.itemView.findViewById(R.id.socializar_friend_request).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.rejeitar_friend_request).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.nome_friend_request).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.foto_friend_request).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.socializar_friend_request:
                Request.botaoItemUserRecycler(item, 0 , positionRequest);
                Log.d("Request" , "Socializar");
                break;

            case R.id.rejeitar_friend_request:
                Request.botaoItemUserRecycler(item, 1, positionRequest);
                Log.d("Request" , "Rejeitar");
                break;
            default:
                Request.botaoItemUserRecycler(item, 2, positionRequest);
                Log.d("Request" , "Default");
                break;
        }
    }

    @Override
    public int getLayout() {

        return R.layout.item_friend_request;
    }


}
