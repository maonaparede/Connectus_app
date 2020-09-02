package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_post_text extends Item<ViewHolder> {


    private String text;

    public Item_post_text(String text) {
        this.text = text;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView textView = viewHolder.itemView.findViewById(R.id.item_post_text_textview);
        textView.setText("\"" +text +"\"");

    }

    @Override
    public int getLayout() {
        return R.layout.item_post_text;
    }
}
