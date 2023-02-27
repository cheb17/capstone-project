package com.example.capstoneapp;

public class Record {
    private String date;
    private String teamName;
    private String opponent;

    public Record(String date, String teamName, String opponent) {
        this.date = date;
        this.teamName = teamName;
        this.opponent = opponent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}

