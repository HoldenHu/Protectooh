package com.scu.holden.protectooth.utlis;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HP on 2017/11/1.
 */

public class getReturnList {
    public static String getResult(String uploadUrl) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            Log.e("ReturnList","over00");

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"img\"; filename=\"test222.jpg\"" + end);
            dos.writeBytes(end);
            System.out.println("send to server............");
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            System.out.println("send to server over");

            // 读取服务器返回结果
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            Log.e("ReturnList","over1");
            BufferedReader br = new BufferedReader(isr);
            Log.e("ReturnList","over2");
            String result = br.readLine();
            Log.e("ReturnList","over3");
//                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            Log.e("ReturnList","over4");
            Log.e("ReturnList",result);
            is.close();
            dos.close();
//            mLog.d(result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "none";
    }
}
