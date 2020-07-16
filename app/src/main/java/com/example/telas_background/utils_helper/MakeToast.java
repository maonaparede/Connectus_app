package com.example.telas_background.utils_helper;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_background.R;

public class MakeToast {

    public static void makeToast(Context context , String texto) {
        Toast.makeText(context, texto,
                Toast.LENGTH_SHORT).show();
    }
}
