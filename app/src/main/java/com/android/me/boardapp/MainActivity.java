package com.android.me.boardapp;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    String TAG=this.getClass().getName();
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

        //리스트뷰와 리스너와의 연결
        listView.setOnItemClickListener(this);
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

    //아이템을 선택하면....
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this , "선택한 게시물의 board_id는 " + id, Toast.LENGTH_SHORT).show();

        TextView txt_title=(TextView) view.findViewById(R.id.txt_title);
        TextView txt_writer=(TextView) view.findViewById(R.id.txt_writer);
        Board board=listAdapter.list.get(position);
        String detail=board.getContent();

        //상세보기 화면으로 전환!!
        Intent intent=new Intent(this, DetailActivity.class);

        intent.putExtra("board_id", id);
        intent.putExtra("title", txt_title.getText().toString());
        intent.putExtra("writer", board.getWriter()); //이것도 가능
        intent.putExtra("content", detail);

        startActivity(intent);
    }

}




