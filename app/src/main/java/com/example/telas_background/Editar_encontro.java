package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Editar_encontro extends AppCompatActivity {

    private Button button;
    private EditText nomeE;
    private EditText descricaoE;
    private EditText dataE;
    private EditText localE;
    private EditText horarioE;
    private ImageView imagem;

    private Integer estado = 1;

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

    public void verificarBotao(View view){



        //estado da Tela criação(0) edição(1)
        if(estado == 0){
            // criarEncontro();
        }
        if(estado == 1){
           // EditarEncontro();
        }
    }
}
