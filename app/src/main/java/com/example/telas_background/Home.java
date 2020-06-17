package com.example.telas_background;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes.Classe_perfil_post;
import com.example.telas_background.firebase.Perfil_firebase;
import com.example.telas_background.item.item_home_encontros;
import com.example.telas_background.item.item_home_pessoas;
import com.google.firebase.database.annotations.Nullable;
import com.xwray.groupie.GroupAdapter;

public class Home extends AppCompatActivity {

    private RecyclerView encontrosRecycler;
    private GroupAdapter encontrosAdapter;

    private RecyclerView pessoasRecycler;
    private GroupAdapter pessoasAdapter;
    Uri uri;
    String urlFoto;


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

    public void criarEncontro(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("estado" , 0);
        Intent intent = new Intent(this, Editar_encontro.class);
        intent.putExtras(bundle);
        startActivity(intent);

        startActivity(new Intent(this , Editar_encontro.class));
    }



    //abre a tela para escolher as imgs
    public void imagemClick(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data!= null && data.getData() != null) {
            uri = data.getData();


            Classe_perfil_post b = new Classe_perfil_post("ll", "Salve", "10");
            Perfil_firebase a = new Perfil_firebase(b , uri);

            a.uparFotoCriarPost();


        }
    }
}
