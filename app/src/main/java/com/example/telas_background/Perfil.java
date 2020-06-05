package com.example.telas_background;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.item.item_perfil_perfil;
import com.example.telas_background.item.item_post;
import com.xwray.groupie.GroupAdapter;

public class Perfil extends AppCompatActivity {

    private RecyclerView perfilRecycler;
    private GroupAdapter perfilAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfilRecycler = findViewById(R.id.recyclerP);

        perfilAdapter = new GroupAdapter();

        perfilRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL , false));

        perfilRecycler.setAdapter(perfilAdapter);

        perfilAdapter.add(new item_perfil_perfil("Meu pai",
                "https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318"
                , "Boa" , "Comer"));


        perfilAdapter.add(new item_post("Meu pai",
                "ft de Perfil ae"
                , "5 Comentários"
                , "https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318"
                , "https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318"
        ));

        perfilAdapter.add(new item_post("Meu pai",
                "ft de Balada 44"
                , "5 Comentários"
                , "https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318"
                , "https://cdna.artstation.com/p/assets/images/images/025/734/778/large/z-w-gu-thronef3handfixweb.jpg?1586776245"
        ));
    }
}
