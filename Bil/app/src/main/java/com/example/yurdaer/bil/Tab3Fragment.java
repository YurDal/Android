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


public class Tab3Fragment extends Fragment {
    private List<String> vehicleHistoryList;


    public Tab3Fragment() { /* Required empty public constructor */ }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab3, container, false);
        initArrayList();
        initRecyclerView(rootView);
        return rootView;
    }

    private void initArrayList() {
        vehicleHistoryList = new ArrayList<>();
        vehicleHistoryList.add("Besiktigad 2017");
        vehicleHistoryList.add("Ägarbyte 2015");
        vehicleHistoryList.add("Registrerad 2008");
        vehicleHistoryList.add("Ägarbyte 2008");
        vehicleHistoryList.add("Trafikstatus 2008");
        vehicleHistoryList.add("Förregistrerad 2008");
        vehicleHistoryList.add("Tillverkad 2008");
    }

    private void initRecyclerView(View rootView) {
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rc_tab_3);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ListAdapterTab3 listAdapter = new ListAdapterTab3(vehicleHistoryList, this);
        mRecyclerView.setAdapter(listAdapter);
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}
