package com.hui.tallybox.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hui.tallybox.R;
/*
 * 数据库操作
 * */
public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context,"tally.db" , null, 1);
    }

    //创建数据库的方法，只有项目第一次运行时，会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表示类型的表
        String sql = "create table typetb(id integer primary key autoincrement,typename varchar(10),imageId integer,sImageId integer,kind integer)";
        db.execSQL(sql);
        insertType(db);
        //创建记账表
        sql="create table accounttb(id integer primary key autoincrement,typename vachar(10),sImageid integer,remark vachar(90),time vachar(60),money integer,year integer,month integer,day integer,kind integer)";
        db.execSQL(sql);
    }

    private void insertType(SQLiteDatabase db) {
        /*向typetb表当中插入元素*/
        String sq = "insert into typetb (typename,imageId,sImageId,kind) values (?,?,?,?)";
        db.execSQL(sq,new Object[]{"其他", R.mipmap.other1,R.mipmap.more,0});
        db.execSQL(sq,new Object[]{"餐饮", R.mipmap.catering1,R.mipmap.catering,0});
        db.execSQL(sq,new Object[]{"公交", R.mipmap.bus1,R.mipmap.bus,0});
        db.execSQL(sq,new Object[]{"服饰", R.mipmap.clothes1,R.mipmap.clothes,0});
        db.execSQL(sq,new Object[]{"购物", R.mipmap.shopping1,R.mipmap.shopping,0});
        db.execSQL(sq,new Object[]{"零食", R.mipmap.snack1,R.mipmap.snack,0});
        db.execSQL(sq,new Object[]{"教育", R.mipmap.study1,R.mipmap.study,0});
        db.execSQL(sq,new Object[]{"娱乐", R.mipmap.amusement1,R.mipmap.amusement,0});
        db.execSQL(sq,new Object[]{"生活缴费", R.mipmap.life1,R.mipmap.life,0});
        db.execSQL(sq,new Object[]{"旅行", R.mipmap.travel1,R.mipmap.travel,0});
        db.execSQL(sq,new Object[]{"车票", R.mipmap.ticket1,R.mipmap.ticket,0});
        db.execSQL(sq,new Object[]{"医疗", R.mipmap.medica1,R.mipmap.medica,0});
        db.execSQL(sq,new Object[]{"保险", R.mipmap.insurance1,R.mipmap.insurance,0});
        db.execSQL(sq,new Object[]{"日常用品", R.mipmap.daily1,R.mipmap.daily,0});
        db.execSQL(sq,new Object[]{"话费", R.mipmap.phone1,R.mipmap.phone,0});


        db.execSQL(sq,new Object[]{"其他", R.mipmap.others1,R.mipmap.others,1});
        db.execSQL(sq,new Object[]{"工资", R.mipmap.wages1,R.mipmap.wages,1});
        db.execSQL(sq,new Object[]{"奖金", R.mipmap.bonus1,R.mipmap.bonus,1});
        db.execSQL(sq,new Object[]{"红包", R.mipmap.redenvelops1,R.mipmap.redenvelops,1});
        db.execSQL(sq,new Object[]{"收转账", R.mipmap.in1,R.mipmap.in,1});
        db.execSQL(sq,new Object[]{"退款", R.mipmap.refund1,R.mipmap.refund,1});
        db.execSQL(sq,new Object[]{"理财", R.mipmap.financial1,R.mipmap.financial,1});

    }

    //数据库版本更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
