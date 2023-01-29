package com.hui.tallybox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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