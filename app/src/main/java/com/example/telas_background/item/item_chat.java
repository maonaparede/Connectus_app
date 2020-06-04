package com.example.telas_background.item;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;


public class item_chat extends Item<ViewHolder> {

    private final String nome;
    private final String mensagem;
    private final Boolean to;

    public item_chat(String nome , String mensagem , Boolean to) {
        this.nome = nome;
        this.mensagem = mensagem;
        this.to = to;
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_chat);
        TextView msg = viewHolder.itemView.findViewById(R.id.msg_chat);

        nome1.setText(nome);
        msg.setText(mensagem);

    }

    @Override
    public int getLayout() {

        //return message.getFromId().equals(FirebaseAuth.getInstance().getUid())
        // ? R.layout.item_from_chat
        //: R.layout.item_to_chat;
        if(to){
            return R.layout.item_to_chat;
        }else{
            return R.layout.item_from_chat;
        }
    }
}
