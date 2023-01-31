package com.hui.tallybox.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.hui.tallybox.utils.FloatUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * 负责管理数据库的类
 * 主要对于表当中的内容进行操作，增删改查
 * */
public class DBManager {

    private static SQLiteDatabase db;

    /* 初始化数据库对象*/
    public static void initDB(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);  //得到帮助类对象
        db = helper.getWritableDatabase();      //得到数据库对象
    }

    /*
     * 读取数据库当中的数据，写入内存集合里
     * kind :表示收入或者支出(收入为1，支出为0)
     */
    public static List<TypeBean> getTypeList(int kind) {
        List<TypeBean> list = new ArrayList<>();
        //读取typetb表当中的数据
        String sql = "select * from typetb where kind = " + kind;
        Cursor cursor = db.rawQuery(sql, null);
        //循环读取游标内容，存储到对象当中
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
    * 向记账表中插入一条数据
    **/
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
    * 获取某一天的支出或收入情况
    **/
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
    /*
    * 获取某月的支出或收入情况
    * */
    @SuppressLint("Range")
    public static List<AccountBean>getMonthAccountFromAccounttb(int year, int month){
        List<AccountBean>list=new ArrayList<>();
        String sql="select * from accounttb where year=? and month=? order by id desc";
        Cursor cursor=db.rawQuery(sql,new String[]{year+" ",month+" "});
        /*遍历符合要求的每一行数据*/
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int sImageid = cursor.getInt(cursor.getColumnIndex("sImageid"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int day=cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean=new AccountBean(id,sImageid,typename,remark,time,money,year,month,day,kind);
            list.add(accountBean);
        }
        return list;
    }
    /*
    * 获取每天的收入支出总金额
    **/
    public static float getSumDayAccount(int year,int month,int day,int kind){
        float total=0.0f;
        String sql="select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor=db.rawQuery(sql,new String[]{year+" ",month+" ",day+" ",kind+" "});
        //遍历
        if(cursor.moveToFirst()){
            @SuppressLint("Range") float money=cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total=money;
        }
        return total;
    }
    /*
     获取每月的收入支出总金额
     */
    public static float getSumMonthAccount(int year,int month,int kind){
        float total=0.0f;
        String sql="select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor=db.rawQuery(sql,new String[]{year+" ",month+" ",kind+" "});
        //遍历
        if(cursor.moveToFirst()){
            @SuppressLint("Range") float money=cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total=money;
        }
        return total;
    }
    /*
     * 获取每年的收入支出总金额
     **/
    public static float getSumYearAccount(int year,int kind){
        float total=0.0f;
        String sql="select sum(money) from accounttb where year=? and kind=?";
        Cursor cursor=db.rawQuery(sql,new String[]{year+" ",kind+" "});
        //遍历
        if(cursor.moveToFirst()){
            @SuppressLint("Range") float money=cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total=money;
        }
        return total;
    }
    /*
     * 统计某月份支出或者收入情况有多少条
     **/
    public static int getCountItemOneMonth(int year,int month,int kind){
        int total = 0;
        String sql = "select count(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex("count(money)"));
            total = count;
        }
        return total;
    }
    /*
     * 查询记账表中记录了的年份
     **/
    public static List<Integer> getYearFromAccounttb() {
        List<Integer>list=new ArrayList<>();
        String sql="select distinct(year) from accounttb order by year asc";
        Cursor cursor=db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") int year=cursor.getInt(cursor.getColumnIndex("year"));
            list.add(year);
        }
        return list;
    }
    /*
     * 通过id从accounttb中删除数据
     **/
    public static int deleteFromAccounttbByid(int id) {
        int i=db.delete("accounttb","id=?",new String[]{id+""});
        return i;
    }
    /*
     * 删除accounttb中所有数据
     **/
    public static void clearAllAccounttb(){
        String sq="delete from accounttb";
        db.execSQL(sq);
    }
    /*
     * 查询指定年份和月份的收入或支出每一种类型的总金额
     **/
    public static List<ChartItemBean>getChartListFromAccounttb(int year,int month,int kind){
        List<ChartItemBean>list = new ArrayList<>();
        float sumMoneyOneMonth = getSumMonthAccount(year, month, kind);  //求出支出或者收入总金额
        String sql = "select typename,sImageid,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename " +
                "order by total desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageid"));
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") float total = cursor.getFloat(cursor.getColumnIndex("total"));
            //计算所占百分比  total /sumMonth
            float ratio = FloatUtils.div(total,sumMoneyOneMonth);
            ChartItemBean bean = new ChartItemBean(sImageId, typename, ratio, total);
            list.add(bean);
        }
        return list;
    }

    /*
     * 获取这个月当中某一天收入支出最大的金额，金额是多少
     **/
    public static float getMaxMoneyOneDayInMonth(int year,int month,int kind){
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=? group by day order by sum(money) desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            return money;
        }
        return 0;
    }
}