package com.example.yurdaer.kavun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_signup;
    private EditText password_id;
    private EditText name_id;
    private TextView kavun_view;
    private CheckBox check_box_pass;
    private CheckBox check_box_remember;
    private Button btn_signin;
    private SQLHelperUsers sqlDataBase;
    private boolean remember = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqlDataBase = new SQLHelperUsers(this);
        initComponents();
        setListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (remember) {
            SharedPreferences sharedPreferences = this.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", name_id.getText().toString());
            editor.putString("password", password_id.getText().toString());
            editor.apply();
            makeToast("creating");
        } else {
            SharedPreferences sharedPreferences = this.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();
            makeToast("deleting");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        if (username != null && password != null) {
            name_id.setText(username);
            password_id.setText(password);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                if (checkValidation()) {
                    if (sqlDataBase.checkUserInfo(name_id.getText().toString(), password_id.getText().toString())) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("username", name_id.getText().toString());
                        startActivity(intent);
                    } else {
                        makeToast("Wrong username or password");
                    }
                }
                break;
            case R.id.btn_sign_up:
                if (checkValidation()) {
                    if (sqlDataBase.isUserNameExist(name_id.getText().toString())) {
                        makeToast("Username is already in use");
                    } else {
                        sqlDataBase.addUser(name_id.getText().toString(), password_id.getText().toString());
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("username", name_id.getText().toString());
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    private void initComponents() {
        btn_signup = (Button) findViewById(R.id.btn_sign_up);
        btn_signin = (Button) findViewById(R.id.btn_sign_in);
        password_id = (EditText) findViewById(R.id.m_pass_input);
        name_id = (EditText) findViewById(R.id.m_name_input);
        kavun_view = (TextView) findViewById(R.id.textView);
        check_box_pass = (CheckBox) findViewById(R.id.check_box);
        check_box_remember = (CheckBox) findViewById(R.id.check_box_remember);

    }

    private void setListeners() {
        btn_signup.setOnClickListener(this);
        btn_signin.setOnClickListener(this);
        check_box_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    check_box_pass.setText(R.string.Hide);
                    password_id.setInputType(InputType.TYPE_CLASS_TEXT);
                    password_id.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    check_box_pass.setText(R.string.Show);
                    password_id.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password_id.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
        check_box_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    remember = true;
                } else {
                    remember = false;
                }
            }
        });

    }


    public void makeToast(String message) {
        Context context = getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private boolean checkValidation() {
        String getPassword = password_id.getText().toString();
        String getName = name_id.getText().toString();

        if (getPassword.equals("") || getPassword.length() == 0 || getName.equals("0") || getName.length() == 0) {
            makeToast("All fields are required!!!");
            return false;
        }

        if (getPassword.length() <= 5) {
            makeToast("Password must contain at least six characters!!!");
            return false;
        } else {

            return true;
        }
    }

}
