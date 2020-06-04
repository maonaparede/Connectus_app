package com.example.telas_background;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.item.item_comentarios_posts;
import com.xwray.groupie.GroupAdapter;

public class Comentarios_Posts extends AppCompatActivity {

    private RecyclerView comentariosRecycler;
    private GroupAdapter comentariosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios__posts);

        comentariosRecycler = findViewById(R.id.recyclerP);

        comentariosAdapter = new GroupAdapter();

        comentariosRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL , false));

        comentariosRecycler.setAdapter(comentariosAdapter);

        comentariosAdapter.add(new item_comentarios_posts("Meu pai",
                "https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318"
                , "Boa"));


    }
}
