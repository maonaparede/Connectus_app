package com.example.telas_background.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class FriendsSqlController {

        private SQLiteDatabase db;
        private Sql dbHelper;
        private final String SCRIPT_DB_DELETE =
                "DROP TABLE IF EXISTS friends";
        private final String SCRIPT_DB_CREATE =
                "create table friends (id VARCHAR(255) primary key NOT NULL);";
        private final String TABLE = "friends";


        public FriendsSqlController(Context ctx){
            dbHelper = new Sql(ctx, SCRIPT_DB_CREATE, SCRIPT_DB_DELETE);
            Log.d(" DB " , "Criado");
        }

        public void insert(String id){
            ContentValues cv = new ContentValues();
            cv.put("id", id);
            db = dbHelper.getWritableDatabase();
            db.insertOrThrow(TABLE, null, cv);
            db.close();
        }

        public int excludeById(String id){
            db = dbHelper.getWritableDatabase();
            int rows = db.delete(TABLE , "id=?", new String[]{id});
            return rows;
        }

        public int excludeAll(){
            db = dbHelper.getWritableDatabase();
            int rows = db.delete(TABLE , null , null);
            return rows;
        }

        public String searchById(String id){
            String resturnS = "";

            String[] columns = {"id"};

            db = dbHelper.getWritableDatabase();

            Cursor cursor = db.query(TABLE, columns,
                    "id = '" + id +"'", null, null, null, null , "1");

            while(cursor.moveToNext()) {
                resturnS = cursor.getString(0);
                Log.d("DB" , resturnS);
            }
            cursor.close();
            db.close();
            return resturnS;
        }

    public ArrayList<String> returnAll(){
        ArrayList<String> resturnS = new ArrayList<String>();

        String sql = "SELECT id FROM " + TABLE;

        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql , null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                resturnS.add(cursor.getString(cursor.getColumnIndex("id")));
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return resturnS;
}


    public Boolean verifyIfExist(String id) {

        String resturnS = "";

        String[] columns = {"id"};
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE, columns,
                "id = '" + id + "'", null, null, null, null, "1");

        while (cursor.moveToNext()) {
            resturnS = cursor.getString(0);
            Log.d("DB", resturnS);
        }
        cursor.close();
        db.close();


        return resturnS.equals(id);


    }
}
