package com.example.yurdaer.kavun;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements View.OnClickListener {
    private TextView tvDatePicker;
    private Spinner spinnerCategory;
    private EditText textAddIncomeTitle;
    private EditText textAddIncomeAmount;
    private Button brnSave;
    private Controller controller;
    private boolean isIncome;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        Bundle bundle = getArguments();
        isIncome = bundle.getBoolean("isIncome");
        initComponents(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        brnSave.setOnClickListener(this);
        tvDatePicker.setOnClickListener(this);
    }

    private void initComponents(View view) {
        tvDatePicker = (TextView) view.findViewById(R.id.tv_date_pick);

        spinnerCategory = (Spinner) view.findViewById(R.id.spn_add_inc);
        textAddIncomeTitle = (EditText) view.findViewById(R.id.tvTitle_add_inc);
        textAddIncomeAmount = (EditText) view.findViewById(R.id.tvAmount_add_inc);
        brnSave = (Button) view.findViewById(R.id.btn_save_inc);
        ArrayAdapter<CharSequence> adapter;
        if (isIncome) {
            adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.category_income_items, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } else {
            adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.category_expenditure_items, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        spinnerCategory.setAdapter(adapter);


    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save_inc:
                if (checkInputs()) {
                    if (isIncome) {
                        controller.saveIncomeClicked(tvDatePicker.getText().toString(), textAddIncomeTitle.getText().toString(), spinnerCategory.getSelectedItem().toString(), Integer.parseInt(textAddIncomeAmount.getText().toString()));
                    } else {
                        controller.saveExpenseClicked(tvDatePicker.getText().toString(), textAddIncomeTitle.getText().toString(), spinnerCategory.getSelectedItem().toString(), Integer.parseInt(textAddIncomeAmount.getText().toString()));
                    }
                }
                break;
            case R.id.tv_date_pick:
                Calendar calender = Calendar.getInstance();
                Dialog mDialog = new DatePickerDialog(getContext(),
                        mDatesetListener, calender.get(Calendar.YEAR),
                        calender.get(Calendar.MONTH), calender
                        .get(Calendar.DAY_OF_MONTH));
                mDialog.show();
                break;

        }
    }

    private boolean checkInputs() {
        boolean res = true;

        if (tvDatePicker.getText().toString().equals("") || spinnerCategory.getSelectedItem().toString().equals("") || textAddIncomeTitle.getText().toString().equals("") || textAddIncomeAmount.getText().toString().equals("") ) {
            controller.makeToast("All fields are required !!!");
            return false;
        }
        if(spinnerCategory.getSelectedItem().toString().equals("Category")){
            controller.makeToast("Please select a category !!!");
            return false;
        }
        if (!TextUtils.isDigitsOnly(textAddIncomeAmount.getText()) || !(Integer.parseInt(textAddIncomeAmount.getText().toString()) > 0)) {
            controller.makeToast("Wrong amount input !!!");
            return false;
        } else return true;
    }

    private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            i1 = i1 + 1;
            String my_date = i + "-" + i1 + "-" + i2;
            tvDatePicker.setText(my_date);
        }
    };
}
