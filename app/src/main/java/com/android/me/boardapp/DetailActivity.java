package com.android.me.boardapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity{
    EditText edit_title, edit_writer, edit_content;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_layout);

        Intent intent = getIntent();
        long board_id=intent.getLongExtra("board_id",0);
        String title=intent.getStringExtra("title");
        String writer = intent.getStringExtra("writer");
        String content= intent.getStringExtra("content");

        edit_title=(EditText) findViewById(R.id.edit_title);
        edit_writer=(EditText) findViewById(R.id.edit_writer);
        edit_content=(EditText) findViewById(R.id.edit_content);

        edit_title.setText(title);
        edit_writer.setText(writer);
        edit_content.setText(content);
    }

    public void delete(View view){
        // 서버의 delete from board where board_id=3 이런 동작을 요청!!

    }

}







