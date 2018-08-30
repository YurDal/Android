package com.example.yurdaer.exe;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by Yurdaer on 2017-11-02.
 */
public class ConnectFragment extends Fragment implements View.OnClickListener {
    private EditText groupName;
    private Button btConnect;
    private Button btDisconnect;
    private Controller controller;

    public ConnectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect, container, false);
        initComponents(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        btConnect.setOnClickListener(this);
        btDisconnect.setOnClickListener(this);
    }

    private void initComponents(View view) {
        groupName = (EditText) view.findViewById(R.id.etGroup);
        btConnect = (Button) view.findViewById(R.id.btnConnect);
        btDisconnect = (Button) view.findViewById(R.id.btnDisconnect);

    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConnect:
                controller.connectClicked(groupName.getText().toString());
                break;
            case R.id.btnDisconnect:
                controller.disconnectClicked();
                break;
        }
    }
}
