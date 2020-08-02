package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_background.Classes_estaticas.User_principal;
import com.example.telas_background.firebase.Encontro_firebase;
import com.example.telas_background.item.Item_home_encontros;
import com.example.telas_background.item.Item_home_pessoas;
import com.example.telas_background.utils_helper.DialogRemoveConfirmation;
import com.example.telas_background.utils_helper.MakeToast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

public class Encontro extends AppCompatActivity {

    private Integer estado = 0;

    private TextView titulo1;
    private TextView descricao1;
    private TextView data1;
    private TextView horario1;
    private TextView local1;
    private ImageView imagem1;
    private String imagem;

    private Button sair;

    private String pathMsg;

    private static Context context;
    private String pathEncontro;
    private static String donoEncontro;


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
        imagem1 = findViewById(R.id.imageView3_fotoE);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            donoEncontro = bundle.getString("dono");
            pathEncontro = "encontro/"+donoEncontro;

            if(donoEncontro.equals(User_principal.getId())){
                sair.setText("Editar");
                estado = 1;
            }

            pegarEncontro();
        }

        context = this;
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
                    imagem = documentSnapshot.get("foto").toString();
                    Picasso.get().load(imagem).into(imagem1);
                     Log.d("foto" , documentSnapshot.get("foto").toString() + " " + imagem);
                    // pegarLastMsg( docAmigo.get("path").toString() , documentSnapshot);
                }
            }
        });


    }

    public void toChat(View v){
        Intent intent = new Intent(this, Chat.class);
        Bundle bundle = new Bundle();

        bundle.putString("nome", titulo1.getText().toString());
        bundle.putString("foto", imagem);
        bundle.putString("path", pathEncontro);

        intent.putExtras(bundle);
        startActivity(intent);

    }


    public void sairEditar(View v){
        switch (estado){
            case 0:
                //abre dialog sai do encontro
                DialogRemoveConfirmation.createDialogRemoveConfirmation(this ,
                        "Quer Sair do Encontro?" , "Sair do Encontro" , 0);
                break;
            case 1:
                Intent intent = new Intent(this, Editar_encontro.class);
                Bundle bundle = new Bundle();

                bundle.putString("encontro", pathEncontro);
                bundle.putInt("estado", 1);

                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case 2:
                break;
        }
    }

    public static void exitEncontro(){
            Encontro_firebase.exitEncontro(donoEncontro);
            //context.startActivity(new Intent(context, Home.class));

    }

}
