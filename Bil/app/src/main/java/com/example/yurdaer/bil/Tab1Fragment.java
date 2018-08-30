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


public class Tab1Fragment extends Fragment {
    private List<String> vehicleDataList;


    public Tab1Fragment() { /* Required empty public constructor */ }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        initArrayList();
        initRecyclerView(rootView);
        return rootView;
    }

    private void initArrayList() {
        vehicleDataList = new ArrayList<>();
        vehicleDataList.add("FABRIKAT");
        vehicleDataList.add("MODELL");
        vehicleDataList.add("FORDONSÅR");
        vehicleDataList.add("REG");
        vehicleDataList.add("STATUS");
        vehicleDataList.add("ANTAL ÄGARE");
        vehicleDataList.add("MÄTARSTÄLLNING");
    }

    private void initRecyclerView(View rootView) {
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rc_tab_1);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ListAdapterTab1 listAdapter = new ListAdapterTab1(vehicleDataList, this);
        mRecyclerView.setAdapter(listAdapter);
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }

}
