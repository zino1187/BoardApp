package com.android.me.boardapp;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/*
  웹서버로부터 게시물의 목록을가져오되, xml 형식으로 가져오자!!
  xml 파서가 있어야 한다..
 */
public class ListAsync extends AsyncTask<String, Void ,InputStream>{
    String TAG=this.getClass().getName();
    ListAdapter listAdapter;
    URL url;
    HttpURLConnection con; //웹상의 요청을 수행하는 객체!! javaSE 꺼임.

    public ListAsync(ListAdapter listAdapter) {
        this.listAdapter=listAdapter;
    }

    @Override
    protected InputStream doInBackground(String... params) {

        //xml 을 문자열들을 모아놓을 변수!!
        StringBuffer sb = new StringBuffer();
        InputStream is=null;

        try {
            url = new URL(params[0]);
            con=(HttpURLConnection) url.openConnection();

            //브라우저가 수행하는 업무를 따라해보자~~
            //브라우저는 요청시 헤더 정보에 여러 데이터를 셋팅해서 출발한다.
            con.setRequestMethod("GET");
            con.setDoInput(true); //xml 데이터를 앱으로 가져오기!! ( 입력임을 알리기)

            int code=con.getResponseCode(); //서버에 요청시도 및 응답코드 받기!!

            Log.d(TAG, "서버의 응답결과 코드는 "+code);  //200

            if(code == HttpURLConnection.HTTP_OK){ // 200을 의미하는 상수값!!
                //xml 데이터를 현지 실행중인 앱으로 가져온다!

                BufferedReader buffr = new BufferedReader(new InputStreamReader(is=con.getInputStream(), "utf-8"));
                /*
                String data=null;
                while(true){
                    data=buffr.readLine(); //한줄 담기!!
                    if(data==null)break;
                    Log.d(TAG, data);
                    sb.append(data); //xml 모으기!!
                }
                */
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    @Override
    protected void onPostExecute(InputStream s) {
        //모아진 xml 문자열을 의미있는 데이터를 추출(파싱)하여 스마트폰에 맞게 보여주기
        //ListView 에 보여주자!!
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser=saxParserFactory.newSAXParser();
            MyHandler myHandler = new MyHandler();
            saxParser.parse( s ,  myHandler);
            //파싱이 끝난 시점에는 ArrayList에 데이터가 채워져 있을 것이므로, 이 데이터를
            //ListAdapter가 보유한 ArrayList에 대입하자!!
            listAdapter.list = myHandler.list;
            listAdapter.notifyDataSetChanged();// 리스트 뷰 갱신요청

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
