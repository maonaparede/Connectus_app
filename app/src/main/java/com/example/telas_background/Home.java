package com.example.telas_background;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Classes_instanciadas.Classe_user_tela;
import com.example.telas_background.item.Item_home_encontros;
import com.example.telas_background.item.Item_home_pessoas;
import com.example.telas_background.utils_helper.DialogFriendRemove;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class Home extends AppCompatActivity {

    private RecyclerView encontrosRecycler;
    private GroupAdapter encontrosAdapter;

    private RecyclerView pessoasRecycler;
    private GroupAdapter pessoasAdapter;
    Context context;
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

        pessoasAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(Home.this, Perfil.class);
                Bundle bundle = new Bundle();

                Item_home_pessoas pessoas = (Item_home_pessoas) item;
                bundle.putString("user", pessoas.user.getId().toString());
                bundle.putInt("estado", 1);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        encontrosAdapter.add(new Item_home_encontros("Teste normal" , "https://cdnb.artstation.com/p/assets/images/images/027/258/167/small/mushk-rizvi-terrasglow.jpg?1591056874"));
        encontrosAdapter.add(new Item_home_encontros("Teste hard" , "https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327"));

        pessoasAdapter.add(new Item_home_pessoas(
                new Classe_user_tela("https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327" , "30FT22MK5dVcJhEqYuBFUqNnPoo1" , "")));

        context = this;
    }

    public void criarEncontro(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("estado" , 0);
        Intent intent = new Intent(this, Editar_encontro.class);
        intent.putExtras(bundle);
        startActivity(intent);

        startActivity(intent);
    }



    public void perfil(View view){

        //PopUp.createDialogOkCancel(this, "Porque?", "Você vai cortar relações com esse usuário")


        startActivity(new Intent(this , Conversas.class));
    }


}
