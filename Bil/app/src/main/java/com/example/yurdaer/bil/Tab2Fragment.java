package com.example.yurdaer.bil;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;


public class Tab2Fragment extends Fragment {
    private List<String> techDataList;


    public Tab2Fragment() { /* Required empty public constructor */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        initArrayList();
        initRecyclerView(rootView);
        return rootView;
    }

    private void initArrayList() {
        techDataList = new ArrayList<>();
        techDataList.add("Motoreffekt");
        techDataList.add("Motorvolym");
        techDataList.add("Toppfart");
        techDataList.add("Drivmedel");
        techDataList.add("Förbrukning bl.");
        techDataList.add("Utsläpp CO2");
        techDataList.add("Växellåda");
        techDataList.add("Fyrhjulsdrift");
        techDataList.add("Ljudnivå körning");
        techDataList.add("Passagerare");
        techDataList.add("Airbag passagerare");
    }

    private void initRecyclerView(View rootView) {
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rc_tab_2);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ListAdapterTab2 listAdapter = new ListAdapterTab2(techDataList, this);
        mRecyclerView.setAdapter(listAdapter);
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}
