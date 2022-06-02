package com.yuan.AircraftWarMobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuan.AircraftWarMobile.R;
import com.yuan.AircraftWarMobile.application.Main;
import com.yuan.AircraftWarMobile.online.OnlineInfo;
import com.yuan.AircraftWarMobile.rank.RankDaoImp;
import com.yuan.AircraftWarMobile.rank.Record;
import com.yuan.AircraftWarMobile.settings.Settings;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class RankActivity extends AppCompatActivity {
    private static final String TAG = "RankActivity";
    private List<Record> list;
    private RankDaoImp dao;
    private MyAdapter adapter;
    private ListView lv;
    private TextView tableTitle;
    private static String currenttime;
    private AlertDialog resultDialog;
    private AlertDialog waitingDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        if (Settings.opponentName!=null) {
            initDialog();
            setResult();
        }
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initView();
        dao = new RankDaoImp(this);

        //访问网络的代码放在这里
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        currenttime = sdf.format(System.currentTimeMillis());
        Record record = new Record(1, Settings.nickname, Main.settings.score, currenttime);
        dao.insert(record);
        try {
            list = dao.queryAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }

    private void initView() {
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
            if (list != null) {
                return list.size();
            } else return 0;
        }

        public Object getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView != null ? convertView : View.inflate(
                    getApplicationContext(), R.layout.item, null);
            TextView idView = (TextView) item.findViewById(R.id.idTV);
            TextView nameView = (TextView) item.findViewById(R.id.nameTV);
            TextView scoreView = (TextView) item.findViewById(R.id.scoreTV);
            TextView dateView = (TextView) item.findViewById(R.id.dateTV);
            final Record record = list.get(position);
            idView.setText(record.getId() + "");
            nameView.setText(record.getName() + "");
            scoreView.setText(record.getScore() + "");
            dateView.setText(record.getDate() + "");
            return item;
        }
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Record r = (Record) parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), r.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initDialog() {
        waitingDialog = new AlertDialog.Builder(this)
                .setTitle("请稍候...")//设置标题
                .setMessage("对手仍在酣战中")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Settings.opponentDeath = -1;
                        Toast.makeText(RankActivity.this, "已取消等待", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .create();
        resultDialog = new AlertDialog.Builder(this)
                .setTitle(Settings.opponentDeath == 1 ? "YOU WIN!" : "YOU LOSE!")//设置标题
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
    }

    private void setResult(){
//        waitingDialog.show();
//        new Thread() {
//            @Override
//            public void run() {
//                while (Settings.opponentDeath == 0) {
//                    Settings.opponentDeath = OnlineInfo.getDeath();
//                }
//                waitingDialog.dismiss();
//            }
//        }.start();
//        if (Settings.opponentDeath!=-1) {
//            resultDialog.show();
//        }
        synchronized (Main.Death_LOCK) {
            resultDialog.show();
        }
    }
}
