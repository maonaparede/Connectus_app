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
import com.example.telas_background.firebase.Encontro_firebase;
import com.example.telas_background.utils_helper.DialogEncontroAddRemove;
import com.example.telas_background.utils_helper.DialogRemoveConfirmation;
import com.example.telas_background.utils_helper.MakeToast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Editar_encontro extends AppCompatActivity {

    private Button addButton;
    private Button remButton;
    private EditText nomeE;
    private EditText descricaoE;
    private EditText dataE;
    private EditText localE;
    private EditText horarioE;
    private ImageView imagem1;
    private Uri uri;
    private String pathEncontro;
    private String imagem;

    private Integer estado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_encontro);

        addButton = findViewById(R.id.add_editar_encontro);
        remButton = findViewById(R.id.remove_editar_encontro);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            pathEncontro = bundle.getString("encontro");
            estado = bundle.getInt("estado");

            if(!pathEncontro.isEmpty()){
                pegarEncontro();
            }
        }

        nomeE = findViewById(R.id.nomeE);
        descricaoE = findViewById(R.id.descricaoE);
        dataE = findViewById(R.id.dataE);
        localE = findViewById(R.id.localE);
        horarioE = findViewById(R.id.horarioE);
        imagem1 = findViewById(R.id.imagemEE);


    }

    @Override
    protected void onStart() {
        if(estado == 0){
            addButton.setVisibility(View.INVISIBLE);
            remButton.setVisibility(View.INVISIBLE);
        }
        super.onStart();
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

            Picasso.get().load(uri).into(imagem1);
        }
    }


    public void verificarBotaoSalvar(View view){

                Criar_encontro_firebase encontro = new Criar_encontro_firebase(nomeE.getText().toString() , descricaoE.getText().toString(),
                        dataE.getText().toString(), localE.getText().toString(), horarioE.getText().toString(), uri);

                encontro.uparfotoEncontro();

                if(estado == 0) {
                    MakeToast.makeToast(this, "Criado!");
                }else {
                    MakeToast.makeToast(this, "Atualizado!");
                }

                startActivity(new Intent(this , Home.class));


    }


    private void pegarEncontro() {

        DocumentReference docRef1 = FirebaseFirestore.getInstance().document(pathEncontro)
                .collection("atributos").document("atributos");

        docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    nomeE.setText(documentSnapshot.get("nome").toString());
                    descricaoE.setText(documentSnapshot.get("descricao").toString());
                    dataE.setText(documentSnapshot.get("dia").toString());
                    horarioE.setText(documentSnapshot.get("horario").toString());
                    localE.setText(documentSnapshot.get("local").toString());
                    imagem = documentSnapshot.get("foto").toString();
                    Picasso.get().load(imagem).into(imagem1);
                }
            }
        });
    }

    public void addRemMembro(View v){
        switch (v.getId()) {

            case R.id.add_editar_encontro:
                //add membro
                MakeToast.makeToast(this , "add");
                DialogEncontroAddRemove.createDialogOkAddRemove(this, pathEncontro,0);
            break;
            case R.id.remove_editar_encontro:
                //rem membro
                MakeToast.makeToast(this , "rem");
                DialogEncontroAddRemove.createDialogOkAddRemove(this, pathEncontro ,1);
                break;
            default:
                break;
        }
        }

        public void excludeEncontroButton(View v){
            DialogRemoveConfirmation.createDialogRemoveConfirmation(this ,
                    "Quer Excluir o Encontro?" , "Excluir o Encontro" , 1);
        }

    public static void excludeEncontro(){
        Encontro_firebase.excludeEncontro();
    }
}
