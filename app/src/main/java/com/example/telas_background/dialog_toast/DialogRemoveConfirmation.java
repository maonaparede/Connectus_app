package com.example.telas_background.dialog_toast;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.telas_background.MeetingEdit;
import com.example.telas_background.Meeting;
import com.example.telas_background.R;

public class DialogRemoveConfirmation{



    public static void createDialogRemoveConfirmation(final Context context, String content1 , String title1 , final Integer estado){

        LayoutInflater inflater =  LayoutInflater.from(context);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view1 = inflater.inflate(R.layout.dialog_ok_cancel, null);

        Button ok = (Button) view1.findViewById(R.id.dialog_ok_cancel_button_ok);
        Button cancel = (Button) view1.findViewById(R.id.dialog_ok_cancel_button_cancel);
        TextView title = (TextView) view1.findViewById(R.id.dialog_ok_cancel_textview_title);
        TextView content = (TextView) view1.findViewById(R.id.dialog_ok_cancel_textview_content);
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


    private static void callFunction(Integer state){
        switch (state) {
            case 0:
                Meeting.exitEncontro();
                break;
            case 1:
                MeetingEdit.FromDialogRemoveConfirmation();
                break;
            default:
                break;
        }
    }
}
