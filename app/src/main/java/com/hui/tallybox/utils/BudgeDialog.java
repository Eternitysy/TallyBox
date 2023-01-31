package com.hui.tallybox.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.hui.tallybox.R;


import androidx.annotation.NonNull;
/*
 * 设置预算对话框
 * */
public class BudgeDialog extends Dialog implements View.OnClickListener {
    EditText et;
    Button cancelBtn,ensureBtn;
    //设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    OnEnsureListener onEnsureListener;
    public BudgeDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_budget);//设置显示布局
        et=findViewById(R.id.dialog_budget_et);
        cancelBtn=findViewById(R.id.dialog_budget_btn_cancel);
        ensureBtn=findViewById(R.id.dialog_budget_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);

    }
    public interface OnEnsureListener{
        public void onEnsure(float money);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_budget_btn_cancel:
                cancel();
                break;
            case R.id.dialog_budget_btn_ensure:
                String data=et.getText().toString();
                if(TextUtils.isEmpty(data)){
                    Toast.makeText(getContext(), "输入数据不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                float money=Float.parseFloat(data);
                if(onEnsureListener!=null){
                    onEnsureListener.onEnsure(money);
                }
                cancel();
                break;
        }

    }
    /*获取输入数据的方法*/
    public String getEditText(){
        return et.getText().toString().trim();
    }
    /*设置Dialog的尺寸和当前屏幕一致*/
    public void setDialogSize(){
        //获取当前窗口对象
        Window window = getWindow();
        //获取窗口对象的参数
        WindowManager.LayoutParams w=window.getAttributes();
        //获取屏幕尺寸
        Display d=window.getWindowManager().getDefaultDisplay();
        w.width=(int)(d.getWidth());
        w.gravity= Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(w);
        handler.sendEmptyMessageDelayed(2,100); //延迟弹出，否则会产生冲突
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            /*自动弹出软键盘的方法*/
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
}




