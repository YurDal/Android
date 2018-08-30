package com.example.yurdaer.kavun;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by YURDAER on 2017-10-08.
 */

public class Controller{
    public static String user;
    private MainActivity mainActivity;
    private MainFragment mainFragment;
    private IncomeFragment incomeFragment;
    private ExpenditureFragment expenditureFragment;
    private SQLHelperIncome tableIncome;
    private SQLHelperExpenditure tableExpenditure;

    public Controller(MainActivity mainActivity, String username) {
        this.user = username;
        this.mainActivity = mainActivity;
        this.tableIncome = new SQLHelperIncome(mainActivity.getApplicationContext(), user);
        this.tableExpenditure = new SQLHelperExpenditure(mainActivity.getApplicationContext(), user);

    }

    public void initFragments() {
        this.mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBack",false);
        bundle.putInt("incomes", tableIncome.getTotalIncome());
        bundle.putInt("expences", tableExpenditure.getTotalExpence());
        mainFragment.setArguments(bundle);
        mainFragment.setController(this);
        mainActivity.setFragment(mainFragment, false);
    }


    public void incomeClicked() {
        this.incomeFragment = new IncomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("salary", tableIncome.getTotalSalary());
        bundle.putInt("interest", tableIncome.getTotalInterest());
        bundle.putInt("exchange", tableIncome.getTotalExchange());
        bundle.putInt("other", tableIncome.getTotalOther());
        incomeFragment.setArguments(bundle);
        incomeFragment.setController(this);
        mainActivity.setFragment(incomeFragment, true);
    }


    public void expenceClicked() {
        this.expenditureFragment = new ExpenditureFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("food", tableExpenditure.getTotalFood());
        bundle.putInt("leisure", tableExpenditure.getTotalLeisure());
        bundle.putInt("travel", tableExpenditure.getTotalTravel());
        bundle.putInt("acommodation", tableExpenditure.getTotalAcommodation());
        bundle.putInt("other", tableExpenditure.getTotalOther());
        expenditureFragment.setArguments(bundle);
        expenditureFragment.setController(this);
        mainActivity.setFragment(expenditureFragment, true);
    }

    public int[] getExpence() {
        int[] array = {tableExpenditure.getTotalFood(), tableExpenditure.getTotalLeisure(), tableExpenditure.getTotalTravel(), tableExpenditure.getTotalAcommodation(), tableExpenditure.getTotalOther()};
        return array;
    }

    public int[] getIncome() {
        int[] array = {tableIncome.getTotalSalary(), tableIncome.getTotalInterest(), tableIncome.getTotalExchange(), tableIncome.getTotalOther()};
        return array;
    }

    public int[] getTotal() {
        int[] array = {tableIncome.getTotalIncome(), tableExpenditure.getTotalExpence()};
        return array;
    }


    public void addClickedIncome() {
        AddFragment addIncomeFragment = new AddFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isIncome", true);
        addIncomeFragment.setArguments(bundle);
        addIncomeFragment.setController(this);
        mainActivity.setFragment(addIncomeFragment, true);
    }

    public void editClickedIncome() {
        mainActivity.setListActivity(true,user);
    }

    public void addClickedExp() {
        AddFragment addIncomeFragment = new AddFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isIncome", false);
        addIncomeFragment.setArguments(bundle);
        addIncomeFragment.setController(this);
        mainActivity.setFragment(addIncomeFragment, true);
    }

    public void editClickedExp() {
        mainActivity.setListActivity(false,user);
    }

    public void makeToast(String message) {
        Context context = mainActivity.getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void saveIncomeClicked(String date, String tittle, String category, int amount) {
        tableIncome.addIncome(date, tittle, category, amount);
        incomeClicked();
    }

    public void saveExpenseClicked(String date, String tittle, String category, int amount) {
        tableExpenditure.addExpence(date, tittle, category, amount);
        expenceClicked();
    }
}
