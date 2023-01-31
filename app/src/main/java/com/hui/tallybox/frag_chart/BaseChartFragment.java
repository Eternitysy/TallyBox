package com.hui.tallybox.frag_chart;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import com.hui.tallybox.R;
import com.hui.tallybox.adapter.ChartItemAdapter;
import com.hui.tallybox.db.ChartItemBean;
import com.hui.tallybox.db.DBManager;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


abstract public class BaseChartFragment extends Fragment {
    ListView chartLv;
    int year;
    int month;
    List<ChartItemBean>mDatas;   //数据源
    private ChartItemAdapter itemAdapter;
    TextView chartTv;     //如果没有收支情况，显示的TextView
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_income_chart, container, false);
        chartLv = view.findViewById(R.id.frag_chart_lv);
        /*获取Activity传递的数据*/
        Bundle bundle = getArguments();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        //设置数据源
        mDatas = new ArrayList<>();
        //设置适配器
        itemAdapter = new ChartItemAdapter(getContext(), mDatas);
        chartLv.setAdapter(itemAdapter);
        return view;
    }
    public void setData(int year,int month){
        this.year=year;
        this.month=month;
    }
    public void loadData(int year,int month,int kind) {
        List<ChartItemBean> list = DBManager.getChartListFromAccounttb(year, month, kind);
        mDatas.clear();
        mDatas.addAll(list);
        itemAdapter.notifyDataSetChanged();
    }
}
