package com.hui.tallybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hui.tallybox.adapter.AccountAdapter;
import com.hui.tallybox.db.AccountBean;
import com.hui.tallybox.db.DBManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView todayLV; //展示今日收支情况的LV
    List<AccountBean>mData; //声明数据源
    AccountAdapter adapter;
    int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        todayLV=findViewById(R.id.main_lv);
        mData=new ArrayList<>();
        //设置适配器，加载每一行数据到列表中
        adapter= new AccountAdapter(this,mData);
        todayLV.setAdapter(adapter);
    }

    private void initTime() {
        Calendar calendar=Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    //当activity获取焦点时会调用的方法
    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();


    }

    private void loadDBData() {
        List<AccountBean> list = DBManager.getDayAccountFromAccounttb(year, month, day);
        mData.clear();
        mData.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mian_iv_serach:

                break;
            case R.id.main_btn_edit:
                Intent it=new Intent(this,RecordActivity.class);
                startActivity(it);
                break;
            case R.id.main_bt_more:

                break;
        }
    }
}