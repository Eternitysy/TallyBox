package com.hui.tallybox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hui.tallybox.R;
import com.hui.tallybox.db.AccountBean;

import java.util.Calendar;
import java.util.List;
/*
 * 账单ListView对应的适配器
 * */
public class AccountAdapter extends BaseAdapter {
    Context context;
    List<AccountBean>mData;
    LayoutInflater inflater;
    int year,month,day;
    public AccountAdapter(Context context, List<AccountBean> mData) {
        this.context = context;
        this.mData = mData;
        inflater=LayoutInflater.from(context);
        Calendar calendar=Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_mainlv,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
            AccountBean bean=mData.get(position);
            holder.typeIv.setImageResource(bean.getsImageid());
            holder.tyTv.setText(bean.getTypename());
            holder.remarkTv.setText(bean.getRemark());
            holder.moneyTv.setText("￥"+bean.getMoney());
            if(bean.getYear()==year&&bean.getMonth()==month&&bean.getDay()==day){
                String time=bean.getTime().split(" ")[1];
                holder.timeTv.setText("今天"+time);
            }
            else{
                holder.timeTv.setText(bean.getTime());
            }
            return convertView;
    }

    class ViewHolder{
        ImageView typeIv;
        TextView tyTv,remarkTv,moneyTv,timeTv;
        public ViewHolder(View view){
            typeIv=view.findViewById(R.id.item_mainlv_iv);
            tyTv=view.findViewById(R.id.item_mainlv_tv_title);
            remarkTv=view.findViewById(R.id.item_mainlv_tv_remark);
            moneyTv=view.findViewById(R.id.item_mainlv_tv_money);
            timeTv=view.findViewById(R.id.item_mainlv_tv_time);
        }
    }
}
