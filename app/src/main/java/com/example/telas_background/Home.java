package com.example.telas_background;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.item.item_home_encontros;
import com.example.telas_background.item.item_home_pessoas;
import com.xwray.groupie.GroupAdapter;

public class Home extends AppCompatActivity {

    private RecyclerView encontrosRecycler;
    private GroupAdapter encontrosAdapter;

    private RecyclerView pessoasRecycler;
    private GroupAdapter pessoasAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        encontrosRecycler = findViewById(R.id.recyclerViewEncontro);
        pessoasRecycler = findViewById(R.id.recyclerViewPessoas);

        encontrosAdapter = new GroupAdapter();
        pessoasAdapter = new GroupAdapter();

        encontrosRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.HORIZONTAL , false));
        pessoasRecycler.setLayoutManager(new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.HORIZONTAL , false));

        encontrosRecycler.setAdapter(encontrosAdapter);
        pessoasRecycler.setAdapter(pessoasAdapter);


        encontrosAdapter.add(new item_home_encontros("Teste normal" , "https://cdnb.artstation.com/p/assets/images/images/027/258/167/small/mushk-rizvi-terrasglow.jpg?1591056874"));
        encontrosAdapter.add(new item_home_encontros("Teste hard" , "https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327"));

        pessoasAdapter.add(new item_home_pessoas("https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327"));
        pessoasAdapter.add(new item_home_pessoas("https://cdnb.artstation.com/p/assets/images/images/027/070/401/large/reza-abedi-chunli.jpg?1590510318"));
    }
}
