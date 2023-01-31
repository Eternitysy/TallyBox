package com.hui.tallybox.frag_chart;

public class OutcomeChartFragment extends BaseChartFragment {
    int kind = 0;
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
