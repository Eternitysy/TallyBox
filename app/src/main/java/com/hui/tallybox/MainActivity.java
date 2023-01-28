package com.hui.tallybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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