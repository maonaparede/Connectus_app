package com.example.telas_background.dialog_toast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.telas_background.R;
import com.example.telas_background.firebase.PostFirebase;
import com.example.telas_background.fragment.FragmentPerfil;
import com.example.telas_background.instanceClasses.ClassPerfilPost;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class DialogCreatePost extends Activity {

    private static Uri uri;
    private AlertDialog dialog;
    private EditText description;
    private static ImageView image;
    private ImageButton addImage;
    private Button post;

    public void createPostDialog(final Context context , final Activity activity){


        LayoutInflater inflater =  LayoutInflater.from(context);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view1 = inflater.inflate(R.layout.activity_post_create, null);

         image = view1.findViewById(R.id.post_create_imageview_image);
         description =  view1.findViewById(R.id.post_create_edittext_description);
         addImage = view1.findViewById(R.id.post_create_imagebutton_add_image);
         post = view1.findViewById(R.id.post_create_button_post);


        mBuilder.setView(view1);
        dialog = mBuilder.create();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost(context);
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagemClick(activity);
            }
        });


        dialog.show();
    }


    private void imagemClick(Activity activity){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
         activity.startActivityForResult(intent, 0);
    }

    public static void getURI(Uri uri1){
        uri = uri1;
        Log.d("image", uri + "");
        Picasso.get().load(uri).into(image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data!= null && data.getData() != null) {
            uri = data.getData();

        }
    }

    private void createPost(Context context){

        String description1 = description.getText().toString();

        if(description1.isEmpty() && uri == null){
            MakeToast.makeToast(context.getApplicationContext() , context.getString(R.string.campos_vazios));
            return;
        }else {
            ClassPerfilPost post;
            post = new ClassPerfilPost("", description1);
            PostFirebase postFirebase = new PostFirebase(post, uri);
            postFirebase.uploadImagePost();
            dialog.dismiss();
        }
    }
}
