package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_background.firebase.PerfilCreateFirebase;
import com.example.telas_background.dialog_toast.MakeToast;
import com.example.telas_background.firebase.PerfilUpdateFirebase;
import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.instanceClasses.ClassPerfilPerfil;
import com.example.telas_background.instanceClasses.ClassUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class PerfilEdit extends AppCompatActivity {

    private EditText hobbieEditText;
    private TextView hobbie1;
    private TextView hobbie2;
    private TextView hobbie3;
    private TextView hobbie4;
    private TextView hobbie5;
    private TextView hobbie6;
    private Integer counter = 1;
    private EditText name;
    private EditText description;
    private ImageView imageView;
    private Uri uri;

    private Integer state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_edit);

        hobbieEditText = findViewById(R.id.perfil_edit_edittext_hobbie);
        hobbie1 = findViewById(R.id.perfil_edit_textview_hobbie1E);
        hobbie2 = findViewById(R.id.perfil_edit_textview_hobbie2E);
        hobbie3 = findViewById(R.id.perfil_edit_textview_hobbie3E);
        hobbie4 = findViewById(R.id.perfil_edit_textview_hobbie4E);
        hobbie5 = findViewById(R.id.perfil_edit_textview_hobbie5E);
        hobbie6 = findViewById(R.id.perfil_edit_textview_hobbie6E);
        name = findViewById(R.id.perfil_edit_edittext_name);
        description = findViewById(R.id.perfil_edit_edittext_description);
        imageView = findViewById(R.id.perfil_edit_imageview_image);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            state = bundle.getInt("estado");
            if(state == 1){
                getfPerfil();
            }
        }else{
            state = 3;
        }
    }


    public void hobbieAdd(View view){
        if(counter == 1){hobbie1.setText(hobbieEditText.getText());}
        if(counter == 2){hobbie2.setText(hobbieEditText.getText());}
        if(counter == 3){hobbie3.setText(hobbieEditText.getText());}
        if(counter == 4){hobbie4.setText(hobbieEditText.getText());}
        if(counter == 5){hobbie5.setText(hobbieEditText.getText());}
        if(counter == 6){hobbie6.setText(hobbieEditText.getText());}
        if(counter > 6) {
            counter = 0;}
        hobbieEditText.setText(null);
        counter++;
    }


    //abre a tela para escolher as imgs
    public void imagemClick(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(PerfilEdit.this); //Use CROP_IMAGE_ACTIVITY_REQUEST_CODE
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

    public void saveUser(View view){
        String name1 = name.getText().toString();
        String description1 = description.getText().toString();
        String Ehobbie1 = hobbie1.getText().toString();
        String Ehobbie2 = hobbie2.getText().toString();
        String Ehobbie3 = hobbie3.getText().toString();
        String Ehobbie4 = hobbie4.getText().toString();
        String Ehobbie5 = hobbie5.getText().toString();
        String Ehobbie6 = hobbie6.getText().toString();

        if(name1 == null || name1.trim().isEmpty() || description1 == null || description1.trim().isEmpty() ||
                Ehobbie1 == null || Ehobbie1.trim().isEmpty() || Ehobbie2 == null || Ehobbie2.trim().isEmpty() ||
                Ehobbie3 == null || Ehobbie3.trim().isEmpty() || Ehobbie4 == null || Ehobbie4.trim().isEmpty() ||
                Ehobbie5 == null || Ehobbie5.trim().isEmpty() || Ehobbie6 == null || Ehobbie6.trim().isEmpty()
        ){
            MakeToast.makeToast(this , getString(R.string.campos_vazios));
        }else {

            if(state == 1){
                PerfilUpdateFirebase updateFirebase = new PerfilUpdateFirebase(uri, name1, description1,
                        Ehobbie1, Ehobbie2, Ehobbie3, Ehobbie4, Ehobbie5, Ehobbie6);
                updateFirebase.uploadPerfilImage();
                MakeToast.makeToast(this, "Perfil Atualizado");
            }else {
                PerfilCreateFirebase perfilCreateFirebase = new PerfilCreateFirebase(uri, name1, description1,
                        Ehobbie1, Ehobbie2, Ehobbie3, Ehobbie4, Ehobbie5, Ehobbie6);
                perfilCreateFirebase.uploadPerfilImage();
                MakeToast.makeToast(this, "Perfil Criado");
            }
            startActivity(new Intent(this, FragmentHandler.class));
        }
    }

    private void getfPerfil(){
        FirebaseFirestore.getInstance().collection("user").document(UserPrincipal.getId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //User, id, foto, nome
                        if(documentSnapshot.exists()) {

                            ClassUser user = new ClassUser(CleanString.clean(documentSnapshot.get("foto")),
                                        documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                                if(user.getImage() != null) {
                                    Picasso.get().load(user.getImage()).into(imageView);
                                }
                                name.setText(user.getName());





                            //perfil - descrição - Hobbie
                            FirebaseFirestore.getInstance().collection("perfil").document(UserPrincipal.getId())
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    ClassPerfilPerfil perfil = documentSnapshot.toObject(ClassPerfilPerfil.class);
                                    description.setText(perfil.getDescricao());
                                    hobbie1.setText(perfil.getH1());
                                    hobbie2.setText(perfil.getH2());
                                    hobbie3.setText(perfil.getH3());
                                    hobbie4.setText(perfil.getH4());
                                    hobbie5.setText(perfil.getH5());
                                    hobbie6.setText(perfil.getH6());
                                }
                            });
                        }
                    }
                });
    }

}
