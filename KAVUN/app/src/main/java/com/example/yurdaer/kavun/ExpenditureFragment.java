package com.example.yurdaer.kavun;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ExpenditureFragment extends Fragment implements View.OnClickListener {
    private PieChart chart;
    private ImageButton btnAddExp;
    private ImageButton btnEditExp;
    private ImageButton btnRefresh;
    private Controller controller;
    private int totalFood;
    private int totalLeisure;
    private int totalTravel;
    private int totalAcommodation;
    private int totalOther;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        Bundle bundle = getArguments();
        totalFood = bundle.getInt("food");
        totalLeisure = bundle.getInt("leisure");
        totalTravel = bundle.getInt("travel");
        totalAcommodation = bundle.getInt("acommodation");
        totalOther = bundle.getInt("other");
        initComponents(view);
        setUpPieChart();
        setListeners();
        return view;
    }

    private void initComponents(View view) {
        chart = (PieChart) view.findViewById(R.id.pie_graph_expenditure);
        btnAddExp = (ImageButton) view.findViewById(R.id.btn_add_expenditure);
        btnEditExp = (ImageButton) view.findViewById(R.id.btn_edit_expenditure);
        btnRefresh =(ImageButton) view.findViewById(R.id.refresh_expenc);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void setListeners() {
        btnAddExp.setOnClickListener(this);
        btnEditExp.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
    }
    private void refreshPieChart(){

    }

    private void setUpPieChart() {
        List<PieEntry> pieEntrys = new ArrayList<>();
        if (totalFood > 0) {
            pieEntrys.add(new PieEntry(totalFood, "Food"));
        }
        if (totalLeisure > 0) {
            pieEntrys.add(new PieEntry(totalLeisure, "Leisure"));
        }
        if (totalTravel > 0) {
            pieEntrys.add(new PieEntry(totalTravel, "Travel"));
        }
        if (totalAcommodation > 0) {
            pieEntrys.add(new PieEntry(totalAcommodation, "Lodging"));
        }
        if (totalOther > 0) {
            pieEntrys.add(new PieEntry(totalOther, "Other"));
        }
        PieDataSet dataSet = new PieDataSet(pieEntrys, "");
        dataSet.setValueTextSize(20);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.setCenterText(Integer.toString(totalFood+totalLeisure+totalTravel+totalAcommodation+totalOther)+" SEK");
        chart.setHoleColor(getResources().getColor(R.color.transparent));
        chart.setCenterTextSize(30);
        chart.setCenterTextColor(getResources().getColor(R.color.red));
        chart.animateY(1000);
        chart.invalidate();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_expenditure:
                controller.addClickedExp();
                break;
            case R.id.btn_edit_expenditure:
                controller.editClickedExp();
                break;
            case R.id.refresh_expenc:
                int[] array = controller.getExpence();
                totalFood = array[0];
                totalLeisure = array[1];
                totalTravel = array[2];
                totalAcommodation = array[3];
                totalOther = array[4];
                setUpPieChart();
                break;

        }
    }
}
