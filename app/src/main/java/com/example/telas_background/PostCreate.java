package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.telas_background.instanceClasses.ClassPerfilPost;
import com.example.telas_background.firebase.PostFirebase;
import com.example.telas_background.dialog_toast.MakeToast;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class PostCreate extends AppCompatActivity {

    private Uri uri;

    private ImageView imageView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post_create);

        imageView = findViewById(R.id.post_create_imageview_image);
        editText = findViewById(R.id.post_create_edittext_description);
    }

    public void imagemClick(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(PostCreate.this); //Use CROP_IMAGE_ACTIVITY_REQUEST_CODE
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                Picasso.get().load(uri).into(imageView);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void createPost(View view){

        String description = editText.getText().toString();

        if(description == null || description.trim().isEmpty() || uri == null){
            MakeToast.makeToast(this , "O post não pode estar em branco!!");
            return;
        }else {
            ClassPerfilPost post;
            post = new ClassPerfilPost("", description);
            PostFirebase postFirebase = new PostFirebase(post, uri);
            postFirebase.uploadImagePost();

            finish();
        }
    }
}