package com.example.yurdaer.bil;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchRegFragment searchFragment = new SearchRegFragment();
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            searchFragment.setArguments(extras);
        }
        getFragmentManager().beginTransaction().add(R.id.startLayout, searchFragment).commit();
    }

}
