package com.example.yurdaer.kavun;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;


public class ListViewFragment extends android.app.ListFragment implements AdapterView.OnItemClickListener {
    private ListController listController;
    private Income[] incomes;
    private Expenditure[] expenditures;
    private boolean isIncome;
    private IncomeAdapter adapterIncome;
    private ExpenditureAdapter expenditureAdapter;
    private ImageButton btnRefresh;
    private TextView tvTimeStart;
    private TextView tvTimeFinish;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initComponents(view);
        setListener();
        Bundle bundle = this.getArguments();
        isIncome = bundle.getBoolean("isIncome");
        return view;
    }


    private void initComponents(View view) {
        btnRefresh = (ImageButton) view.findViewById(R.id.imageButtonRef);
        tvTimeStart = (TextView) view.findViewById(R.id.picker_1);
        tvTimeFinish = (TextView) view.findViewById(R.id.picker2);
    }

    private void setListener() {
        btnRefresh.setOnClickListener(new MyViewListener());
        tvTimeStart.setOnClickListener(new MyViewListener());
        tvTimeFinish.setOnClickListener(new MyViewListener());

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isIncome) {
            adapterIncome = new IncomeAdapter(getActivity().getApplicationContext(), incomes);
            setListAdapter(adapterIncome);
        } else {
            expenditureAdapter = new ExpenditureAdapter(getActivity().getApplicationContext(), expenditures);
            setListAdapter(expenditureAdapter);
        }

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void setListController(ListController listController) {
        this.listController = listController;
    }

    public void setIncomes(Income[] incomes) {
        this.incomes = incomes;
    }

    public void setExpenditures(Expenditure[] expenditures) {
        this.expenditures = expenditures;
    }

    public void refreshListView(Context context) {
        if (isIncome) {
            IncomeAdapter adapter = new IncomeAdapter(context, incomes);
            setListAdapter(adapter);
        } else {
            ExpenditureAdapter adapter = new ExpenditureAdapter(context, expenditures);
            setListAdapter(adapter);
        }
    }

    private class MyViewListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imageButtonRef:
                    listController.refreshClicked(tvTimeStart.getText().toString(), tvTimeFinish.getText().toString());
                    break;
                case R.id.picker_1:
                    Calendar calender = Calendar.getInstance();
                    Dialog mDialog = new DatePickerDialog(getContext(),
                            mDatesetListener, calender.get(Calendar.YEAR),
                            calender.get(Calendar.MONTH), calender
                            .get(Calendar.DAY_OF_MONTH));
                    mDialog.show();
                    break;
                case R.id.picker2:
                    Calendar calender2 = Calendar.getInstance();
                    Dialog mDialog2 = new DatePickerDialog(getContext(),
                            mDatesetListener2, calender2.get(Calendar.YEAR),
                            calender2.get(Calendar.MONTH), calender2
                            .get(Calendar.DAY_OF_MONTH));
                    mDialog2.show();
                    break;
            }

        }
    }
    private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            i1 = i1 + 1;
            String my_date = i + "-" + i1 + "-" + i2;
            tvTimeStart.setText(my_date);
        }
    };
    private DatePickerDialog.OnDateSetListener mDatesetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            i1 = i1 + 1;
            String my_date = i + "-" + i1 + "-" + i2;
            tvTimeFinish.setText(my_date);
        }
    };

}
