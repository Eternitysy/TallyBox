package com.hui.tallybox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hui.tallybox.adapter.AccountAdapter;
import com.hui.tallybox.db.AccountBean;
import com.hui.tallybox.db.DBManager;
import com.hui.tallybox.utils.BudgeDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /*展示今日收支情况的LV相关控件*/
    ListView todayLV;
    ImageView searchIv;
    Button editBtn;
    ImageButton moreBtn;
    /*声明数据源*/
    List<AccountBean>mData;
    AccountAdapter adapter;
    int year,month,day;
    /*头布局相关控件*/
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
    /*添加头布局的方法*/
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
        setLVLongClickListener();
    }
    /*设置ListView的长按事件*/
    private void setLVLongClickListener() {
        todayLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int pos=position-1;
                AccountBean clickBean= mData.get(pos); //获取当前被点击的数据
                //弹出是否删除删除的对话框
                showDeleteDialog(clickBean);
                return false;
            }
        });
    }
    private void showDeleteDialog(AccountBean clickBean) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("删除后无法恢复,是否删除?").setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int click_id=clickBean.getId();  //获取id
                DBManager.deleteFromAccounttbByid(click_id); //从数据库中删除数据
                mData.remove(clickBean); //从ListView中移除数据
                Toast.makeText(MainActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged(); //提示适配器更新数据
                setTopTvShow(); //更新头部数据
            }
        });
        builder.create().show();
    }

    private void initTime() {
        Calendar calendar=Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /*当activity获取焦点时会调用的方法*/
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
        /*设置显示预算剩余*/
        float bmoney=sharedPreferences.getFloat("bmoney",0);
        float emoney=bmoney-outcomeMonth;
        topemoneyTv.setText("￥"+emoney);
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
                startActivity(it1); //点击账本，跳转到历史账单
                break;
            case R.id.main_btn_edit:
                Intent it2=new Intent(this,RecordActivity.class);
                startActivity(it2); //点击记一笔，跳转到记录界面
                break;
            case R.id.main_bt_more:
                Intent it3=new Intent(this,SettingActivity.class);
                startActivity(it3); //点击设置，跳转到设置界面
                break;
            case R.id.item_mainlv_iv_top_budget:
                showBudgeDialog(); //点击eye,弹出设置预算的对话框
                break;
        }
        /*如果被点击的是头布局*/
        if(view==headView){
            Intent intent = new Intent();
            intent.setClass(this, MonthChartActivity.class);
            startActivity(intent); //跳转到数据分析界面
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
                float emoney=money-outcomeMonth;//剩余预算不计入收入金额
                topemoneyTv.setText("￥"+emoney);
            }
        });
    }
}