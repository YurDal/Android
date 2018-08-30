package com.example.yurdaer.bil;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;



public class TabActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initializeTabLayout();
        setupSectionsPageAdapter(viewPager);
    }

    private void initializeTabLayout() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupSectionsPageAdapter(ViewPager viewPager) {
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.addFragment(new Tab1Fragment(), "Fordonsdata");
        sectionsPageAdapter.addFragment(new Tab2Fragment(), "Teknisk Data");
        sectionsPageAdapter.addFragment(new Tab3Fragment(), "Historik");
        viewPager.setAdapter(sectionsPageAdapter);
    }

}
