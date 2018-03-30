package com.example.pierre.chisterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierre on 30/03/2018.
 */

public class MatchsDataSource {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TEAM1,   MySQLiteHelper.COLUMN_TEAM2,   MySQLiteHelper.COLUMN_LONG,   MySQLiteHelper.COLUMN_LAT };

    public MatchsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Match createMatch(String team1, String team2, String longitude, String latitude) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TEAM1, team1);
        values.put(MySQLiteHelper.COLUMN_TEAM2, team2);
        values.put(MySQLiteHelper.COLUMN_LONG, longitude);
        values.put(MySQLiteHelper.COLUMN_LAT, latitude);
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

    public void deleteMatch(Match match) {
        long id = match.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_MATCHS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Match> getAllComments() {
        List<Match> comments = new ArrayList<Match>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MATCHS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Match match = cursorToMatch(cursor);
            comments.add(match);
            cursor.moveToNext();
        }

        cursor.close();
        return comments;
    }

    private Match cursorToMatch(Cursor cursor) {
        Match match = new Match();
        match.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        match.setTeam1(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_TEAM1)));
        match.setTeam2(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_TEAM2)));
        match.setLongitude(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_LONG)));
        match.setLatitude(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_LAT)));
        return match;
    }
}
