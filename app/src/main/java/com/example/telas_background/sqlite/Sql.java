package com.example.telas_background.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sql extends SQLiteOpenHelper {

    private static final String dbName = "banco.db";
    private static final int version = 1;
    private String create;
    private String delete;


    public Sql(Context context , String create , String delete){
        super(context, dbName,null, version);

        this.create = create;
        this.delete = delete;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(delete);
        onCreate(db);
    }

}
