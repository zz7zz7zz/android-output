package com.open.iandroidtsing.notification;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/17.
 */

public class NotificationHttp {

    public static void doPost(String requestUrl ,String postData){
        HttpURLConnection connection = null;
        ByteArrayOutputStream mOutStream = null;
        InputStream mInStream = null;
        try {
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            OutputStream out = connection.getOutputStream();
            out.write(postData.getBytes());

            int code = connection.getResponseCode();
            if( code == 200){
                mInStream = connection.getInputStream();
                mOutStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while( (len = mInStream.read(buffer)) !=-1 ){
                    mOutStream.write(buffer, 0, len);
                }
                Log.v("NotificationHttp",new String(mOutStream.toByteArray()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if(connection != null){
                connection.disconnect();
            }

            if(mOutStream != null){
                try {
                    mOutStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(mInStream != null){
                try {
                    mInStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
