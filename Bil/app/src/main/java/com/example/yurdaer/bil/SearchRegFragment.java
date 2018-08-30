package com.example.yurdaer.bil;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class SearchRegFragment extends Fragment implements View.OnClickListener, EditText.OnEditorActionListener {
    private Button btnSearch;
    private Button btnScan;
    private EditText etReg;
    private String regNumber = "";


    public SearchRegFragment() { /* Required empty public constructor */ }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_reg, container, false);
        Bundle args = getArguments();
        if (args  != null){
            regNumber = args.getString("reg");
        }
        initializeComponents(rootView);
        attachListeners();
        return rootView;
    }

    private void initializeComponents(View rootView) {
        btnSearch = (Button) rootView.findViewById(R.id.search_frag_btn_search);
        btnScan = (Button) rootView.findViewById(R.id.search_frag_btn_scan);
        etReg = (EditText) rootView.findViewById(R.id.search_frag_et_reg);
        if(regNumber!=null)
        etReg.setText(regNumber);
    }

    private void attachListeners() {
        btnSearch.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        etReg.setOnClickListener(this);
        etReg.setOnEditorActionListener(this);
    }

    private boolean validReg() {
        String regNbr = etReg.getText().toString();
        return (!regNbr.isEmpty() && regNbr.length() == 6);
    }

    private void clearRegField() {
        etReg.getText().clear();
        etReg.setCursorVisible(false);
    }

    private void startTabActivity() {
        Intent tabIntent = new Intent(getActivity(), TabActivity.class);
        startActivity(tabIntent);
    }


    private void startCameraActivity() {
        Intent cameraIntent = new Intent(getActivity(), Camera_Activity.class);
        startActivity(cameraIntent);
    }

    public void showText(String text) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.search_frag_btn_search:
                if (validReg()) {
                    clearRegField();
                    startTabActivity();
                } else {
                    showText("Enter a valid reg number, 6 characters.");
                }
                break;

            case R.id.search_frag_btn_scan:
                startCameraActivity();
                etReg.setCursorVisible(false);
                break;

            case R.id.search_frag_et_reg:
                etReg.setCursorVisible(true);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etReg.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            etReg.setCursorVisible(false);
            return true;
        }
        return false;
    }

}
