package com.scu.holden.protectooth.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scu.holden.protectooth.HomeActivity;
import com.scu.holden.protectooth.LoginActivity;
import com.scu.holden.protectooth.MainActivity;
import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.component.EditTextWithDel;
import com.scu.holden.protectooth.component.PaperButton;
import com.scu.holden.protectooth.localdatbase.User;
import com.scu.holden.protectooth.localdatbase.UserDatabaseManager;
import com.scu.holden.protectooth.rong_chat.App;
import com.scu.holden.protectooth.rong_chat.DemoContext;
import com.scu.holden.protectooth.utlis.CheckUtils;
import com.scu.holden.protectooth.utlis.PostUtils;
import com.scu.holden.protectooth.utlis.Tools;

import net.sf.json.JSONObject;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

@ContentView(R.layout.fragment_login)
public class FragmentLogin extends BaseFragment  {
    @ViewInject(R.id.userph)
    EditTextWithDel userphone;
    @ViewInject(R.id.userpass)
    EditTextWithDel userpass;
    @ViewInject(R.id.bt_login)
    PaperButton bt_login;
    @ViewInject(R.id.login_progress)
    ProgressBar login_progress;
    @ViewInject(R.id.tv_forgetcode)
    TextView tv_forgetcode;
    @ViewInject(R.id.loginusericon)
    ImageView loginusericon;
    @ViewInject(R.id.codeicon)
    ImageView codeicon;
    @ViewInject(R.id.rela_name)
    RelativeLayout rela_name;
    @ViewInject(R.id.rela_pass)
    RelativeLayout rela_pass;
    private Handler handler = new Handler(){};
    private int ServerisAvaliable;
    SharedPreferences.Editor edit;


    PostUtils postu=new PostUtils();
    String local_phone,local_password;
    int return_code;
    JSONObject return_Json;

    private UserDatabaseManager mManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mManager = UserDatabaseManager.newInstance(this.getContext());
        User user = UserDatabaseManager.getRecent();
        if (null != user){
            userphone.setText(user.getName());
            userpass.setText(user.getPassword());
        }

        initLogin();
        textListener();

    }

    private void textListener() {
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
                    rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));

                }

            }
        });
        userpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                rela_pass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    //rela_pass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));

            }
        });
    }

    private void initLogin() {
//        SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo", 0);
//        userphone.setText(userinfo.getString("username",null));
//        userpass.setText(userinfo.getString("password",null));
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = userphone.getText().toString();
                final String passwords = userpass.getText().toString();
                final View view= v;

                if (TextUtils.isEmpty(phone)){
                    rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    loginusericon.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(v,"Pt提示：请输入手机号码");
                    return;
                }
//                if(!CheckUtils.isMobile(phone)){
//                    //抖动
//                    rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
//                    loginusericon.setAnimation(Tools.shakeAnimation(2));
//                    showSnackar(v,"Pt提示：用户名不正确");
//                    return;
//                }
                if (TextUtils.isEmpty(passwords)){
                    rela_pass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                    codeicon.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(v,"Pt提示：请输入密码");

                    return;
                }
                login_progress.setVisibility(View.VISIBLE);
                boolean temp_judge=judge(phone,passwords);
                if(ServerisAvaliable==0){
                    login_progress.setVisibility(View.GONE);
                    codeicon.setAnimation(Tools.shakeAnimation(2));
                    showSnackar(view,"Pt提示：服务器正在整修，无法连接");
                }
                else {
                    if (temp_judge) {
                        rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));
                        rela_name.setBackground(getResources().getDrawable(R.drawable.bg_border_color_black));
                        showSnackar(view, "Pt提示：登陆成功");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                login_progress.setVisibility(View.GONE);
                                User user = new User(phone, passwords);
                                UserDatabaseManager.Remember(user);
                                int is_doctor=(int)return_Json.getJSONObject("user").get("is_admin");
                                Log.e("Holden_is_doctor",Integer.toString(is_doctor));
                                if(is_doctor==0) {
                                    Intent intent = new Intent(getActivity(), com.scu.holden.protectooth.HomeActivity.class);
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.fade,
                                            R.anim.my_alpha_action);
                                    String token = "kjG60BgYrdvkvRiRyfiU+YTUvRV/fcQmQxRAxoynxDR9/Jw+zUmRtsffQAHre0usij1BDUafMLxnTlRG7WZKXg==";
                                    connect(token);
                                    edit = DemoContext.getInstance().getSharedPreferences().edit();
                                    edit.putString("DEMO_TOKEN", token);
                                    edit.apply();
                                }
                                if(is_doctor==1) {
                                    Intent intent = new Intent(getActivity(), com.scu.holden.protectooth.doctor.Doctor_homeActivity.class);
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.fade,
                                            R.anim.my_alpha_action);
                                    String token = "v5knmQ01S8qbj+/fq9RlVt0tcuICyyhOkNtfcBtxv9+YYIl4ZYlXw2+xNYrYJkmNKyepH/KlDDNT9nEn3RIYrA==";
                                    connect(token);
                                    edit = DemoContext.getInstance().getSharedPreferences().edit();
                                    edit.putString("DEMO_TOKEN", token);
                                    edit.apply();
                                }
                            }
                        }, 1500);
                    } else {
                        login_progress.setVisibility(View.GONE);
                        rela_pass.setBackground(getResources().getDrawable(R.drawable.bg_border_color_cutmaincolor));
                        codeicon.setAnimation(Tools.shakeAnimation(2));
                        showSnackar(view, "Pt提示：登陆失败");
                    }
                }
            }
        });

    }


    private boolean judge(String phone,String psw) {

        local_phone = phone;
        local_password = psw;
        return_code=1;
        ServerisAvaliable=1;
        Thread workThread1=new Thread(){
            @Override
            public void run(){
                JSONObject obj=new JSONObject();
                obj.put("phone",local_phone);
                obj.put("psw",local_password);
                Log.e("Holden","prepare to login");
                try {
                    String return_str = postu.post(obj, "http://119.29.246.19:7777/login");
                    Log.e("Holden", "login return Json:" + return_str);
                    return_Json = JSONObject.fromObject(return_str);
                    return_code = (int) return_Json.getJSONObject("error").get("code");
                    Log.e("Holden", "code: " + String.valueOf(return_code));
                } catch (Exception e){
                    Log.e("Holden","Can not connect to server");
                    ServerisAvaliable=0;
                }

            }
        };


        workThread1.start();

        //主线程等待workThread1执行完毕
        try {
            workThread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(return_code==0){
            return true;
        }
        else{
            return false;
        }
    }


    private void connect(String token) {

        if (getActivity().getApplicationInfo().packageName.equals(App.getCurProcessName(getActivity()))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 *                  http://www.rongcloud.cn/docs/android.html#常见错误码
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }



}
	
