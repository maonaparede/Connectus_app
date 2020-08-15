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
import com.example.telas_background.fragment.FragmentHome;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class MeetingEdit extends AppCompatActivity {

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

    private Integer estado = 0;

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
            estado = bundle.getInt("estado");

            if(!pathEncontro.isEmpty()){
                getfMeeting();
            }
        }
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

            Picasso.get().load(uri).into(imageView);
        }
    }


    //tem q criar uma operação de update no Meeting Create Firebase
    public void saveOrCreateMeeting(View view){
                MeetingCreateFirebase meeting =
                        new MeetingCreateFirebase(name.getText().toString() , description.getText().toString(),
                        day.getText().toString(), local.getText().toString(), hour.getText().toString(), uri);

                meeting.uploadImageMeeting();

                if(estado == 0) {
                    MakeToast.makeToast(this, "Criado!");
                }else {
                    MakeToast.makeToast(this, "Atualizado!");
                }

                startActivity(new Intent(this , FragmentHome.class));
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

    public void toDialogMeeting(View v){
        switch (v.getId()) {

             //add membro
            case R.id.meeting_edit_button_add_member:

                MakeToast.makeToast(this , "add");
                DialogMeeting.createDialogOkAddRemove(this,0);
            break;
            //rem membro
            case R.id.meeting_edit_button_remove_member:

                MakeToast.makeToast(this , "rem");
                DialogMeeting.createDialogOkAddRemove(this ,1);
                break;
            default:
                break;
        }
        }

    public void excludeEncontroButton(View v){
            DialogRemoveConfirmation.createDialogRemoveConfirmation(this ,
                    "Quer Excluir o Encontro?" , "Excluir o Encontro" , 1);
    }

    public static void FromDialogRemoveConfirmation(){
        MeetingFirebase.excludeMeeting();
    }
}
