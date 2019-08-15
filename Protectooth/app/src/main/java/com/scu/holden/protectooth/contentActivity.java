package com.scu.holden.protectooth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.scu.holden.protectooth.localdatbase.UserDatabaseManager;
import com.scu.holden.protectooth.utlis.PostUtils;

/**
 * Created by mirrorssssssss on 5/22/17.
 */


public class contentActivity extends BaseActivity{
    private Handler handler;
    private UserDatabaseManager mManager;
    private String title;
    private String content;
    private String img_url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Log.e("Holden","enter the content activity");

        //获取用户之前点击的新闻题目
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        TextView titlev=(TextView) findViewById(R.id.news_title);
        titlev.setText(title);
        TextView contv=(TextView) findViewById(R.id.news_content);
        getNewsContent();
        getNewsPicUrls();
        contv.setText(content);

        //显示新闻图片
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                ImageView imgView = (ImageView) findViewById(R.id.cttView);
                imgView.setImageBitmap((Bitmap) msg.obj);
            };
        };

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(img_url != "") {
                    String urlpath = img_url;
                    Bitmap bm = PostUtils.getInternetPicture(urlpath);
                    Message msg = new Message();
                    // 把bm存入消息中,发送到主线程
                    msg.obj = bm;
                    handler.sendMessage(msg);
                }
            }
        }).start();


    }

    public void getNewsContent(){
        Thread workThread1=new Thread(){
            @Override
            public void run(){

                PostUtils postu = new PostUtils();
                content = postu.get("http://119.29.246.19:7777/getNewsContent/?title="+title);

            }
        };
        workThread1.start();
        //主线程等待workThread1执行完毕
        try {
            workThread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void getNewsPicUrls(){
        Thread workThread1=new Thread(){
            @Override
            public void run(){
                PostUtils postu = new PostUtils();
                img_url = postu.get("http://119.29.246.19:7777/getNewsPic/?title=" + title);
            }
        };
        workThread1.start();
        //主线程等待workThread1执行完毕
        try {
            workThread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}