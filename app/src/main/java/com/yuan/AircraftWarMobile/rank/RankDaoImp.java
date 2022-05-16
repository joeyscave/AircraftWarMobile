package com.yuan.AircraftWarMobile.rank;

import com.yuan.AircraftWarMobile.settings.Settings;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RankDaoImp implements RankDao {

    private List<Record> rank = new LinkedList<>();

    public RankDaoImp() {
        String[] mode={"easy","normal","hard"};
        this.load(mode[Settings.backGroundIndex/2]);
    }

    @Override
    public String toString() {
        String rankString =
                "**********************\n" +
                        "       得分排行榜\n" +
                        "**********************\n";
        int index = 1;
        for (Record record : rank) {
            rankString += "第" + index + "名：" + record + "\n";
            index++;
        }
        return rankString;
    }

    @Override
    public String[][] toTable() {
        if (rank == null) return null;
        String[][] tableData = new String[rank.size()][];
        int index = 1;
        for (Record record : rank) {
            String[] recordString = {Integer.toString(index), record.getName(), Integer.toString(record.getScore()), record.getDate()};
            tableData[index - 1] = recordString;
            index++;
        }
        return tableData;
    }

    /**
     * 从文件读入javabean
     */
    @Override
    public void load(String mode) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src\\edu\\hitsz\\rank\\rank_"+mode+".txt"))) {
            rank = (LinkedList<Record>) ois.readObject();
        } catch (EOFException e) {
            //忽略
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从javabean写入文件
     */
    @Override
    public void store(String mode) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src\\edu\\hitsz\\rank\\rank_"+mode+".txt"))) {
            oos.writeObject(rank);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(String name, int score, String date) {
        rank.add(new Record(score, name, date));
        System.out.println("----------");
        Collections.sort(rank);
    }

    @Override
    public void remove(int row) {
        this.rank.remove(row);
        Collections.sort(this.rank);
    }

    @Override
    public void clear() {
        rank.clear();
    }
}
