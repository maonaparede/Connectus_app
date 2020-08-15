package com.example.telas_background.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.telas_background.instanceClasses.ClassMeetingRequest;
import com.example.telas_background.R;
import com.example.telas_background.fragment.FragmentRequest;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class Item_meeting_request extends Item<ViewHolder> implements View.OnClickListener {

    private Integer positionRequest;
    private Item item;

    public ClassMeetingRequest request;

    public Item_meeting_request(ClassMeetingRequest request) {
        this.request = request;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        TextView title = viewHolder.itemView.findViewById(R.id.item_meeting_request_textview_title);
         TextView name = viewHolder.itemView.findViewById(R.id.item_meeting_request_textview_owner);
         TextView localDay = viewHolder.itemView.findViewById(R.id.item_meeting_request_textview_local);
         TextView hour = viewHolder.itemView.findViewById(R.id.item_meeting_request_textview_hour);
         ImageView imageView = viewHolder.itemView.findViewById(R.id.item_meeting_request_imageview_image);

        positionRequest = position;
        item = viewHolder.getItem();

        title.setText(request.getTitle());
        name.setText(request.getName());
        Picasso.get().load(request.getImage()).into(imageView);
        localDay.setText(request.getLocal() + " | " + request.getDay());
        hour.setText(request.getHour());

        viewHolder.itemView.findViewById(R.id.item_meeting_request_button_accept).setOnClickListener(this);
        viewHolder.itemView.findViewById(R.id.item_meeting_request_button_reject).setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_meeting_request_button_accept:
                FragmentRequest.fromItemMeetingRequest(item, 0 ,positionRequest);
                break;
            case R.id.item_meeting_request_button_reject:
                FragmentRequest.fromItemMeetingRequest(item, 1 ,positionRequest);
                break;

            default:
                break;
        }
    }

    @Override
    public int getLayout() {
        return R.layout.item_meeting_request;
    }

}
