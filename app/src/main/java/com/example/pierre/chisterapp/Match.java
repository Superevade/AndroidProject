package com.example.pierre.chisterapp;

/**
 * Created by pierre on 30/03/2018.
 */

public class Match {
    private long id;
    private String team1;
    private String team2;
    private String longitude;
    private String latitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team) {
        this.team1 = team;
    }
    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team) {
        this.team2 = team;
    }

    public String getLong() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLat() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {

     String s = "Match opposant " + team1 +  " à " + team2 +"\n" + longitude + "\n" + latitude;
        return s;
    }
}
