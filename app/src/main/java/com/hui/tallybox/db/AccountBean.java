package com.hui.tallybox.db;
/*
* 描述记录今日收支中一条数据的相关内容
**/
public class AccountBean {
    int id;
    int sImageid; //被选中的图片
    String typename;   //保存类型名称
    String remark;   //保存备注
    String time;  //保存时间字符串
    float money;
    int year;
    int month;
    int day;
    int kind;  //类型 支出为0，收入为1

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getsImageid() {
        return sImageid;
    }

    public void setsImageid(int sImageid) {
        this.sImageid = sImageid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public AccountBean() {
    }

    public AccountBean(int id, int sImageid, String typename, String remark, String time, float money, int year, int month, int day, int kind) {
        this.id = id;
        this.sImageid = sImageid;
        this.typename = typename;
        this.remark = remark;
        this.time = time;
        this.money = money;
        this.year = year;
        this.month = month;
        this.day = day;
        this.kind = kind;
    }
}
