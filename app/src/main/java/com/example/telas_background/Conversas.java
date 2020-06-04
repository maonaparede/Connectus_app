package com.example.telas_background;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.item.item_conversas;
import com.xwray.groupie.GroupAdapter;

public class Conversas extends AppCompatActivity {

    private RecyclerView conversasRecycler;
    private GroupAdapter conversasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        conversasRecycler = findViewById(R.id.recycler_conversas);

        conversasAdapter = new GroupAdapter();

        conversasRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL , false));

        conversasRecycler.setAdapter(conversasAdapter);

        conversasAdapter.add(new item_conversas("Meu pai",
                "https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318"
                , "Boa"));
    }
}
