package com.example.yurdaer.kavun;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    private String username;
    private boolean isIncome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        isIncome = bundle.getBoolean("isIncome");
        username = bundle.getString("username");
        ListViewFragment listViewFragment = new ListViewFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("isIncome", isIncome);
        listViewFragment.setArguments(bundle2);
        ListController listController = new ListController(this, isIncome, username, listViewFragment);
        listViewFragment.setListController(listController);
        setFragment(listViewFragment, false);
    }

    public void setFragment(Fragment fragment, boolean backStack) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerList, fragment);
        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }


}
