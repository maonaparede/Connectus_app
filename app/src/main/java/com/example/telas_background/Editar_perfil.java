package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Editar_perfil extends AppCompatActivity {

    private EditText hobbieTexto;
    private TextView hobbie1;
    private TextView hobbie2;
    private TextView hobbie3;
    private TextView hobbie4;
    private TextView hobbie5;
    private TextView hobbie6;
    private Integer contador = 1;

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
    }


    public void hobbieBotao(View view){
        if(contador == 1){hobbie1.setText(hobbieTexto.getText());}
        if(contador == 2){hobbie2.setText(hobbieTexto.getText());}
        if(contador == 3){hobbie3.setText(hobbieTexto.getText());}
        if(contador == 4){hobbie4.setText(hobbieTexto.getText());}
        if(contador == 5){hobbie5.setText(hobbieTexto.getText());}
        if(contador == 6){hobbie6.setText(hobbieTexto.getText());}
        if(contador > 6) {contador = 0;}
        contador++;
    }



}
