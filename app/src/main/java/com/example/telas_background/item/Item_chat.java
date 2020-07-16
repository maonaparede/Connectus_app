package com.example.telas_background.item;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.Classes_instanciadas.Classe_chat_msg;
import com.example.telas_background.R;
import com.google.firebase.auth.FirebaseAuth;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;


public class Item_chat extends Item<ViewHolder> {

    private final Classe_chat_msg chat_msg;
    private Boolean to;

    public Item_chat(Classe_chat_msg chat_msg) {
        this.chat_msg = chat_msg;
        to = false;
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView nome1 = viewHolder.itemView.findViewById(R.id.nome_chat);
        TextView msg = viewHolder.itemView.findViewById(R.id.msg_chat);

        nome1.setText(chat_msg.getNome());
        msg.setText(chat_msg.getMensagem());

    }

    @Override
    public int getLayout() {

       /* if (chat_msg.getNome() == String.valueOf(R.string.voce)) {
            return R.layout.item_to_chat;
        } else {
            return R.layout.item_from_chat;
        }


        */


         return chat_msg.getId().equals(FirebaseAuth.getInstance().getUid().toString())
               ? R.layout.item_from_chat : R.layout.item_to_chat;
    }
}
