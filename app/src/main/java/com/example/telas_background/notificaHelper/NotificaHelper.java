package com.example.telas_background.notificaHelper;

import android.content.Context;
import android.widget.Toast;

public class NotificaHelper {

    public static void mostrarToast(Context context , String texto) {
        Toast.makeText(context, texto,
                Toast.LENGTH_SHORT).show();
    }
}
