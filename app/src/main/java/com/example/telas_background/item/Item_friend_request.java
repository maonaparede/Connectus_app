package com.example.telas_background.item;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.Request;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_friend_request extends Item<ViewHolder> implements View.OnClickListener {

    public final ClassUserScreen user;
    private Integer positionRequest;
    private Item item;

    public Item_friend_request(ClassUserScreen user) {
        this.user = user;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView name = viewHolder.itemView.findViewById(R.id.item_friend_request_textview_title);
        name.setText(user.getName());

        ImageView image = viewHolder.itemView.findViewById(R.id.item_friend_request_imageview_image);
        Picasso.get().load(user.getImage()).into(image);

        positionRequest = position;
        item = viewHolder.getItem();

        viewHolder.itemView.findViewById(R.id.item_friend_request_button_connect).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.item_friend_request_button_reject).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.item_friend_request_textview_title).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.item_friend_request_imageview_image).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_friend_request_button_connect:
                Request.fromItemFriendRequest(item, 0 , positionRequest);
                Log.d("Request" , "Socializar");
                break;

            case R.id.item_friend_request_button_reject:
                Request.fromItemFriendRequest(item, 1, positionRequest);
                Log.d("Request" , "Rejeitar");
                break;
            default:
                Request.fromItemFriendRequest(item, 2, positionRequest);
                Log.d("Request" , "Default");
                break;
        }
    }

    @Override
    public int getLayout() {

        return R.layout.item_friend_request;
    }


}
