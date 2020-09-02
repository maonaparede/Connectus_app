package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_background.dialog_toast.ConfirmationDialog;
import com.example.telas_background.dialog_toast.DialogRemoveConfirmation;
import com.example.telas_background.dialog_toast.MakeToast;
import com.example.telas_background.firebase.PostExcludeFirebase;
import com.example.telas_background.firebase.PostFirebase;
import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.item.Item_post_text;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;

public class Post extends AppCompatActivity implements  ConfirmationDialog{

    private String imageUser1;
    private String idUser;
    private String nameUser;
    private String descriptionUser;
    private String image;
    private String description;
    private String idPost;

    private ImageView imagePost;
    private ImageButton exclude;
    private RecyclerView recycler;
    private GroupAdapter adapter;
    private TextView name;
    private ImageView imgUser;
    private TextView descUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post);

        exclude = findViewById(R.id.post_imagebutton_exclude);
        exclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excludePost();
            }
        });


        imagePost = findViewById(R.id.post_imageview_image_post);
        name = findViewById(R.id.post_textview_name_user);
        imgUser = findViewById(R.id.post_imageview_image_user);
        descUser = findViewById(R.id.post_textview_desc_user);

        recycler = findViewById(R.id.post_recyclerView);
        adapter = new GroupAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            try {
                idUser = bundle.getString("id");
                imageUser1 = bundle.getString("image");
                nameUser = bundle.getString("name");
                descriptionUser = bundle.getString("description");
                idPost = bundle.getString("id_post");
                image = bundle.getString("post_image");
                description = bundle.getString("post_description");
            }finally {
                setAtributes();
            }
        }else{
            MakeToast.makeToast(this , "Error");
        }
    }

    public void excludePost(){
        exclude.setVisibility(View.INVISIBLE);
        new DialogRemoveConfirmation().createDialogRemoveConfirmation(this ,
                "Quer mesmo apagar esse Post?" , "Apagar Post" , this);
    }

    private void setAtributes(){

        if(idUser.equals(UserPrincipal.getId())){
            exclude.setVisibility(View.VISIBLE);
        }

        if(image != null){
            Picasso.get().load(image).into(imagePost);
        }
        if(imageUser1 != null){
            Picasso.get().load(imageUser1).into(imgUser);
        }
        name.setText(nameUser);
        descUser.setText(descriptionUser);

        adapter.add(new Item_post_text(description));
    }

    @Override
    public void DialogConfirmation() {
        new PostExcludeFirebase().excludePost(idPost);
        finish();
    }
}