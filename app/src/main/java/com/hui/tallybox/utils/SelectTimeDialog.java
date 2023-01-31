package com.hui.tallybox.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.hui.tallybox.R;

import androidx.annotation.NonNull;

/*
 * 设置时间选择对话框
 * */
public class SelectTimeDialog extends Dialog implements View.OnClickListener {
    DatePicker datePicker;
    EditText hours,minutes;
    Button cancelBtn,ensureBtn;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    OnEnsureListener onEnsureListener;
    public interface OnEnsureListener{
        public void OnEnsure(String time,int year,int month,int day);

    }
    public SelectTimeDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_time);
        datePicker=findViewById(R.id.dialog_time_dp);
        hours=findViewById(R.id.dialog_time_ed_hour);
        minutes=findViewById(R.id.dialog_time_ed_minute);
        cancelBtn=findViewById(R.id.dialog_time_btn_cancel);
        ensureBtn=findViewById(R.id.dialog_time_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_time_btn_cancel:
                cancel();
                break;
            case R.id.dialog_time_btn_ensure:
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int dayOfMonth = datePicker.getDayOfMonth();
                String monthSg=String.valueOf(month);
                if(month<10){
                    monthSg="0"+month;
                }
                String daySg=String.valueOf(dayOfMonth);
                if(dayOfMonth<10){
                    daySg="0"+dayOfMonth;
                }
                String hoursg=hours.getText().toString();
                String minutesg=minutes.getText().toString();
                int hour=0;
                if(!TextUtils.isEmpty(hoursg)){
                    hour=Integer.parseInt(hoursg);
                    hour=hour%24;
                }
                int minute=0;
                if(!TextUtils.isEmpty(minutesg)){
                    minute=Integer.parseInt(minutesg);
                    minute=minute%60;
                }
                hoursg=String.valueOf(hour);
                minutesg=String.valueOf(minute);
                if(hour<10){
                    hoursg="0"+hour;
                }
                if(minute<10){
                    minutesg="0"+minute;
                }
                String timeformat=year+"/"+monthSg+"/"+daySg+" "+hoursg+":"+minutesg;
                if(onEnsureListener!=null){
                    onEnsureListener.OnEnsure(timeformat,year,month,dayOfMonth);
                }
                cancel();
                break;


        }

    }
}
