package com.scu.holden.protectooth;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.scu.holden.protectooth.localdatbase.UserDatabaseManager;
import com.scu.holden.protectooth.utlis.Uploadfile;
import com.scu.holden.protectooth.localdatbase.User;

/**
 * Created by mirrorssssssss on 10/30/17.
 */

public class PhotoUploadActivity extends BaseActivity{
    private String pic_rootpath="/storage/emulated/0/MOVIEW/Photos/";
    private ImageView img;
    private String filepath="/storage/emulated/0/MOVIEW/Photos/20171026224446.jpg";        //rencent pic

    private UserDatabaseManager mManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);

        mManager = UserDatabaseManager.newInstance(this.getApplicationContext());
        user = UserDatabaseManager.getRecent();

//        final Intent intent = new Intent(Intent.ACTION_VIEW);
//        String packageName = "com.tony.molink.moview";
//        String className = "com.qihoo.util.StartActivity";
//        intent.setClassName(packageName, className);
//        startActivity(intent);



//        List<String> imagePathList = new ArrayList<String>();
        File fileAll = new File(pic_rootpath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
//        for (int i = 0; i < files.length; i++) {          //将所有图片文件都添加到List
//            imagePathList.add(files[i].getPath());
//            Log.e("Holden_photo", files[i].getPath());
//        }
        filepath=files[files.length-1].getPath();       //get the most recent one

        img = (ImageView) findViewById(R.id.display_tooth_image);
        File file = new File(filepath);
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(filepath);
            //将图片显示到ImageView中
            img.setImageBitmap(bm);
        }

        findViewById(R.id.button_send_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(networkTask).start();
                Log.e("Holden_3","prepare to enter the Finished view");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                String packageName = "com.scu.holden.protectooth";
                String className = "com.scu.holden.protectooth.TransferUploadsuccessActivity";
                intent.setClassName(packageName, className);
                startActivity(intent);
            }
        });

        Log.e("Holden", "enter the photoUpload activity");

    }


    Handler handler = new Handler() {

        @Override

        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            Bundle data = msg.getData();

            String val = data.getString("value");

            Log.e("holden_sendphoto", "请求结果为-->" + val);

            // TODO

            // UI界面的更新等相关操作

        }

    };



    Runnable networkTask = new Runnable() {

        @Override

        public void run() {

            // TODO

            // 在这里进行 http request.网络请求相关操作

            Uploadfile.uploadFile("http://119.29.246.19:7777/savePic/?phone="+user.getPhone()+"&discription=yes",filepath);

            Message msg = new Message();

            Bundle data = new Bundle();

            data.putString("value", "成功上传图片");

            msg.setData(data);

            handler.sendMessage(msg);

        }

    };



}
