package com.example.telas_background.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_background.Chat;
import com.example.telas_background.FragmentHandler;
import com.example.telas_background.dialog_toast.ConfirmationDialog;
import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.MeetingEdit;
import com.example.telas_background.R;
import com.example.telas_background.firebase.MeetingFirebase;
import com.example.telas_background.dialog_toast.DialogRemoveConfirmation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class FragmentMeeting extends Fragment implements ConfirmationDialog {

    private Integer state = 0;

    private TextView title1;
    private TextView description1;
    private TextView day1;
    private TextView hour1;
    private TextView local1;
    private ImageView imageView;
    private Button buttonExit;
    private Button buttonToChat;

    private String image;
    private String path;
    private static String owner;
    private static Context context;


    @Override
    public void onAttach(@NonNull Context context1) {
        context = getActivity();
        super.onAttach(context1);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_meeting, container , false);

        title1 = root.findViewById(R.id.meeting_textview_title);
        description1 = root.findViewById(R.id.meeting_textview_description);
        day1 = root.findViewById(R.id.meeting_textview_day);
        hour1 = root.findViewById(R.id.meeting_textview_hour);
        local1 = root.findViewById(R.id.meeting_textview_local);
        buttonExit = root.findViewById(R.id.meeting_button_exit);
        buttonToChat = root.findViewById(R.id.meeting_button_chat);
        imageView = root.findViewById(R.id.meeting_imageview_image);

        Bundle bundle = getArguments();
        if(bundle != null) {
            owner = bundle.getString("dono");
            path = "encontro/"+ owner;

            if(owner.equals(UserPrincipal.getId())){
                buttonExit.setText("Editar");
                state = 1;
            }

            getfMeeting();
        }

        buttonToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toChat(v);
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEditOrExit(v);
            }
        });

        return root;
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
                    if(!image.isEmpty()) {
                        Picasso.get().load(image).into(imageView);
                    }
                }
            }
        });
    }

    public void toChat(View v){
        Intent intent = new Intent(getActivity(), Chat.class);
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

                new DialogRemoveConfirmation().createDialogRemoveConfirmation(context ,
                        "Quer Sair do Encontro?" , "Sair do Encontro" , this);

                break;
            case 1:
                Intent intent = new Intent(getActivity(), MeetingEdit.class);
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


    @Override
    public void DialogConfirmation() {
        MeetingFirebase.exitMeeting(owner);
        context.startActivity(new Intent(context, FragmentHandler.class));
    }
}
