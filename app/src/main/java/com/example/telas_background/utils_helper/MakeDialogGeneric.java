package com.example.telas_background.utils_helper;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.telas_background.R;
import com.example.telas_background.firebase.Friend_request_firebase;

public class MakeDialogGeneric {

    public static Button ok;
    public static TextView title;
    public static TextView content;

    public static void createDialogOk(final Context context, String content1 , String title1){


        LayoutInflater inflater =  LayoutInflater.from(context);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view1 = inflater.inflate(R.layout.dialog_ok, null);

        ok = (Button) view1.findViewById(R.id.ok_dialog_ok);
        title = (TextView) view1.findViewById(R.id.titulo_dialog_ok);
        content = (TextView) view1.findViewById(R.id.conteudo_dialog_ok);

        title.setText(title1);
        content.setText(content1);


        mBuilder.setView(view1);
        final AlertDialog dialog = mBuilder.create();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

}
