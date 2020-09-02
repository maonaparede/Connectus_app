package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.telas_background.firebase.MeetingCreateFirebase;
import com.example.telas_background.firebase.MeetingFirebase;
import com.example.telas_background.dialog_toast.DialogMeeting;
import com.example.telas_background.dialog_toast.DialogRemoveConfirmation;
import com.example.telas_background.dialog_toast.MakeToast;
import com.example.telas_background.dialog_toast.ConfirmationDialog;
import com.example.telas_background.firebase.MeetingUpdateFirebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class MeetingEdit extends AppCompatActivity implements ConfirmationDialog {

    private Button addButton;
    private Button remButton;
    private EditText name;
    private EditText description;
    private EditText day;
    private EditText local;
    private EditText hour;
    private ImageView imageView;
    private Uri uri;
    private String pathEncontro;
    private String imagem;

    private Integer state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_edit);

        addButton = findViewById(R.id.meeting_edit_button_add_member);
        remButton = findViewById(R.id.meeting_edit_button_remove_member);
        name = findViewById(R.id.meeting_edit_edittext_name);
        description = findViewById(R.id.meeting_edit_textview_description);
        day = findViewById(R.id.meeting_edit_textview_day);
        local = findViewById(R.id.meeting_edit_textview_local);
        hour = findViewById(R.id.meeting_edit_textview_hour);
        imageView = findViewById(R.id.meeting_edit_imageview_image);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            pathEncontro = bundle.getString("encontro");
            state = bundle.getInt("estado");

            if(!pathEncontro.isEmpty()){
                getfMeeting();
            }
        }else {
            state = 5;
        }
    }

    @Override
    protected void onStart() {
        if(state == 0){
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

            Picasso.get().load(uri).into(imageView);
        }
    }


    //tem q criar uma operação de update no Meeting Create Firebase
    public void saveOrCreateMeeting(View view){

       String name1 = name.getText().toString().trim();
       String description1 = description.getText().toString().trim();
       String day1 = day.getText().toString().trim();
       String local1 = local.getText().toString().trim();
       String hour1 = hour.getText().toString().trim();


       //Verifica se os campos não estão vazios
        if(name1 == null || name1.isEmpty() || description1 == null || description1.isEmpty()
                || day1 == null || day1.isEmpty() || local1 == null || local1.isEmpty() || hour1 == null || hour1.isEmpty() ){


            MakeToast.makeToast(this , getString(R.string.campos_vazios));

        }else {


            if (state == 1) {
                MeetingUpdateFirebase updateFirebase = new MeetingUpdateFirebase(name1, description1,day1, local1, hour1, uri);
                updateFirebase.uploadImageMeeting();
                MakeToast.makeToast(this, "Atualizado!");
            } else {
                MeetingCreateFirebase meeting = new MeetingCreateFirebase(name1, description1,day1, local1, hour1, uri);
                meeting.uploadImageMeeting();
                MakeToast.makeToast(this, "Criado!");
            }

            startActivity(new Intent(this, FragmentHandler.class));
        }
    }


    private void getfMeeting() {
        FirebaseFirestore.getInstance().document(pathEncontro)
                .collection("atributos").document("atributos").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    name.setText(documentSnapshot.get("nome").toString());
                    description.setText(documentSnapshot.get("descricao").toString());
                    day.setText(documentSnapshot.get("dia").toString());
                    hour.setText(documentSnapshot.get("horario").toString());
                    local.setText(documentSnapshot.get("local").toString());
                    imagem = documentSnapshot.get("foto").toString();
                    if(!imagem.isEmpty()) {
                        Picasso.get().load(imagem).into(imageView);
                    }
                }
            }
        });
    }

    public void cancelEditMeeting(View view){
        Intent intent = new Intent( this , FragmentHandler.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void toDialogMeeting(View v){
        switch (v.getId()) {

             //add membro
            case R.id.meeting_edit_button_add_member:

                MakeToast.makeToast(this , "add");
                new DialogMeeting().createDialogOkAddRemove(this,0);
            break;
            //rem membro
            case R.id.meeting_edit_button_remove_member:

                MakeToast.makeToast(this , "rem");
                new DialogMeeting().createDialogOkAddRemove(this ,1);
                break;
            default:
                break;
        }
        }

    public void excludeEncontroButton(View v){
            new DialogRemoveConfirmation().createDialogRemoveConfirmation(this ,
                    "Quer Excluir o Encontro?" , "Excluir o Encontro" , this);
    }

    @Override
    public void DialogConfirmation() {
        MeetingFirebase.excludeMeeting();
    }
}
