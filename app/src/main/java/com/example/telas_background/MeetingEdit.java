package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.telas_background.dialog_toast.DateListener;
import com.example.telas_background.dialog_toast.DialogDate;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MeetingEdit extends AppCompatActivity implements ConfirmationDialog , DateListener {

    private Button addButton;
    private Button remButton;
    private EditText name;
    private EditText description;
    private Button day;
    private EditText local;
    private EditText hour;
    private ImageView imageView;
    private Uri uri;
    private String pathMeeting;
    private String image;
    private Integer dateNumber;

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
            pathMeeting = bundle.getString("encontro");
            state = bundle.getInt("estado");

            if(!pathMeeting.isEmpty()){
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

    public void createDialogDate(View v) {
        new DialogDate().createDialogOk(this,this);
    }

    public void imagemClick1(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(4,3)
                .start(MeetingEdit.this); //Use CROP_IMAGE_ACTIVITY_REQUEST_CODE
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


    //tem q criar uma operação de update no Meeting Create Firebase
    public void saveOrCreateMeeting(View view){

       String name1 = name.getText().toString().trim();
       String description1 = description.getText().toString().trim();
       String day1 = day.getText().toString().trim();
       String local1 = local.getText().toString().trim();
       String hour1 = hour.getText().toString().trim();


       //Verifica se os campos não estão vazios
        if(name1 == null || name1.isEmpty() || description1 == null || description1.isEmpty()
                || day1 == null || day1.isEmpty() || local1 == null || local1.isEmpty() || hour1 == null || hour1.isEmpty()
        || dateNumber == null){


            MakeToast.makeToast(this , getString(R.string.campos_vazios));

        }else if(day1 == null || day1.isEmpty()) {
            MakeToast.makeToast(this , getString(R.string.campos_data));
        }else{

            if (state == 1) {
                MeetingUpdateFirebase updateFirebase = new MeetingUpdateFirebase(name1, description1,dateNumber, local1, hour1, uri);
                updateFirebase.uploadImageMeeting();
                MakeToast.makeToast(this, "Atualizado!");
            } else {
                MeetingCreateFirebase meeting = new MeetingCreateFirebase(name1, description1,dateNumber, local1, hour1, uri);
                meeting.uploadImageMeeting();
                MakeToast.makeToast(this, "Criado!");
            }

            startActivity(new Intent(this, FragmentHandler.class));
        }
    }


    private void getfMeeting() {
        FirebaseFirestore.getInstance().document(pathMeeting)
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
                    image = documentSnapshot.get("foto").toString();
                    if(!image.isEmpty()) {
                        Picasso.get().load(image).into(imageView);
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

    public void createDialogMemberEdit(View v){
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        String name = shared.getString("name" , "");
        switch (v.getId()) {

             //add membro
            case R.id.meeting_edit_button_add_member:

                MakeToast.makeToast(this , "add");
                new DialogMeeting().createDialogOkAddRemove(this,0 , name);
            break;
            //rem membro
            case R.id.meeting_edit_button_remove_member:

                MakeToast.makeToast(this , "rem");
                new DialogMeeting().createDialogOkAddRemove(this ,1 , name);
                break;
            default:
                break;
        }
        }

    public void excludeMeetingButton(View v){
            new DialogRemoveConfirmation().createDialogRemoveConfirmation(this ,
                    "Quer Excluir o Encontro?" , "Excluir o Encontro" , this);
    }

    @Override
    public void DialogConfirmation() {
        MeetingFirebase.excludeMeeting();
    }

    @Override
    public void dateUpdate(String date) {
        dateNumber = Integer.parseInt(date);
        dessasemble(Integer.parseInt(date));
    }

    private void dessasemble(Integer integer){
        String date2 = String.valueOf(integer);
        String year1 = date2.substring(0,4);
        String month1 = date2.substring( 4,6);
        String day1 = date2.substring(6);

        //textView.setText(data);
        day.setText(day1 + "/" + month1 + "/" + year1);
    }
}
