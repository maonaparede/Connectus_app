package com.example.telas_background.initialize;

import android.content.Context;

import com.example.telas_background.sqlite.FriendsSqlController;

public class Initialize {

    private Context context;

    public Initialize Initialize(Context context1) {
        context = context1;

        return this;
    }

    public void friendListener(){
        FriendsSqlController friendsSqlController = new FriendsSqlController(context);
        //logica
    }

    public void meetingListener(){

    }
}
