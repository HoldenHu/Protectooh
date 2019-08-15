package com.scu.holden.protectooth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.scu.holden.protectooth.localdatbase.UserDatabaseManager;

public class MainActivity extends BaseActivity {

    private UserDatabaseManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
