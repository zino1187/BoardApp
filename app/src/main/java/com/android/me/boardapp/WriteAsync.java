package com.android.me.boardapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * 네트워크 작업은 안드로이드의 메인 실행부가 절대 수행할 수없다!!
 * 그 이유?? 만일 메인쓰레드가 네트워크 접속행위를 하게 되면, 네트워크 지연발생시
 * 스마트폰의 UI, Event 등 운영은 누가 담당하는가?? 그래서 금지시킨다!!
 *
 * 해결책)  쓰레드를 이용하여 네트워크 연결을시도하면 되지만, 서버로 부터 가져온 데이터를
 *             쓰레드가 UI에 반영하지 못하도록 금지시키다.. 따라서 Handler를 지원한다...
  *             하지만, 쓰레드가 언제 시점에 네트워크상의 요청을 완료할지는 알수가없으므로, 안정된
  *             작업이 보장되지 않는다..
  *
  * 개선책) AsyncTask =  쓰레드 + 핸들러+ ajax의 onreadystatechange 이벤트가 합쳐진 편한객체..
  *            쓰레드가 언제 요청을 완료했는지 여부를 알려주며, 각종 편한 메서드를 가지고 있다!
 */
public class WriteAsync extends AsyncTask<String, Void, String>{
    Context context;
    String TAG=this.getClass().getName();
    //원격지에 떨어진 웹서버에게 글 등록을 요청하자!!(http 요청 post/get)
    URL url=null;
    HttpURLConnection con=null;

    public WriteAsync(Context context){
        this.context=context;
    }

    /* 비동기로 웹서버에 요청을 시도할때 주로 사용하는 메서드!!
    * 주의) 이 메서드는 쓰레드가 수행한다!! 여기서 개발자가 반드시 짚고넘어가야할것은
     *        이 메서드내에서는 UI제어할 수 없다!!
    * */
    protected String doInBackground(String... params) {
        StringBuffer sb = null;

        try {
            url = new URL(params[0]);
            con=(HttpURLConnection)url.openConnection();

            //브라우저 흉내내자~! (헤더 정보 셋팅)
            con.setRequestMethod("POST"); //post방식으로 요청하겠다!!!
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            con.setDoOutput(true); //데이터를 내보내겠다!!
            con.setDoInput(true); //데이터를 가지고도 오겟다!!

            //파라미터 값들을 전송하자!! ( 바디 정보 셋팅)
            BufferedWriter buffw=new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "utf-8"));
            buffw.write("title="+params[1]+"&writer="+params[2]+"&content="+params[3]+"\n");
            buffw.flush();
            buffw.close();

            //웹서버에 요청이 일어나려면 반드시 getResponseCode()
            int code=con.getResponseCode();
            Log.d(TAG, "code is "+code);

            //서버의 요청 처리가 성공되면, json으로 받은 데이터에 따라 알맞는 메세지를 출력해주자!!
            if(code == HttpURLConnection.HTTP_OK){
                //입력스트림으로 데이터 가져오기!!
                BufferedReader buffr = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));

                String data=null;
                sb = new StringBuffer();

                while(true){
                    data=buffr.readLine();
                    if(data==null)break;
                    sb.append(data);
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(con !=null){
                con.disconnect();
                con=null;
            }
        }

        return sb.toString();
    }

    /*
    * 요청이 끝난 시점에 호출되는 메서드!!
    * 이 메서드는 메인쓰레드에 의해호출된다. 따라서 이 메서드내에서는 UI제어가 가능하다
    * 결국 Handler를 사용할 필요가 없다!!
    * */
    protected void onPostExecute(String s) {
        //넘겨받은 json 문자열을 대상으로 의미를 파악하자!! (파싱)
        Log.d(TAG, s);

        try {
            JSONObject jsonObject = new JSONObject(s); //파싱!!
            String msg=null;

            Log.d(TAG, "resultCode is "+jsonObject.getInt("resultCode"));

            if(jsonObject.getInt("resultCode")==1){
                msg="등록 성공";
            }else if(jsonObject.getInt("resultCode")==0){
                msg="등록 실패";
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}







