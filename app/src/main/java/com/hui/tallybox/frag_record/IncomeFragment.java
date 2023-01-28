package com.hui.tallybox.frag_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hui.tallybox.R;
import com.hui.tallybox.db.DBManager;
import com.hui.tallybox.db.TypeBean;
import com.hui.tallybox.utils.KeyBoardUtils;

import java.security.Key;
import java.util.List;

import androidx.fragment.app.Fragment;

public class IncomeFragment extends BaseFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> inlist= DBManager.getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.others);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManager.insertItemToAccounttb(accountBean);
    }
}