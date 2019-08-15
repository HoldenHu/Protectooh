package com.scu.holden.protectooth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.scu.holden.protectooth.HomeActivity;
import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.component.EditTextWithDel;
import com.scu.holden.protectooth.component.PaperButton;
import com.scu.holden.protectooth.localdatbase.User;
import com.scu.holden.protectooth.localdatbase.UserDatabaseManager;
import com.scu.holden.protectooth.utlis.CheckUtils;
import com.scu.holden.protectooth.utlis.PostUtils;
import com.scu.holden.protectooth.utlis.Tools;

import net.sf.json.JSONObject;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;



@ContentView(R.layout.fragment_regist)
public class FragmentRegist extends BaseFragment  {
@ViewInject(R.id.next)
PaperButton nextBt;
    @ViewInject(R.id.userpassword)
    EditTextWithDel userpassword;
    @ViewInject(R.id.username)
    EditTextWithDel username;
    @ViewInject(R.id.send_smscode)
    PaperButton sendsmscode;
    @ViewInject(R.id.userphone)
    EditTextWithDel userphone;
    @ViewInject(R.id.smscode)
    EditTextWithDel smscode;
    @ViewInject(R.id.fg_regist)
    LinearLayout fg_regist;
    @ViewInject(R.id.rela_rephone)
    RelativeLayout rela_rephone;
    @ViewInject(R.id.rela_recode)
    RelativeLayout rela_recode;
    @ViewInject(R.id.rela_repass)
    RelativeLayout rela_repass;
    @ViewInject(R.id.rela_username)
    RelativeLayout rela_name;
    @ViewInject(R.id.usericon)
    ImageView phoneIv;
    @ViewInject(R.id.keyicon)
    ImageView keyIv;
    @ViewInject(R.id.codeicon)
    ImageView passIv;
    @ViewInject(R.id.personicon)
    ImageView nameIv;
    MyCountTimer timer;

    PostUtils postu=new PostUtils();
    int return_code;
    JSONObject return_Json;
    String local_phone,local_name,local_password,return_str;
    String token;
    private UserDatabaseManager mManager;
    private int ServerisAvaliable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        TextListener();
    }

    private void TextListener() {
        //手机改变背景变
        userphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = userphone.getText().toString();
                if(CheckUtils.isMobile(text)){
                    //抖动
                    rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));

                }

            }
        });
        //验证码改变背景变
        smscode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    rela_recode.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));


            }
        });
        //密码改变背景变
        userpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                rela_repass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));
            }
        });
        //用户名改变背景变
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rela_repass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));
            }
        });


    }

    private void initView() {
        //发送验证码点击事件
        sendsmscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view =v;
                final String phone = userphone.getText().toString();
//                boolean mobile = CheckUtils.isMobile(phone);
                boolean mobile=true;
                if (!TextUtils.isEmpty(phone)){
                if (mobile) {
                    timer = new MyCountTimer(60000, 1000);
                    timer.start();

                    Log.e("Holden","prepare to send message");
                    ServerisAvaliable=1;
                    Thread thread1=new Thread() {
                        @Override
                        public void run(){
                            try{
                            return_str=postu.get("http://119.29.246.19:7777/signup/auth?phone="+phone);
                            Log.e("Holden","Get:http://119.29.246.19:7777/signup/auth?phone="+phone);
                            Log.e("Holden","return_json: "+return_str);
                            return_Json=JSONObject.fromObject(return_str);
                            return_code=return_Json.getJSONObject("error").getInt("code");}
                            catch (Exception e){
                                Log.e("Holden","Can not connect to server");
                                ServerisAvaliable=0;
                            }
                        }
                    };
                    thread1.start();
                    try {
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(ServerisAvaliable==0){
                        showSnackar(view,"Pt提示：服务器正在整修，无法连接");
                    }else {
                        if (return_code == 0)
                            showSnackar(view, "Pt提示：短信发送成功");
                        else
                            showSnackar(view, "Pt提示：短信发送失败");
                    }

                }

                else
                {
                    rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    phoneIv.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(view,"Pt提示：输入手机号码");
                }


                }else {
                    rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    phoneIv.setAnimation(Tools.shakeAnimation(2));
                  showSnackar(view,"Pt提示：手机号码不正确");
                }

            }
        });
        //下一步的点击事件
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                final String password = userpassword.getText().toString();
                final String user_name = username.getText().toString();
                final String code = smscode.getText().toString();
                final String phone = userphone.getText().toString();

                if (TextUtils.isEmpty(phone)){
                   // fg_regist.setBackgroundResource(R.color.colorAccent);
                    rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    phoneIv.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(view,"Pt提示：请输入手机号码");

                    return;
                }
//                if(!CheckUtils.isMobile(phone)){
//                    rela_rephone.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
//                    phoneIv.setAnimation(Tools.shakeAnimation(2));
//                    showSnackar(view,"Pt提示：手机号不正确");
//                    // fg_regist.setBackgroundResource(R.color.colorAccent);
//                    return;
//                }
                if (TextUtils.isEmpty(code)){
                    rela_recode.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    keyIv.setAnimation(Tools.shakeAnimation(2));
                   // fg_regist.setBackgroundResource(R.color.colorAccent);
                    showSnackar(view,"Pt提示：请输入验证码");
                    return;

                }
                if (TextUtils.isEmpty(password)){
                    rela_repass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    passIv.setAnimation(Tools.shakeAnimation(2));
                   // fg_regist.setBackgroundResource(R.color.colorAccent);
                    showSnackar(view,"Pt提示：请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(user_name)){
                    rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    nameIv.setAnimation(Tools.shakeAnimation(2));
                    // fg_regist.setBackgroundResource(R.color.colorAccent);
                    showSnackar(view,"Pt提示：请输入用户名");
                    return;
                }

                Log.e("Holden","prepare to confirm phone");
                ServerisAvaliable=1;
                Thread thread1=new Thread() {
                    @Override
                    public void run(){
                        try {
                            return_str = postu.get("http://119.29.246.19:7777/signup/auth?phone=" + phone + "&code=" + code);
                            Log.e("Holden", "Get:http://119.29.246.19:7777/signup/auth?phone=" + phone + "&code=" + code);
                            Log.e("Holden", "return_json: " + return_str);
                            return_Json = JSONObject.fromObject(return_str);
                            return_code = return_Json.getJSONObject("error").getInt("code");
                            token = return_Json.getJSONObject("token").getString("token");
                        }catch (Exception e){
                            Log.e("Holden","Can not connect to server");
                            ServerisAvaliable=0;
                        }
                    }
                };
                thread1.start();
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(ServerisAvaliable==0){
                    showSnackar(view,"Pt提示：服务器正在整修,无法连接");
                }else {
                    if (return_code == 0)
                        showSnackar(view, "Pt提示：手机验证成功");
                    else {
                        rela_recode.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                        keyIv.setAnimation(Tools.shakeAnimation(2));
                        showSnackar(view, "Pt提示：手机验证失败");
                        return;
                    }
                }


                Log.e("Holden","prepare to regist");
                ServerisAvaliable=1;
                Thread thread2=new Thread() {
                    @Override
                    public void run(){
                        JSONObject obj=new JSONObject();
                        JSONObject userobj=new JSONObject();
                        userobj.put("name",user_name);
                        userobj.put("phone",phone);
                        userobj.put("psw",password);
                        userobj.put("gender","male");
                        userobj.put("avatar","http://t1.mmonly.cc/uploads/tu/201601/100/52vhanjnvgx.png");
                        obj.put("user",userobj);
                        obj.put("token",token);
                        Log.e("Holden",obj.toString());
                        try {
                            return_str = postu.post(obj, "http://119.29.246.19:7777/signup");
                            Log.e("Holden", "Post:http://119.29.246.19:7777/signup");
                            Log.e("Holden", "return_json: " + return_str);
                            return_Json = JSONObject.fromObject(return_str);
                            return_code = return_Json.getJSONObject("error").getInt("code");
                        }catch (Exception e){
                            Log.e("Holden","Can not connect to server");
                            ServerisAvaliable=0;
                        }
                    }
                };
                thread2.start();
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(ServerisAvaliable==0){
                    showSnackar(view,"Pt提示：服务器正在整修，无法连接");
                }else {
                    if (return_code == 0) {
                        showSnackar(view, "Pt提示：注册成功");
                        final User user = new User();
                        user.setPassword(password);
                        user.setPhone(phone);
                        user.setName(phone);
                        UserDatabaseManager.Remember(user);
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.fade,
                                R.anim.my_alpha_action);

                    } else {
                        showSnackar(view, "Pt提示：注册失败,请确认网络连接正常");
                        return;
                    }
                }





            }
        });


    }
//事件定时器
    class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            sendsmscode.setText((millisUntilFinished / 1000) +"秒后重发");
            sendsmscode.setClickable(false);
        }
        @Override
        public void onFinish() {
            sendsmscode.setText("重新发送");
            sendsmscode.setClickable(true);
        }
    }
//回收timer
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }
    }
}
	
