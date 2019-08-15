package com.scu.holden.protectooth.doctor.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.fragment.BaseFragment;
import com.scu.holden.protectooth.localdatbase.User;
import com.scu.holden.protectooth.localdatbase.UserDatabaseManager;
import com.scu.holden.protectooth.utlis.download;
import com.scu.holden.protectooth.utlis.getReturnList;

import net.sf.json.JSONObject;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.math.BigDecimal;

import io.rong.imkit.RongIM;

/**
 * Created by mirrorssssssss on 11/11/17.
 */
@ContentView(R.layout.fragment_comment)
public class FragmentComment extends BaseFragment {
    @ViewInject(R.id.tooth_photo)
    private ImageView image_tooth;
    private UserDatabaseManager mManager;
    private User user;
    private String show_data;
    private double show_index_number;
    private String tooth_pic_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Holden","doctorhome->onCreateView");
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int i = Log.i("Holden", "doctorhome->onViewCreated");
        mManager = UserDatabaseManager.newInstance(getActivity());
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
                String s = getReturnList.getResult("http://119.29.246.19:7777/getRctPic/?phone=17761251862");
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
            show_index_number=(double)return_id_json.get("result");

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
