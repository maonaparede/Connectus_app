package com.example.telas_background.item;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassChatMsg;
import com.example.telas_background.R;
import com.google.firebase.auth.FirebaseAuth;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;


public class Item_chat extends Item<ViewHolder> {

    private final ClassChatMsg chat_msg;

    public Item_chat(ClassChatMsg chat_msg) {
        this.chat_msg = chat_msg;
    }


    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView name = viewHolder.itemView.findViewById(R.id.item_chat_name);
        TextView msg = viewHolder.itemView.findViewById(R.id.item_chat_msg);

        name.setText(chat_msg.getName());
        msg.setText(chat_msg.getMsg());

    }

    @Override
    public int getLayout() {
         return chat_msg.getId().equals(FirebaseAuth.getInstance().getUid())
               ? R.layout.item_chat_to : R.layout.item_chat_from;
    }
}
