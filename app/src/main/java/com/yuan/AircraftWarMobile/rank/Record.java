package com.yuan.AircraftWarMobile.rank;

import java.io.Serializable;

public class Record implements Serializable,Comparable<Record> {
    private int score;
    private String name;
    private String date;

    public Record(int score, String name, String date) {
        this.score = score;
        this.name = name;
        this.date = date;
    }

    @Override
    public int compareTo(Record record) {
        return record.getScore()-this.getScore();
    }

    @Override
    public String toString() {
        return name+","+score+","+date;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
