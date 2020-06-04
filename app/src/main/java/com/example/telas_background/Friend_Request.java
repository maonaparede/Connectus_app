package com.example.telas_background;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.item.item_friend_request;
import com.xwray.groupie.GroupAdapter;

public class Friend_Request extends AppCompatActivity {

    private RecyclerView requestRecycler;
    private GroupAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend__request);

        requestRecycler = findViewById(R.id.recycler_request);

        requestAdapter = new GroupAdapter();

        requestRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL , false));

        requestRecycler.setAdapter(requestAdapter);

        requestAdapter.add(new item_friend_request("Meu pai",
                "https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318"
               ));
    }
}
