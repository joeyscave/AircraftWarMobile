package com.yuan.AircraftWarMobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuan.AircraftWarMobile.R;
import com.yuan.AircraftWarMobile.application.Main;
import com.yuan.AircraftWarMobile.rank.RankDaoImp;
import com.yuan.AircraftWarMobile.rank.Record;
import com.yuan.AircraftWarMobile.settings.Settings;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class RankActivity extends AppCompatActivity {
    private List<Record> list;
    private RankDaoImp dao;
    private MyAdapter adapter;
    private ListView lv;
    private TextView tableTitle;
    private static String currenttime;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        initView();
        dao = new RankDaoImp(this);

        SimpleDateFormat sdf= new SimpleDateFormat("MM-dd HH:mm");
        currenttime = sdf.format(System.currentTimeMillis());
        Record record = new Record(1,"Player", Main.settings.score,currenttime);
        dao.insert(record);
        try {
            list=dao.queryAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }
    private void initView(){
        lv = (ListView) findViewById(R.id.l1);
        lv.setOnItemClickListener(new MyOnItemClickListener());
        tableTitle = (TextView) findViewById(R.id.tableTitle);
        switch (Settings.backGroundIndex / 2) {
            case 0:
                tableTitle.setText("简单模式排行榜");
                break;
            case 1:
                tableTitle.setText("普通模式排行榜");
                break;
            case 2:
                tableTitle.setText("困难模式排行榜");
                break;
            default:
        }
    }

    private class MyAdapter extends BaseAdapter {
        public int getCount() {
            return list.size();
        }
        public Object getItem(int position) {
            return list.get(position);
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView != null ? convertView : View.inflate(
                    getApplicationContext(), R.layout.item, null            );
            TextView idView = (TextView) item.findViewById(R.id.idTV);
            TextView nameView = (TextView) item.findViewById(R.id.nameTV);
            TextView scoreView = (TextView) item.findViewById(R.id.scoreTV);
            TextView dateView = (TextView) item.findViewById(R.id.dateTV);
            final Record record = list.get(position);
            idView.setText(record.getId()+"");
            nameView.setText(record.getName()+"");
            scoreView.setText(record.getScore()+"");
            dateView.setText(record.getDate()+"");
            return item;
        }
    }
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Record r = (Record) parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(),r.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
