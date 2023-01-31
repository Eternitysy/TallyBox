package com.hui.tallybox.frag_record;


import com.hui.tallybox.R;
import com.hui.tallybox.db.DBManager;
import com.hui.tallybox.db.TypeBean;
import java.util.List;


public class IncomeFragment extends BaseFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        /*获取数据库当中的数据源*/
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