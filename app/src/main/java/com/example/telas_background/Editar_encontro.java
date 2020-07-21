package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.telas_background.firebase.Criar_encontro_firebase;
import com.example.telas_background.utils_helper.MakeToast;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

public class Editar_encontro extends AppCompatActivity {

    private Button button;
    private EditText nomeE;
    private EditText descricaoE;
    private EditText dataE;
    private EditText localE;
    private EditText horarioE;
    private ImageView imagem;
    private Uri uri;

    private Integer estado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_encontro);

        button = findViewById(R.id.addRemMembro);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            estado = bundle.getInt("estado");
        }

        nomeE = findViewById(R.id.nomeE);
        descricaoE = findViewById(R.id.descricaoE);
        dataE = findViewById(R.id.dataE);
        localE = findViewById(R.id.localE);
        horarioE = findViewById(R.id.horarioE);
        imagem = findViewById(R.id.imagemEE);

    }

    public void imagemClick1(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data!= null && data.getData() != null) {
            uri = data.getData();

            Picasso.get().load(uri).into(imagem);
        }
    }


    public void verificarBotao1(View view){

        switch(estado){
            case 0:
                Criar_encontro_firebase encontro = new Criar_encontro_firebase(nomeE.getText().toString() , descricaoE.getText().toString(),
                        dataE.getText().toString(), localE.getText().toString(), horarioE.getText().toString(), uri);

                encontro.uparfotoEncontro();

                MakeToast.makeToast(this , "test");
                break;
            case 1:
                //editar Encontro
                break;
            default:
                break;
        }
    }
}
