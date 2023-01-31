package com.hui.tallybox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hui.tallybox.db.DBManager;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_iv_back:
                finish();
                break;
            case R.id.setting_tv_clear:
                showClearDialog();
                break;
            case R.id.setting_tv_about:
                Intent it=new Intent(this,AboutActivity.class);
                startActivity(it);
        }
    }
    /*弹出是否清空的对话框*/
    private void showClearDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("清空后将无法恢复,是否确定清空所有记录?").setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBManager.clearAllAccounttb();
                Toast.makeText(SettingActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.create().show();
    }
}