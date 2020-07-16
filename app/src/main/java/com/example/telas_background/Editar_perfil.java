package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_background.firebase.Editar_perfil_firebase;
import com.example.telas_background.utils_helper.MakeToast;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

public class Editar_perfil extends AppCompatActivity {

    private EditText hobbieTexto;
    private TextView hobbie1;
    private TextView hobbie2;
    private TextView hobbie3;
    private TextView hobbie4;
    private TextView hobbie5;
    private TextView hobbie6;
    private Integer contador = 1;
    private EditText nomePerfil;
    private EditText descricaoPerfil;
    private ImageView fotoPerfil;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        hobbieTexto = findViewById(R.id.hobbieEP);
        hobbie1 = findViewById(R.id.hobbie1E);
        hobbie2 = findViewById(R.id.hobbie2E);
        hobbie3 = findViewById(R.id.hobbie3E);
        hobbie4 = findViewById(R.id.hobbie4E);
        hobbie5 = findViewById(R.id.hobbie5E);
        hobbie6 = findViewById(R.id.hobbie6E);
        nomePerfil = findViewById(R.id.nomeEP);
        descricaoPerfil = findViewById(R.id.descricaoEP);
        fotoPerfil = findViewById(R.id.fotoEP);
    }


    public void hobbieBotao(View view){
        if(contador == 1){hobbie1.setText(hobbieTexto.getText());}
        if(contador == 2){hobbie2.setText(hobbieTexto.getText());}
        if(contador == 3){hobbie3.setText(hobbieTexto.getText());}
        if(contador == 4){hobbie4.setText(hobbieTexto.getText());}
        if(contador == 5){hobbie5.setText(hobbieTexto.getText());}
        if(contador == 6){hobbie6.setText(hobbieTexto.getText());}
        if(contador > 6) {contador = 0;}
        hobbieTexto.setText(null);
        contador++;
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

            Picasso.get().load(uri).into(fotoPerfil);
        }
    }

    public void salvar(View view){
        String nome = nomePerfil.getText().toString();
        String descricao = descricaoPerfil.getText().toString();
        String Ehobbie1 = hobbie1.getText().toString();
        String Ehobbie2 = hobbie2.getText().toString();
        String Ehobbie3 = hobbie3.getText().toString();
        String Ehobbie4 = hobbie4.getText().toString();
        String Ehobbie5 = hobbie5.getText().toString();
        String Ehobbie6 = hobbie6.getText().toString();

        Editar_perfil_firebase updatePerfil = new Editar_perfil_firebase(uri, nome, descricao,
                Ehobbie1, Ehobbie2, Ehobbie3, Ehobbie4, Ehobbie5, Ehobbie6);

        updatePerfil.uparfotoperfil();

        MakeToast.makeToast(this , "Perfil Atualizado");
        startActivity(new Intent(this , Home.class));
    }

}
