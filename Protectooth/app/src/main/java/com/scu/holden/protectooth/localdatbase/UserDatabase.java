package com.scu.holden.protectooth.localdatbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.scu.holden.protectooth.fragment.FragmentLogin;
import com.scu.holden.protectooth.localdatbase.GlobeManager;

/**
 * Created by Holden on 4/10/2017.
 */

public class UserDatabase extends SQLiteOpenHelper{

    public UserDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(GlobeManager.UserInfo.CREATE_TABLE);
        sqLiteDatabase.execSQL(GlobeManager.RecentUsers.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
