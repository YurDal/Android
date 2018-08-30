package com.example.yurdaer.kavun;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener{
    private PieChart chart;
    private Button btnIncome;
    private Button btnExpence;
    private ImageButton btnRefresh;
    private Controller controller;
    private int totalIncome = 0;
    private int totalExpence = 0;
    private boolean isBack = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Bundle bundle = getArguments();
        isBack = bundle.getBoolean("isBack");
        totalIncome = bundle.getInt("incomes");
        totalExpence = bundle.getInt("expences");
        initComponents(view);
        setUpPieChart();
        setListeners();
        return view;
    }

    private void initComponents(View view) {
        chart = (PieChart) view.findViewById(R.id.pie_graph_main);
        btnExpence = (Button) view.findViewById(R.id.btn_expence);
        btnIncome = (Button) view.findViewById(R.id.btn_income);
        btnRefresh = (ImageButton) view.findViewById(R.id.refresh_main);
    }

    private void setUpPieChart() {
        List<PieEntry> pieEntrys = new ArrayList<>();
        pieEntrys.add(new PieEntry(totalIncome, "Incomes"));
        pieEntrys.add(new PieEntry(totalExpence, "Expences"));
        PieDataSet dataSet = new PieDataSet(pieEntrys, "");
        dataSet.setValueTextSize(20);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.setUsePercentValues(false);
        chart.setHoleColor(getResources().getColor(R.color.transparent));
        chart.setCenterTextSize(90);
        if (totalIncome>totalExpence){
            chart.setCenterText("+");
            chart.setCenterTextColor(getResources().getColor(R.color.green));
        }
        else {
            chart.setCenterText("-");
            chart.setCenterTextColor(getResources().getColor(R.color.red));
        }
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
        chart.invalidate();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void setListeners() {
        btnIncome.setOnClickListener(this);
        btnExpence.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_income:
                controller.incomeClicked();
                break;
            case R.id.btn_expence:
                controller.expenceClicked();
                break;
            case R.id.refresh_main:
                int[] array = controller.getTotal();
                totalIncome = array[0];
                totalExpence = array[1];
                setUpPieChart();
                break;

        }
    }


}
