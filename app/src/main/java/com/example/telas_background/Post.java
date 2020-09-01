package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_background.dialog_toast.MakeToast;
import com.squareup.picasso.Picasso;

public class Post extends AppCompatActivity {

    private String imageUser1;
    private String nameUser;
    private String image;
    private String description;

    private ImageView imagePost;
    private TextView descriptionPost;
    private TextView name;
    private TextView imageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post);



        imagePost = findViewById(R.id.post_imageview_image_post);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            try {
                imageUser1 = bundle.getString("name");
                nameUser = bundle.getString("image");
                image = bundle.getString("post_image");
                description = bundle.getString("post_description");
            }finally {
                if(image != null){
                    Picasso.get().load(image).into(imagePost);
                }
            }

        }else{
            MakeToast.makeToast(this , "Error");
        }
    }
}