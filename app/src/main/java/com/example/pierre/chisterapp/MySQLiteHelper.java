package com.example.pierre.chisterapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pierre on 30/03/2018.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_MATCHS = "matchs";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEAM1 = "team1";
    public static final String COLUMN_TEAM2 = "team2";
    public static final String COLUMN_LONG = "long";
    public static final String COLUMN_LAT = "lat";

    private static final String DATABASE_NAME = "matchs.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MATCHS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TEAM1 + COLUMN_TEAM1
            + " text not null " + COLUMN_TEAM2
            + " text not null " + COLUMN_LONG
            + " text not null " + COLUMN_LAT
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHS);
        onCreate(db);
    }


}