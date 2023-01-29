package com.hui.tallybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hui.tallybox.adapter.AccountAdapter;
import com.hui.tallybox.db.AccountBean;
import com.hui.tallybox.db.DBManager;
import com.hui.tallybox.utils.BudgeDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //展示今日收支情况的LV相关控件
    ListView todayLV;
    ImageView searchIv;
    Button editBtn;
    ImageButton moreBtn;
    //声明数据源
    List<AccountBean>mData;
    AccountAdapter adapter;
    int year,month,day;
    //头布局相关控件
    View headView;
    TextView topOutTv,topInTv,topemoneyTv,topAllTv;
    ImageView budgetIv;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTime();
        sharedPreferences=getSharedPreferences("budget", Context.MODE_PRIVATE);
        //添加ListView的头布局
        addLVHead();
        todayLV=findViewById(R.id.main_lv);
        mData=new ArrayList<>();
        //设置适配器，加载每一行数据到列表中
        adapter= new AccountAdapter(this,mData);
        todayLV.setAdapter(adapter);
    }
    //添加头布局的方法
    private void addLVHead() {
        headView= getLayoutInflater().inflate(R.layout.item_mainlv_top,null);
        todayLV.addHeaderView(headView);
        topOutTv= headView.findViewById(R.id.item_mainlv_iv_top_out);
        topInTv= headView.findViewById(R.id.item_mainlv_iv_top_in);
        budgetIv= headView.findViewById(R.id.item_mainlv_iv_top_budget);
        topemoneyTv= headView.findViewById(R.id.item_mainlv_iv_top_emoney);
        topAllTv= headView.findViewById(R.id.item_mainlv_iv_top_all);
        budgetIv.setOnClickListener(this);
        headView.setOnClickListener(this);


    }

    private void initView() {
        todayLV = findViewById(R.id.main_lv);
        editBtn = findViewById(R.id.main_btn_edit);
        moreBtn = findViewById(R.id.main_bt_more);
        searchIv = findViewById(R.id.mian_iv_box);
        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIv.setOnClickListener(this);
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
        setTopTvShow();


    }
    /*设置头布局中文本的显示*/
    private void setTopTvShow() {
        float incomeday = DBManager.getSumDayAccount(year,month,day,1);
        float outcomeday = DBManager.getSumDayAccount(year,month,day,0);
        String day="今日支出 "+outcomeday+" 今日收入 "+incomeday;
        topAllTv.setText(day);
        float incomeMonth = DBManager.getSumMonthAccount(year, month,1);
        float outcomeMonth = DBManager.getSumMonthAccount(year, month,0);
        topInTv.setText("￥"+incomeMonth);
        topOutTv.setText("￥"+outcomeMonth);


    }

    private void loadDBData() {
        List<AccountBean> list = DBManager.getDayAccountFromAccounttb(year, month, day);
        mData.clear();
        mData.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mian_iv_box:
                Intent it1=new Intent(this,HistoryActivity.class);
                startActivity(it1);
                break;
            case R.id.main_btn_edit:
                Intent it2=new Intent(this,RecordActivity.class);
                startActivity(it2);
                break;
            case R.id.main_bt_more:

                break;
            case R.id.item_mainlv_iv_top_budget:
                showBudgeDialog();
                break;

        }
        //如果被点击的是头布局
        if(view==headView){

        }
    }
    /*显示预算设置对话框*/
    private void showBudgeDialog() {
        BudgeDialog dialog=new BudgeDialog(this);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new BudgeDialog.OnEnsureListener() {

            @Override
            public void onEnsure(float money) {
                //将预算数据写入共享数据中进行存储
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putFloat("bmoney",money);
                editor.commit();
                //计算剩余金额
                float outcomeMonth=DBManager.getSumMonthAccount(year,month,0);
                float emoney=money-outcomeMonth;//不计入收入金额
                topemoneyTv.setText("￥"+emoney);
            }
        });



    }
}