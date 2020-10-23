package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassContact;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_contact extends Item<ViewHolder> {


  public ClassContact contact;

    public Item_contact(ClassContact contact) {
        this.contact = contact;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView name = viewHolder.itemView.findViewById(R.id.item_contact_textview_name);
        name.setText(contact.getName());

        TextView lastMsg = viewHolder.itemView.findViewById(R.id.item_contact_textview_last_msg);
        lastMsg.setText(contact.getLastMsg());

        ImageView image = viewHolder.itemView.findViewById(R.id.item_contact_imageview_image);
        Picasso.get().load(contact.getImage()).resize(512, 512)
                .centerCrop().into(image);


    }


    @Override
    public int getLayout() {

        return R.layout.item_contact;
    }

    public String getName() {
        return contact.getName();
    }
}

