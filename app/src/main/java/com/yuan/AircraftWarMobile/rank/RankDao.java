package com.yuan.AircraftWarMobile.rank;

public interface RankDao {
    public void store(String mode);
    public void load(String mode);
    public void add(String name,int score,String date);
    public void remove(int rank);
    public void clear();
    public String[][] toTable();
}
