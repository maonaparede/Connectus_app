package com.example.telas_background.dialog_toast;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.telas_background.R;
import com.example.telas_background.firebase.FriendRequestFirebase;

public class DialogFriendRemove {

    public static Button ok;
    public static Button cancel;
    public static TextView title;
    public static TextView content;

    public void createDialogOkCancel(final Context context, final String user2){

        LayoutInflater inflater =  LayoutInflater.from(context);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view1 = inflater.inflate(R.layout.dialog_ok_cancel, null);

        ok = view1.findViewById(R.id.dialog_ok_cancel_button_ok);
        cancel = view1.findViewById(R.id.dialog_ok_cancel_button_cancel);
        title = view1.findViewById(R.id.dialog_ok_cancel_textview_title);
        content = view1.findViewById(R.id.dialog_ok_cancel_textview_content);

        title.setText(R.string.title_dialog_friend_remove);
        content.setText(R.string.content_dialog_friend_remove);


        mBuilder.setView(view1);
        final AlertDialog dialog = mBuilder.create();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendRequestFirebase.removeFriend(user2);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
