package com.example.telas_background.utils_helper;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.telas_background.Editar_encontro;
import com.example.telas_background.Encontro;
import com.example.telas_background.R;
import com.example.telas_background.firebase.Encontro_firebase;

public class DialogRemoveConfirmation{


    public static Button ok;
    public static Button cancel;
    public static TextView title;
    public static TextView content;

    public static void createDialogRemoveConfirmation(final Context context, String content1 , String title1 , final Integer estado){


        LayoutInflater inflater =  LayoutInflater.from(context);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view1 = inflater.inflate(R.layout.dialog_ok_cancel, null);

        ok = (Button) view1.findViewById(R.id.ok_dialog);
        cancel = (Button) view1.findViewById(R.id.cancel_dialog);

        title = (TextView) view1.findViewById(R.id.titulo_dialog);
        content = (TextView) view1.findViewById(R.id.conteudo_dialog);

        title.setText(title1);
        content.setText(content1);


        mBuilder.setView(view1);
        final AlertDialog dialog = mBuilder.create();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callFunction(estado);

            }
        });


        dialog.show();
    }


    private static void callFunction(Integer estado){

        switch (estado) {
            case 0:
            Encontro.exitEncontro();
            break;
            case 1:
                Editar_encontro.excludeEncontro();
                break;
            default:
                break;
        }
    }
}
