package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.telas_background.Classes_instanciadas.Classe_perfil_post;
import com.example.telas_background.firebase.Post_firebase;
import com.example.telas_background.notificaHelper.NotificaHelper;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

public class CriarPost extends AppCompatActivity {

    private Uri uri;

    private ImageView imageView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_post);

        imageView = findViewById(R.id.dialog_imageView);
        editText = findViewById(R.id.dialog_descricao);
    }

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

            Picasso.get().load(uri).into(imageView);
        }
    }

    public void postar(View view){

        String descricao = editText.getText().toString();

        if(descricao.isEmpty() && uri == null){

            NotificaHelper.mostrarToast(this , "O post não pode estar em branco!!");

            return;
        }else {

            Classe_perfil_post post;
            post = new Classe_perfil_post("", descricao, "Breve");

            Post_firebase postar = new Post_firebase(post, uri);
            postar.uparFotoCriarPost();

            startActivity(new Intent( this , Perfil.class));
        }
    }
}