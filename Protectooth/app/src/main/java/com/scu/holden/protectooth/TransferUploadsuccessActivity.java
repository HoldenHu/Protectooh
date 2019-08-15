package com.scu.holden.protectooth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scu.holden.protectooth.component.PaperButton;

import org.xutils.view.annotation.ViewInject;

import static com.scu.holden.protectooth.R.id.textView;

/**
 * Created by mirrorssssssss on 11/1/17.
 */

public class TransferUploadsuccessActivity extends BaseActivity{
    /*@ViewInject(R.id.bt_look_result)
    PaperButton bt_login;*/

//    @ViewInject(R.id.btn_confirm)
//    TextView text_confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadsuccess);
        PaperButton bt_login=(PaperButton)findViewById(R.id.bt_look_result);
        TextView text_confirm=(TextView)findViewById(R.id.btn_confirm);;

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String packageName = "com.scu.holden.protectooth";
                String className = "com.scu.holden.protectooth.PhotoResultActivity";
                intent.setClassName(packageName, className);
                startActivity(intent);
            }
        });

        text_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String packageName = "com.scu.holden.protectooth";
                String className = "com.scu.holden.protectooth.HomeActivity";
                intent.setClassName(packageName, className);
                startActivity(intent);

            }
        });

    }
}
