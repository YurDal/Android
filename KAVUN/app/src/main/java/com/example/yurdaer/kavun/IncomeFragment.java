package com.example.yurdaer.kavun;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment implements View.OnClickListener{
    private PieChart chart;
    private ImageButton btnAdd;
    private ImageButton btnEdit;
    private ImageButton btnRefresh;
    private Controller controller;
    private int totalSalary;
    private int totalInterest;
    private int totalExhange;
    private int totalOther;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        Bundle bundle = getArguments();
        totalSalary = bundle.getInt("salary");
        totalInterest = bundle.getInt("interest");
        totalExhange = bundle.getInt("exchange");
        totalOther = bundle.getInt("other");
        initComponents(view);
        setUpPieChart();
        setListeners();
        return view;
    }

    private void initComponents(View view) {
        chart = (PieChart) view.findViewById(R.id.pie_graph_income);
        btnAdd = (ImageButton) view.findViewById(R.id.btn_add);
        btnEdit = (ImageButton) view.findViewById(R.id.btn_edit);
        btnRefresh = (ImageButton) view.findViewById(R.id.refresh_income);


    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void setListeners() {
        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);

    }


    private void setUpPieChart() {
        List<PieEntry> pieEntrys = new ArrayList<>();
        if (totalSalary > 0) {
            pieEntrys.add(new PieEntry(totalSalary, "Salary"));
        }
        if (totalInterest > 0) {
            pieEntrys.add(new PieEntry(totalInterest, "Interest"));
        }
        if (totalExhange > 0) {
            pieEntrys.add(new PieEntry(totalExhange, "Exchange"));
        }
        if (totalOther > 0) {
            pieEntrys.add(new PieEntry(totalOther, "Other"));
        }

        PieDataSet dataSet = new PieDataSet(pieEntrys, "");
        dataSet.setValueTextSize(20);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        // dataSet.setValueTextSize(0);
        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.setCenterText(Integer.toString(totalSalary+totalOther+totalExhange+totalInterest)+" SEK");
        chart.setCenterTextSize(30);
        chart.setCenterTextColor(getResources().getColor(R.color.green));
        chart.setHoleColor(getResources().getColor(R.color.transparent));
        chart.animateY(1000);
        chart.invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                controller.addClickedIncome();
                break;
            case R.id.btn_edit:
                controller.editClickedIncome();
                break;
            case R.id.refresh_income:
                int[] array = controller.getIncome();
                totalSalary = array[0];
                totalInterest = array[1];
                totalExhange = array[2];
                totalOther = array[3];
                setUpPieChart();
                break;
        }
    }
}
