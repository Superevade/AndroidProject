package com.example.pierre.chisterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;

import java.util.ArrayList;
import java.util.List;

import static com.example.pierre.chisterapp.MySQLiteHelper.COLUMN_FAUTES2;

/**
 * Created by pierre on 30/03/2018.
 */

public class MatchsDataSource {


    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TEAM1, MySQLiteHelper.COLUMN_TEAM2, MySQLiteHelper.COLUMN_LONG, MySQLiteHelper.COLUMN_LAT,MySQLiteHelper.COLUMN_SCORE1, MySQLiteHelper.COLUMN_FAUTES1,MySQLiteHelper.COLUMN_SCORE2, COLUMN_FAUTES2};

    public MatchsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Match createMatch(String team1, String team2, String longitude, String latitude,String score1, String fautes1,String score2, String fautes2) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TEAM1, team1);
        values.put(MySQLiteHelper.COLUMN_TEAM2, team2);
        values.put(MySQLiteHelper.COLUMN_LONG, longitude);
        values.put(MySQLiteHelper.COLUMN_LAT, latitude);
        values.put(MySQLiteHelper.COLUMN_SCORE1, score1);
        values.put(MySQLiteHelper.COLUMN_FAUTES1, fautes1);
        values.put(MySQLiteHelper.COLUMN_SCORE2, score2);
        values.put(COLUMN_FAUTES2, fautes2);
        long insertId = database.insert(MySQLiteHelper.TABLE_MATCHS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MATCHS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Match newMatch = cursorToMatch(cursor);
        cursor.close();
        return newMatch;
    }

    public void update(long id, String score1, String faute1, String score2, String faute2 ) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_SCORE1,score1);
        contentValues.put(MySQLiteHelper.COLUMN_FAUTES1,faute1);
        contentValues.put(MySQLiteHelper.COLUMN_SCORE2,score2);
        contentValues.put(MySQLiteHelper.COLUMN_FAUTES2,faute2);


        database.update(MySQLiteHelper.TABLE_MATCHS, contentValues, MySQLiteHelper.COLUMN_ID+ "=" + id, null);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MATCHS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        Match newMatch = cursorToMatch(cursor);
        cursor.close();


    }

    public void deleteMatch(long id) {

        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_MATCHS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Match> getAllMatchs() {
        List<Match> comments = new ArrayList<Match>();
        List<Match> matchs = new ArrayList<Match>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MATCHS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Match match = cursorToMatch(cursor);
            comments.add(match);
            cursor.moveToNext();
        }

        cursor.close();
        if(comments.size()>5)
        {

            matchs.add(comments.get((comments.size()-1)));
            matchs.add(comments.get((comments.size()-2)));
            matchs.add(comments.get((comments.size()-3)));
            matchs.add(comments.get((comments.size()-4)));
            matchs.add(comments.get((comments.size()-5)));
            return matchs;

        }

        else {

            return comments;

        }

    }

    private Match cursorToMatch(Cursor cursor) {
        Match match = new Match();
        match.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        match.setTeam1(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_TEAM1)));
        match.setTeam2(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_TEAM2)));
        match.setLongitude(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_LONG)));
        match.setLatitude(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_LAT)));
        match.setScore1(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_SCORE1)));
        match.setFaute1(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_FAUTES1)));
        match.setScore2(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_SCORE2)));
        match.setFaute2(cursor.getString(cursor.getColumnIndex(COLUMN_FAUTES2)));
        return match;
    }
}
