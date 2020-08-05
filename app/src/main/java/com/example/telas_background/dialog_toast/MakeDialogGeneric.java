package com.example.telas_background.dialog_toast;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.telas_background.R;

public class MakeDialogGeneric {

    public static void createDialogOk(final Context context, String content1 , String title1){


        LayoutInflater inflater =  LayoutInflater.from(context);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view1 = inflater.inflate(R.layout.dialog_ok, null);

        Button ok = (Button) view1.findViewById(R.id.dialog_ok_button_ok);
        TextView title = (TextView) view1.findViewById(R.id.dialog_ok_textview_title);
        TextView content = (TextView) view1.findViewById(R.id.dialog_ok_textview_content);

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
