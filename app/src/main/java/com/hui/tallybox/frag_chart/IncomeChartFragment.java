package com.hui.tallybox.frag_chart;


public class IncomeChartFragment extends BaseChartFragment {
    int kind = 1;
    @Override
    public void onResume() {
        super.onResume();
        loadData(year,month,kind);
    }

    @Override
    public void setData(int year, int month) {
        super.setData(year, month);
        loadData(year,month,kind);
    }
}
