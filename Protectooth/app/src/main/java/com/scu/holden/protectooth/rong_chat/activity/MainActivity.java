package com.scu.holden.protectooth.rong_chat.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.rong_chat.App;
import com.scu.holden.protectooth.rong_chat.DemoContext;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mBut2, mBut3, mBut4, mBut5, mBut6;
    private ImageView mBackImg;
    private TextView mTitle;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rong_main);

        String token = "ke0BCyqQnwWiagWoS1ckzrI6ZiT8q7s0UEaMPWY0lMwZFCr6nvpDtX2/KvQEWEDo6r3YdoOyCDqUgN53uY4SEA==";
        connect(token);
        edit = DemoContext.getInstance().getSharedPreferences().edit();
        edit.putString("DEMO_TOKEN", token);
        edit.apply();
        mBut2 = (Button) findViewById(R.id.bt2);
        mBut3 = (Button) findViewById(R.id.bt3);
        mBut4 = (Button) findViewById(R.id.bt4);
        mBut5 = (Button) findViewById(R.id.bt5);
        mBut6 = (Button) findViewById(R.id.bt6);
        mBackImg = (ImageView) findViewById(R.id.img1);
        mTitle = (TextView) findViewById(R.id.txt1);

        mBackImg.setVisibility(View.GONE);
        mTitle.setText("主页面");
        mBut6.setVisibility(View.GONE);

        mBut2.setOnClickListener(this);
        mBut3.setOnClickListener(this);
        mBut4.setOnClickListener(this);
        mBut5.setOnClickListener(this);
        mBut6.setOnClickListener(this);
    }

    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

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
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt2:
                if (RongIM.getInstance() != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                genToken.GetRongCloudToken("111");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
//                    RongIM.getInstance().startPrivateChat(MainActivity.this, "26594", "title");

                break;
            case R.id.bt3:
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(MainActivity.this);
                break;
            case R.id.bt4:
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startSubConversationList(MainActivity.this, Conversation.ConversationType.GROUP);
                break;
            case R.id.bt5:
                startActivity(new Intent(MainActivity.this, ContactsActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {


            final AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
            alterDialog.setMessage("确定退出应用？");
            alterDialog.setCancelable(true);

            alterDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().disconnect(true);

                    Process.killProcess(Process.myPid());
                }
            });
            alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alterDialog.show();
        }

        return false;
    }
}
