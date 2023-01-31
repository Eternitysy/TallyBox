package com.hui.tallybox.frag_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hui.tallybox.R;
import com.hui.tallybox.db.AccountBean;
import com.hui.tallybox.db.TypeBean;
import com.hui.tallybox.utils.KeyBoardUtils;
import com.hui.tallybox.utils.RemarkDialog;
import com.hui.tallybox.utils.SelectTimeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements View.OnClickListener{
    TextView typeTv,remarkTv,timeTv;
    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    GridView typeGv;
    List<TypeBean>typeList;
    TypeBaseAdapter adapter;
    AccountBean accountBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean();
        accountBean.setTypename("其他");
        accountBean.setsImageid(R.mipmap.more);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view);
        setinitime();
        loadDataToGV();
        setGVListener();
        return view;
    }

    private void setinitime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm"); //HH大写时为24小时制
        String time=sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);
        Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    /* 设置GridView每一项的点击事件*/
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos = position;
                adapter.notifyDataSetInvalidated();  //提示绘制发生变化了
                TypeBean typeBean = typeList.get(position);
                String typename=typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);
                int sImageid=typeBean.getSimageId();
                typeIv.setImageResource(sImageid);
                accountBean.setsImageid(sImageid);
            }
        });
    }

    /* 给GridView填充数据的方法*/
    public void loadDataToGV() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
    }

    public void initView(View view){
        typeTv=view.findViewById(R.id.frag_record_tv_type);
        remarkTv=view.findViewById(R.id.frag_record_tv_remark);
        timeTv=view.findViewById(R.id.frag_record_tv_time);
        keyboardView=view.findViewById(R.id.frag_record_keyboard);
        moneyEt=view.findViewById(R.id.frag_record_et_money);
        typeIv=view.findViewById(R.id.frag_record_iv);
        typeGv=view.findViewById(R.id.frag_record_gv);
        remarkTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        /*显示键盘*/
        KeyBoardUtils boardUtils=new KeyBoardUtils(keyboardView,moneyEt);
        boardUtils.showKeyboard();
        /*设置接口，监听确定按钮被点击了*/
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //获取输入钱数
                String moneys=moneyEt.getText().toString();
                if(TextUtils.isEmpty(moneys)||moneys.equals("0")){
                    getActivity().finish();
                    return;
                }
                float money=Float.parseFloat(moneys);
                accountBean.setMoney(money);
                //获取数据存储在数据库中
                saveAccountToDB();
                //返回上一级界面
                getActivity().finish();
            }
        });

    }
    /*在子类中重写此方法*/
    public void saveAccountToDB() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_record_tv_remark:
                showRemarkDialog();
                break;
            case R.id.frag_record_tv_time:
                shoeTimeDialog();
                break;
        }
    }
    /*弹出选择时间对话框*/
    private void shoeTimeDialog() {
        SelectTimeDialog time=new SelectTimeDialog(getContext());
        time.show();
        time.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void OnEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }

    /*弹出备注对话框*/
    public void showRemarkDialog() {
        RemarkDialog dialog = new RemarkDialog(getContext());
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new RemarkDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String msg = dialog.getEditText();
                if(!TextUtils.isEmpty(msg)){
                    remarkTv.setText(msg);
                    accountBean.setRemark(msg);
                }
                dialog.cancel();
            }
        });
    }
}