package com.android.me.boardapp;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    static MainActivity mainActivity;
    String serverIP;
    ListView listView;
    ListAdapter listAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        listView =(ListView)findViewById(R.id.listView);
        listAdapter = new ListAdapter(this);
        listView.setAdapter(listAdapter);
    }

    //보여질 메뉴를 설정하는 메서드!!
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    //글 등록 메서드 정의
    public void regist(){
        Intent intent  = new Intent(this, WriteActivity.class);
        startActivity(intent);
    }

    //리스트 불러오기 메서드 정의
    public void getList(){
        listAdapter.loadData();
        listAdapter.notifyDataSetChanged();
    }

    //메뉴를 선택하면 호출되는 메서드 !!
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_write){
            regist();
        }else if(item.getItemId() == R.id.menu_refresh){
            getList();
        }
        return super.onOptionsItemSelected(item);
    }
}
