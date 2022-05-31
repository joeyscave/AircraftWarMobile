package com.yuan.AircraftWarMobile.rank;

import java.io.Serializable;

public class Record implements Serializable, Comparable<Record> {
    private int id;
    private int score;
    private String name;
    private String date;

    public Record(int id, String name, int score, String date) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.date = date;
    }

    @Override
    public int compareTo(Record record) {
        return record.getScore() - this.getScore();
    }

    @Override
    public String toString() {
        return id + "," + name + "," + score + "," + date;
    }

    public int getId() {
        return id;
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

    public void setId(int rank) {
        this.id = rank;
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
