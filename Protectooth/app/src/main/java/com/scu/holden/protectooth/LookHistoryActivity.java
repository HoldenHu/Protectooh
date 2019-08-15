package com.scu.holden.protectooth;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.scu.holden.protectooth.fragment.BaseFragment;
import com.scu.holden.protectooth.localdatbase.User;
import com.scu.holden.protectooth.localdatbase.UserDatabaseManager;
import com.scu.holden.protectooth.utlis.download;
import com.scu.holden.protectooth.utlis.getReturnList;

import net.sf.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by mirrorssssssss on 11/10/17.
 */

public class LookHistoryActivity extends BaseActivity{
    private String tooth_pic_url;
    private String doctor_head_path;
    private ImageView image_tooth;
    private ImageView image_doctor_head;
    private UserDatabaseManager mManager;
    private User user;
    private String show_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_information);
        image_tooth=(ImageView)findViewById(R.id.tooth_photo);

        mManager = UserDatabaseManager.newInstance(this.getApplicationContext());
        user = UserDatabaseManager.getRecent();
        setRecentPhoto();

    }

    private void setRecentPhoto(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO
                // 在这里进行 http request.网络请求相关操作
//            "http://119.29.246.19:7777/upload_img/"
                String s = getReturnList.getResult("http://119.29.246.19:7777/getRctPic/?phone="+user.getPhone());
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", s);
                msg.setData(data);
                handler2.sendMessage(msg);
            }
        }).start();
    }


    Handler handler2 = new Handler() {

        @Override

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.e("Holden", "请求结果为-->" + val);

            JSONObject return_id_json=JSONObject.fromObject(val);
            show_data=(String)return_id_json.get("data");

            tooth_pic_url="http://119.29.246.19:7777"+(String)return_id_json.get("url");

            Log.e("Holden","tooth_rul:"+tooth_pic_url);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    String urlpath = tooth_pic_url;
                    Bitmap bm = download.getInternetPicture(urlpath);
                    Message msg = new Message();
                    // 把bm存入消息中,发送到主线程
                    msg.obj = bm;
                    handler3.sendMessage(msg);
                }
            }).start();

        }

    };


    Handler handler3 = new Handler() {

        public void handleMessage(android.os.Message msg) {
            image_tooth.setImageBitmap((Bitmap) msg.obj);
        };
    };
}
