package com.example.telas_background.dialog_toast;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.telas_background.R;

public class DialogDate {
    private DatePicker date;
    private DateListener listener;

    public void createDialogOk(final Context context , DateListener listener){


        LayoutInflater inflater =  LayoutInflater.from(context);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View view1 = inflater.inflate(R.layout.dialog_calendar, null);

        this.listener = listener;

        Button button = view1.findViewById(R.id.dialog_calendar_button_ok);
        date = view1.findViewById(R.id.dialog_calendar_calendarView);
        date.setMinDate(System.currentTimeMillis());



        mBuilder.setView(view1);
        final AlertDialog dialog = mBuilder.create();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assemble();
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void assemble(){

        String year = date.getYear() + "";
        String mt = date.getMonth() + "";
        String day = date.getDayOfMonth() + "";
        if (mt.length() == 1){
            mt = "0"+ mt;
        }
        if(day.length() < 2){
            day = "0" + day;
        }

        String assemble = year + mt + day;
        listener.dateUpdate(assemble);
    }


}
