package com.example.telas_background.item;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.R;
import com.example.telas_background.dialog_toast.DialogMeeting;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_meeting_remove_user extends Item<ViewHolder> implements View.OnClickListener {

    public final ClassUserScreen user;
    private Integer positionRequest;
    private Item item;

    public Item_meeting_remove_user(ClassUserScreen user) {
        this.user = user;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        TextView name = viewHolder.itemView.findViewById(R.id.item_meeting_user_textview_name);
        name.setText(user.getName());


        ImageView imageView = viewHolder.itemView.findViewById(R.id.item_meeting_user_imageview_image);
        Picasso.get().load(user.getImage()).into(imageView);

        positionRequest = viewHolder.getAdapterPosition();
        item = viewHolder.getItem();

        viewHolder.itemView.findViewById(R.id.item_meeting_user_button_send_request).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.item_meeting_user_textview_name).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.item_meeting_user_imageview_image).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_meeting_user_button_send_request:
                DialogMeeting.botaoItemRecyclerMeeting(item, 1 , positionRequest);
                Log.d("Rem encontro" , "Remover");
                break;

            default:
                DialogMeeting.botaoItemRecyclerMeeting(item, 3, positionRequest);
                Log.d("Rem encontro" , "Default");
                break;
        }
    }

    @Override
    public int getLayout() {

        return R.layout.item_meeting_remove_user;
    }

    public String getName(){
        return user.getName();
    }

}