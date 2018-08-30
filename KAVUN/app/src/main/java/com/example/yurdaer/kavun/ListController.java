package com.example.yurdaer.kavun;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by YURDAER on 2017-10-11.
 */

public class ListController {
    private SQLHelperIncome tableIncome;
    private SQLHelperExpenditure tableExpenditure;
    private boolean isIncome;
    private ListViewFragment listViewFragment;
    private ListActivity listActivy;

    public ListController(ListActivity listActivity, boolean isIncome, String username, ListViewFragment listViewFragment) {
        this.isIncome = isIncome;
        this.tableIncome = new SQLHelperIncome(listActivity.getApplicationContext(), username);
        this.tableExpenditure = new SQLHelperExpenditure(listActivity.getApplicationContext(), username);
        this.listViewFragment = listViewFragment;
        this.listActivy = listActivity;
        initComponent();
    }

    private void initComponent() {
        listViewFragment.setExpenditures(tableExpenditure.getAllExpenditure());
        listViewFragment.setIncomes(tableIncome.getAllIncome());
        listViewFragment.refreshListView(listActivy.getApplicationContext());
    }


    public void refreshClicked(String start, String finish) {
        if (!start.equals("") && !finish.equals("")) {
            if (isIncome) {
                listViewFragment.setIncomes(tableIncome.getAllIncomeBetween(start, finish));
                listViewFragment.refreshListView(listActivy.getApplicationContext());

            } else {
                listViewFragment.setExpenditures(tableExpenditure.getAllExpenditureBetween(start, finish));
                listViewFragment.refreshListView(listActivy.getApplicationContext());

            }
        }
        else {
            makeToast("Select a valid date range");
        }
    }
    public void makeToast(String message) {
        Context context = listActivy.getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
