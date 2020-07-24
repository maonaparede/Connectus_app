package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.telas_background.Classes_estaticas.User_principal;
import com.example.telas_background.item.Item_home_encontros;
import com.example.telas_background.item.Item_home_pessoas;
import com.example.telas_background.utils_helper.MakeToast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Encontro extends AppCompatActivity {

    private Integer estado = 0;

    private TextView titulo1;
    private TextView descricao1;
    private TextView data1;
    private TextView horario1;
    private TextView local1;

    private Button sair;

    private String pathMsg;

    private String pathEncontro;
    private String donoEncontro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encontro);

        titulo1 = findViewById(R.id.tituloE);
        descricao1 = findViewById(R.id.descricaoE);
        data1 = findViewById(R.id.dataE);
        horario1 = findViewById(R.id.horarioE);
        local1 = findViewById(R.id.localE);
        sair = findViewById(R.id.Exit_ButtonE);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            pathEncontro = bundle.getString("encontro");
            donoEncontro = bundle.getString("dono");

            if(donoEncontro.equals(User_principal.getId())){
                sair.setText("Editar");
                estado = 1;
            }

            pegarEncontro();
        }
    }

    private void pegarEncontro() {

        DocumentReference docRef1 = FirebaseFirestore.getInstance().document(pathEncontro)
                .collection("atributos").document("atributos");

        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    titulo1.setText(documentSnapshot.get("nome").toString());
                    descricao1.setText(documentSnapshot.get("descricao").toString());
                    data1.setText(documentSnapshot.get("dia").toString());
                    horario1.setText(documentSnapshot.get("horario").toString());
                    local1.setText(documentSnapshot.get("local").toString());
                    pathMsg = documentSnapshot.get("path").toString();


                    // Log.d("foto" , documentSnapshot.get("foto").toString());
                    // pegarLastMsg( docAmigo.get("path").toString() , documentSnapshot);
                }
            }
        });
    }



    public void sairEditar(View v){
        switch (estado){
            case 0:
                //abre dialog sait encontro
                MakeToast.makeToast(this , "teste");
            case 1:
                Intent intent = new Intent(this, Editar_encontro.class);
                Bundle bundle = new Bundle();

                bundle.putInt("estado", 1);

                intent.putExtras(bundle);
                startActivity(intent);

        }
    }

}
