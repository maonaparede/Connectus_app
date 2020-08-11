package com.example.telas_background.item;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.fragment.Home;
import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_home_meeting extends Item<ViewHolder> implements View.OnClickListener {

    private Integer position;
    private Item item;

    private final String name;
    private final String image;
    private String ownerId;

    public Item_home_meeting(String name, String image, String ownerId) {
        this.name = name;
        this.image = image;
        this.ownerId = ownerId;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        ImageButton imageView = viewHolder.itemView.findViewById(R.id.item_home_meeting_imagebutton_image);
        TextView meetingName = viewHolder.itemView.findViewById(R.id.item_home_meeting_textview_title);

        if(image.isEmpty()){
            Picasso.get().load(R.drawable.linear_background).into(imageView);
        }else{
        Picasso.get().load(image).into(imageView);
        }

        this.position = viewHolder.getAdapterPosition();
        item = viewHolder.getItem();

        imageView.setOnClickListener(this);
        meetingName.setOnClickListener(this);

        meetingName.setText(name);

    }


    public String getOwnerId() {return ownerId; }

    @Override
    public void onClick(View v) {
        Home.toMeeting(item);
    }

    @Override
    public int getLayout() {

        return R.layout.item_home_meeting;
    }
}
