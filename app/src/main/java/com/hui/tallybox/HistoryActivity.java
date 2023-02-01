package com.hui.tallybox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hui.tallybox.adapter.AccountAdapter;
import com.hui.tallybox.db.AccountBean;
import com.hui.tallybox.db.DBManager;
import com.hui.tallybox.utils.CalendarDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    TextView time;
    ListView lv;
    List<AccountBean>mData;
    AccountAdapter adapter;
    int year,month;
    int dialogSelPos = -1;
    int dialogSelMonth = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        time=findViewById(R.id.history_tv_time);
        lv=findViewById(R.id.history_lv);
        mData=new ArrayList<>();
        //设置适配器
        adapter=new AccountAdapter(this,mData);
        lv.setAdapter(adapter);
        intiTtime();
        time.setText(year+"/"+month);
        loadData(year,month);
        setLVLongClickListener();
    }
    /*设置ListView的长按事件*/
    private void setLVLongClickListener() {
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AccountBean clickBean= mData.get(position); //获取当前被点击的数据
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
                Toast.makeText(HistoryActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged(); //提示适配器更新数据
            }
        });
        builder.create().show();
    }
    /*获取指定年月的数据*/
    private void loadData(int year,int month) {
        List<AccountBean> list = DBManager.getMonthAccountFromAccounttb(year, month);
        mData.clear();
        mData.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void intiTtime() {
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1; //以年月为单位，无需初始化日
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.history_iv_back:
                finish();
                break;
            case R.id.history_iv_calendar:
                CalendarDialog dialog = new CalendarDialog(this,dialogSelPos,dialogSelMonth);
                dialog.show();
                dialog.setDialogSize();
                dialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
                    @Override
                    public void onRefresh(int selPos, int year, int month) {
                        time.setText(year+"/"+month);
                        loadData(year,month);
                        dialogSelPos = selPos;
                        dialogSelMonth = month;
                    }
                });
                break;
        }
    }
}