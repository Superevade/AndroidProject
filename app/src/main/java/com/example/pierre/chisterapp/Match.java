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
    private String score1;
    private String faute1;
    private String score2;
    private String faute2;


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

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score) {
        this.score1 = score;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score) {
        this.score2 = score;
    }

    public String getFaute1() {
        return faute1;
    }

    public void setFaute1(String faute) {
        this.faute1 = faute;
    }

    public String getfaute2() {
        return faute2;
    }

    public void setFaute2(String faute) {
        this.faute2 = faute;
    }






    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {

     String s = "ID du match : "+ id +"\n"+"Match opposant " + team1 +  " à " + team2 +"\n" + longitude + "\n" + latitude + "\n" +
        "Score de l'équipe 1 :  " + score1 + "\n"+"Nombre de fautes de l'équipe 1 :  " + faute1 + "\n" +
             "Score de l'équipe 2 :  " + score2 + "\n"+"Nombre de fautes de l'équipe 2 :  " + faute2 + "\n" ;
        return s;
    }
}
