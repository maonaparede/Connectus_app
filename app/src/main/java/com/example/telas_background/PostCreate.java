package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.telas_background.fragment.Perfil;
import com.example.telas_background.instanceClasses.ClassPerfilPost;
import com.example.telas_background.firebase.PostFirebase;
import com.example.telas_background.dialog_toast.MakeToast;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

public class PostCreate extends AppCompatActivity {

    private Uri uri;

    private ImageView imageView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        imageView = findViewById(R.id.post_create_imageview_image);
        editText = findViewById(R.id.post_create_edittext_description);
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

    public void createPost(View view){

        String description = editText.getText().toString();

        if(description.isEmpty() && uri == null){
            MakeToast.makeToast(this , "O post não pode estar em branco!!");
            return;
        }else {
            ClassPerfilPost post;
            post = new ClassPerfilPost("", description);
            PostFirebase postFirebase = new PostFirebase(post, uri);
            postFirebase.uploadImagePost();

            startActivity(new Intent( this , Perfil.class));
        }
    }
}