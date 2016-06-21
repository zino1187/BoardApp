package com.android.me.boardapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Created by Administrator on 2016-06-16.
 */
public class ListAdapter extends BaseAdapter {
    Context context;
    ListAsync listAsync; //웹연동에 사용할 비동기 객체!!
    ArrayList<Board> list = new ArrayList<Board>();
    LayoutInflater layoutInflater;

    public ListAdapter(Context context) {
        this.context=context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loadData();
    }

    //리스트 가져오기(갱신) 메서드!!
    public void loadData() {
        listAsync = new ListAsync(this); //비동기 객체 메모리에 생성!!
        listAsync.execute("http://192.168.13.11:9090/xml/list.jsp");
    }


    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=null;
        if(convertView ==null){
            view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        }else{
            view=convertView;
        }

        //아이템에 알맞는 데이터 대입!!!
        TextView txt_title=(TextView)view.findViewById(R.id.txt_title);
        TextView txt_writer=(TextView)view.findViewById(R.id.txt_writer);
        TextView txt_regdate=(TextView)view.findViewById(R.id.txt_regdate);

        Board board=list.get(position);

        txt_title.setText(board.getTitle());
        txt_writer.setText(board.getWriter());
        txt_regdate.setText(board.getRegdate());

        return view;
    }
}
