package com.hui.tallybox.frag_record;

import com.hui.tallybox.R;
import com.hui.tallybox.db.DBManager;
import com.hui.tallybox.db.TypeBean;

import java.util.List;

public class OutcomeFragment extends BaseFragment{
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> outlist= DBManager.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.more);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManager.insertItemToAccounttb(accountBean);
    }
}
