package com.example.telas_background.item;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.example.telas_background.instanceClasses.ClassPerfilPost;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_post extends Item<ViewHolder> {


    private final ClassPerfilPost post;
    private String idPost;


    public Item_post(ClassPerfilPost post, String idPost) {

        this.post = post;
        this.idPost = idPost;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        ImageView imagePost = viewHolder.itemView.findViewById(R.id.item_post_image_imageview);
        Picasso.get().load(post.getImage()).resize(512, 512)
                .centerCrop().into(imagePost);

    }

    public ClassPerfilPost getPost() {
        return post;
    }

    public String getIdPost() {
        return idPost;
    }

    @Override
    public int getLayout() {
        return R.layout.item_post_image;
    }
}
