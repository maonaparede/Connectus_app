package com.example.telas_background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_background.Classes_estaticas.UserPrincipal;
import com.example.telas_background.firebase.MeetingFirebase;
import com.example.telas_background.dialog_toast.DialogRemoveConfirmation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Meeting extends AppCompatActivity {

    private Integer state = 0;

    private TextView title1;
    private TextView description1;
    private TextView day1;
    private TextView hour1;
    private TextView local1;
    private ImageView imageView;
    private Button buttonExit;

    private String image;
    private String path;
    private static String owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        title1 = findViewById(R.id.meeting_textview_title);
        description1 = findViewById(R.id.meeting_textview_description);
        day1 = findViewById(R.id.meeting_textview_day);
        hour1 = findViewById(R.id.meeting_textview_hour);
        local1 = findViewById(R.id.meeting_textview_local);
        buttonExit = findViewById(R.id.meeting_button_exit);
        imageView = findViewById(R.id.meeting_imageview_image);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            owner = bundle.getString("dono");
            path = "encontro/"+ owner;

            if(owner.equals(UserPrincipal.getId())){
                buttonExit.setText("Editar");
                state = 1;
            }

            getfMeeting();
        }
    }

    private void getfMeeting() {
        FirebaseFirestore.getInstance().document(path)
                .collection("atributos").document("atributos").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    title1.setText(documentSnapshot.get("nome").toString());
                    description1.setText(documentSnapshot.get("descricao").toString());
                    day1.setText(documentSnapshot.get("dia").toString());
                    hour1.setText(documentSnapshot.get("horario").toString());
                    local1.setText(documentSnapshot.get("local").toString());
                    image = documentSnapshot.get("foto").toString();
                    Picasso.get().load(image).into(imageView);
                }
            }
        });
    }

    public void toChat(View v){
        Intent intent = new Intent(this, Chat.class);
        Bundle bundle = new Bundle();

        bundle.putString("nome", title1.getText().toString());
        bundle.putString("foto", image);
        bundle.putString("path", path);

        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void toEditOrExit(View v){
        switch (state){
            case 0:
                //abre dialog sai do encontro
                DialogRemoveConfirmation.createDialogRemoveConfirmation(this ,
                        "Quer Sair do Encontro?" , "Sair do Encontro" , 0);
                break;
            case 1:
                Intent intent = new Intent(this, MeetingEdit.class);
                Bundle bundle = new Bundle();

                bundle.putString("encontro", path);
                bundle.putInt("estado", 1);

                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case 2:
                break;
        }
    }

    public static void exitEncontro(){
            MeetingFirebase.exitMeeting(owner);
            //context.startActivity(new Intent(context, Home.class));
    }

}
