package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassPerfilPost;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_post extends Item<ViewHolder> {

    private final String image;
    private final String name;
    private final ClassPerfilPost post;


    public Item_post(String name, String image, ClassPerfilPost post) {
        this.name = name;
        this.image = image;
        this.post = post;

    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView name1 = viewHolder.itemView.findViewById(R.id.item_post_textview_name);
        name1.setText(name);

        TextView description = viewHolder.itemView.findViewById(R.id.item_post_textview_post_description);
        description.setText(post.getDescription());

        ImageView imagePerfil = viewHolder.itemView.findViewById(R.id.item_post_imageview_image);
        Picasso.get().load(image).into(imagePerfil);

        ImageView imagePost = viewHolder.itemView.findViewById(R.id.item_post_imageview_post_image);
        Picasso.get().load(post.getImage()).into(imagePost);

    }

    @Override
    public int getLayout() {
        return R.layout.item_post;
    }
}
