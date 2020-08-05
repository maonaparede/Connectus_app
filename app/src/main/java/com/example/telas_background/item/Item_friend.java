package com.example.telas_background.item;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.Friends;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_friend extends Item<ViewHolder> implements View.OnClickListener {

    public final ClassUserScreen user;
    private String path;

    private Integer positionRequest;
    private Item item;

    public Item_friend(ClassUserScreen user , String path) {
        this.user = user;
        this.path = path;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView name = viewHolder.itemView.findViewById(R.id.item_friend_textview_name);
        name.setText(user.getName());


        ImageView image = viewHolder.itemView.findViewById(R.id.item_friend_imageview_image);
        Picasso.get().load(user.getImage()).into(image);

        positionRequest = viewHolder.getAdapterPosition();
        item = viewHolder.getItem();

        viewHolder.itemView.findViewById(R.id.item_friend_button_perfil).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.item_friend_button_disconect).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.item_friend_textview_name).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.item_friend_imageview_image).setOnClickListener(this);
    }

    @NonNull
    @Override
    public Item getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_friend_button_perfil:
                Friends.fromItemFriend(item, 2 , positionRequest);
                Log.d("amigo" , "Socializar");
                break;

            case R.id.item_friend_button_disconect:
                Friends.fromItemFriend(item, 0, positionRequest);
                Log.d("amigo" , "Rejeitar");
                break;
            default:
                Friends.fromItemFriend(item, 2, positionRequest);
                Log.d("amigo" , "Default");
                break;
        }
    }

    @Override
    public int getLayout() {

        return R.layout.item_friend;
    }


}
