package com.hui.tallybox.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

/*
 * 负责管理数据库的类
 *   主要对于表当中的内容进行操作，增删改查
 * */
public class DBManager {

    private static SQLiteDatabase db;

    /* 初始化数据库对象*/
    public static void initDB(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);  //得到帮助类对象
        db = helper.getWritableDatabase();      //得到数据库对象
    }

    /**
     * 读取数据库当中的数据，写入内存集合里
     * kind :表示收入或者支出
     */
    public static List<TypeBean> getTypeList(int kind) {
        List<TypeBean> list = new ArrayList<>();
        //读取typetb表当中的数据
        String sql = "select * from typetb where kind = " + kind;
        Cursor cursor = db.rawQuery(sql, null);
//        循环读取图标内容，存储到对象当中
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind1);
            list.add(typeBean);
        }
        return list;
    }
    /*
      向记账表中插入一条数据
     */
    public static void insertItemToAccounttb(AccountBean bean){
        ContentValues values = new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("sImageid",bean.getsImageid());
        values.put("remark",bean.getRemark());
        values.put("time",bean.getTime());
        values.put("money",bean.getMoney());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("kind",bean.getKind());
        db.insert("accounttb",null,values);
    }
    /*
    获取某一天的支出或收入情况
     */
    @SuppressLint("Range")
    public static List<AccountBean>getDayAccountFromAccounttb(int year, int month, int day){
        List<AccountBean>list=new ArrayList<>();
        String sql="select * from accounttb where year=? and month=? and day=? order by id desc";
        Cursor cursor=db.rawQuery(sql,new String[]{year+" ",month+" ",day+" "});
        /*遍历符合要求的每一行数据*/
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int sImageid = cursor.getInt(cursor.getColumnIndex("sImageid"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean=new AccountBean(id,sImageid,typename,remark,time,money,year,month,day,kind);
            list.add(accountBean);

        }
        return list;
    }
}