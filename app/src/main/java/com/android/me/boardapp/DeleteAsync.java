package com.android.me.boardapp;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * Created by zino on 2016-06-21.
 */
public class DeleteAsync extends AsyncTask<String, Void, String>{
    URL url;
    HttpURLConnection con;
    int board_id;

    public DeleteAsync(int board_id){
        this.board_id=board_id;
    }

    protected String doInBackground(String... params) {
        try {
            url = new URL("http://192.168.13.11:9090/board/delete.jsp?board_id="+board_id);
            con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);

            con.getResponseCode();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
