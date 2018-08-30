package com.example.yurdaer.kavun;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity  {
    private Controller controller;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        controller = new Controller(this,username);
        controller.initFragments();
    }

    public void setFragment(Fragment fragment, boolean backStack) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public void setListActivity(boolean isIncome,String username){
        Intent intent = new Intent(this,ListActivity.class);
        intent.putExtra("username",  username);
        intent.putExtra("isIncome",isIncome);
        startActivity(intent);
    }


}
