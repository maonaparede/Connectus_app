package com.example.telas_background.item;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.fragment.FragmentFriends;
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


        Button deny = viewHolder.itemView.findViewById(R.id.item_friend_button_disconect);
        deny.setOnClickListener(this);

        positionRequest = viewHolder.getAdapterPosition();
        item = viewHolder.getItem();

    }

    @NonNull
    @Override
    public Item getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getId() {
        return super.getId();
    }

    public String getName() {
        return user.getName();
    }

    @Override
        public void onClick(View v) {
                    FragmentFriends.fromItemFriend(item, positionRequest);
                    Log.d("amigo" , "Rejeitar");
        }



    @Override
    public int getLayout() {

        return R.layout.item_friend;
    }


}
