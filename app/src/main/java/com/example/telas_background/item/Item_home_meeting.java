package com.example.telas_background.item;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.R;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_home_meeting extends Item<ViewHolder> {

    private Integer position;
    private Item item;

    private final String name;
    private final String image;
    private String ownerId;
    private Integer day;

    public Item_home_meeting(String name, String image, String ownerId, Integer day) {
        this.name = name;
        this.image = image;
        this.ownerId = ownerId;
        this.day = day;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

        ImageView imageView = viewHolder.itemView.findViewById(R.id.item_home_meeting_imageview_image);
        TextView meetingName = viewHolder.itemView.findViewById(R.id.item_home_meeting_textview_title);

        if(!image.isEmpty()) {
            Picasso.get().load(image).into(imageView);
        }

        this.position = viewHolder.getAdapterPosition();
        item = viewHolder.getItem();

        meetingName.setText(name);

    }

    public Item getItem() {
        return item;
    }

    public String getOwnerId() {return ownerId; }

    public Integer getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int getLayout() {

        return R.layout.item_home_meeting;
    }
}
