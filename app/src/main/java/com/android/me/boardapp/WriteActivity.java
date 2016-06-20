package com.android.me.boardapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/*
 * Created by Administrator on 2016-06-14.
 */
public class WriteActivity  extends AppCompatActivity {
    String TAG=this.getClass().getName();

    /*
    * 웹서버는 HTTP 프로토콜로 요청을 해야 응답을 하므로,
    * 우리의 앱에서 HTTP 요청을 시도해야 한다. 이러한 목적으로 지원되는 객체가
    * 바로 javaSE 의 HttpURLConnection 객체이다!!
    * */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.write_layout);
    }
    public void regist(View view){
        Log.d(TAG, "regist 호출??");

        //비동기 객체에게 네트워크 작업을 시키자!!!
        MyAsync myAsync = new MyAsync();
        myAsync.execute("http://192.168.1.113:9090/board/regist.jsp");


    }
    public void close(){

    }
}





