package com.example.yurdaer.bil;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by Yurdaer Dalkic on 2017-11-13.
 */
public class Camera_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getFragmentManager().beginTransaction().add(R.id.mainLayout, new CameraFragment()).commit();
    }
}
