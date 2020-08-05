package com.example.telas_background.dialog_toast;

import android.content.Context;
import android.widget.Toast;

public class MakeToast {

    public static void makeToast(Context context , String text) {
        Toast.makeText(context, text,
                Toast.LENGTH_SHORT).show();
    }

}
