package com.hui.tallybox;

import android.app.Application;

import com.hui.tallybox.db.DBManager;
/*
* 表示全局应用的类
**/
public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());// 初始化数据库
    }
}
