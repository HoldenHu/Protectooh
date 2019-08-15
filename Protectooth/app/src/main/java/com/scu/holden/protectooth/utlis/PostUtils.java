package com.scu.holden.protectooth.utlis;

/**
 * Created by mirrorssssssss on 5/3/17.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostUtils {
    public static String post(JSONObject obj,String URL) {
        try {

            System.out.println(obj);
            // 创建url资源
            URL url = new URL(URL);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);

            conn.setDoInput(true);

            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            //转换为字节数组
            byte[] data = (obj.toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));

            // 设置文件类型:
            conn.setRequestProperty("contentType", "application/json");

            // 开始连接请求
            conn.connect();
            OutputStream  out = conn.getOutputStream();
            // 写入请求的字符串
            out.write((obj.toString()).getBytes());
            out.flush();
            out.close();

            // 请求返回的状态
            if (conn.getResponseCode() == 200) {

                // 请求返回的数据
                InputStream in = conn.getInputStream();


                String a = null;
                try {
                    int data_length=0;

                    in.read(new byte[0]);
                    data_length = in.available();
                    Log.e("Holden", "return_length: "+String.valueOf(data_length));


                    byte[] data1 = new byte[data_length];

                    in.read(data1);

                    // 转成字符串
                    a = new String(data1);


                    return a;
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    Log.e("Holden", "error");
                    e1.printStackTrace();
                }
            } else {
                Log.e("Holden", "no++");
            }

        } catch (Exception e) {
            Log.e("Holden", "error0");
        }
        return "";

    }



    public static String get(String URL) {
        try {
            // 创建url资源
            URL url = new URL(URL);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");// 设置请求的方式
            conn.setReadTimeout(5000);// 设置超时的时间
            conn.setConnectTimeout(5000);// 设置链接超时的时间
            // 设置请求的头
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");



            // 请求返回的状态
            if (conn.getResponseCode() == 200) {

                // 请求返回的数据
                InputStream in = conn.getInputStream();

                String a = null;
                try {
                    int data_length=0;

                    in.read(new byte[0]);
                    data_length = in.available();
                    Log.e("Holden", "return_length: "+String.valueOf(data_length));


                    byte[] data1 = new byte[data_length];

                    in.read(data1);

                    // 转成字符串
                    a = new String(data1);


                    return a;
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    Log.e("Holden", "error1");
                    e1.printStackTrace();
                }
            } else {
                Log.e("Holden", "no++");
            }

        } catch (Exception e) {
            Log.e("Holden", "error0");
        }
        return "";

    }



    public static Bitmap getInternetPicture(String UrlPath) {
        Bitmap bm = null;
        // 1、确定网址
        // http://pic39.nipic.com/20140226/18071023_164300608000_2.jpg
        String urlpath = UrlPath;
        // 2、获取Uri
        try {
            URL uri = new URL(urlpath);

            // 3、获取连接对象、此时还没有建立连接
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            // 4、初始化连接对象
            // 设置请求的方法，注意大写
            connection.setRequestMethod("GET");
            // 读取超时
            connection.setReadTimeout(5000);
            // 设置连接超时
            connection.setConnectTimeout(5000);
            // 5、建立连接
            connection.connect();

            // 6、获取成功判断,获取响应码
            if (connection.getResponseCode() == 200) {
                // 7、拿到服务器返回的流，客户端请求的数据，就保存在流当中
                InputStream is = connection.getInputStream();
                // 8、从流中读取数据，构造一个图片对象GoogleAPI
                bm = BitmapFactory.decodeStream(is);
                // 9、把图片设置到UI主线程
                // ImageView中,获取网络资源是耗时操作需放在子线程中进行,通过创建消息发送消息给主线程刷新控件；

                Log.i("", "网络请求成功");

            } else {
                Log.v("tag", "网络请求失败");
                bm = null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;

    }


}
