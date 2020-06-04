package com.example.telas_background;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.item.item_chat;
import com.xwray.groupie.GroupAdapter;

public class Chat extends AppCompatActivity {

    private RecyclerView chatRecycler;
    private GroupAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

       // ImageView foto = findViewById(R.id.imageView2);
       // Picasso.get().load("https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318").into(foto);

        chatRecycler = findViewById(R.id.recycler_chat);

        chatAdapter = new GroupAdapter();

        chatRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL , false));

        chatRecycler.setAdapter(chatAdapter);

        chatAdapter.add( new item_chat("Jefff","bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb " , true));

        chatAdapter.add( new item_chat("manoBeow"," aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" , false));
    }
}
