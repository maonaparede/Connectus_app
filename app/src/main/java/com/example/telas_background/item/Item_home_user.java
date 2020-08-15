package com.example.telas_background.item;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_home_user extends Item<ViewHolder> {

    private Integer position;
    private Item item;
    public final ClassUserScreen user;

    public Item_home_user(ClassUserScreen user) {
        this.user = user;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        ImageView imageView = viewHolder.itemView.findViewById(R.id.item_home_imageview_user);

        this.position = viewHolder.getAdapterPosition();
        item = viewHolder.getItem();


        Picasso.get().load(user.getImage()).into(imageView);

    }

    @NonNull
    @Override
    public Item getItem(int position) {
        return super.getItem(position);
    }


    @Override
    public int getLayout() {

        return R.layout.item_home_user;
    }
}

