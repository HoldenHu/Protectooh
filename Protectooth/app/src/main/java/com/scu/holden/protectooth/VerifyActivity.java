package com.scu.holden.protectooth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.scu.holden.protectooth.component.PaperButton;

/**
 * Created by mirrorssssssss on 11/14/17.
 */

public class VerifyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_page);
        PaperButton bt_verify=(PaperButton)findViewById(R.id.bt_verify);

        bt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("已将信息传给服务器，等待1～3工作日验证");
            }
        });


    }
}
