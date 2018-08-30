package com.example.yurdaer.bil;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.openalpr.OpenALPR;
import org.openalpr.model.Results;
import org.openalpr.model.ResultsError;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;


/**
 * Created by Yurdaer Dalkic on 2017-11-13.
 */
public class CameraFragment extends Fragment {
    private Button capture;
    private Camera mCamera;
    private String imagePath;
    private View view;
    private CameraPreview mPreview;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private String ANDROID_DATA_DIR;
    private String openAlprConfFile;


    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Create an instance of Camera
        ANDROID_DATA_DIR = getActivity().getApplicationInfo().dataDir;
        openAlprConfFile = ANDROID_DATA_DIR + File.separatorChar + "runtime_data" + File.separatorChar + "openalpr.conf";

        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        this.view = view;

        intComponents(view);
        return view;
    }

    private void intComponents(View view) {
        capture = (Button) view.findViewById(R.id.button_capture);
        capture.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                        mCamera.startPreview();
                    }
                }
        );
    }

    private void process() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String result = OpenALPR.Factory.create(getContext(), ANDROID_DATA_DIR).recognizeWithCountryRegionNConfig("eu", "", imagePath, openAlprConfFile, 6);

                try {
                    final Results results = new Gson().fromJson(result, Results.class);
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (results == null || results.getResults() == null || results.getResults().size() == 0 || results.getResults().get(0).getPlate().length()<6) {

                                Toast.makeText(getActivity().getApplicationContext(), "It was not possible to detect the licence plate.", Toast.LENGTH_LONG).show();

                            }
                            else {
                                String reg = results.getResults().get(0).getPlate();
                                Toast.makeText(getActivity().getApplicationContext(), results.getResults().get(0).getPlate(), Toast.LENGTH_LONG).show();
                                startTabActivity(reg);

                            }

                        }
                    });
                } catch (JsonSyntaxException exception) {
                    final ResultsError resultsError = new Gson().fromJson(result, ResultsError.class);
                    Log.i("Error ", resultsError.getMsg());
                }
            }
        });


    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            Log.i("Error", "Camera is not available" );
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.i("", "Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                refreshGalery(pictureFile);
                //    imagePath=pictureFile.getAbsolutePath();
                imagePath = pictureFile.getAbsolutePath();
                process();
            } catch (FileNotFoundException e) {
                Log.i("", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.i("", "Error accessing file: " + e.getMessage());
            }
        }
    };



    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");


        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.i("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "BIL_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCamera = getCameraInstance();
        mPreview = new CameraPreview(getContext(), mCamera,getActivity());
        FrameLayout preview = (FrameLayout) view.findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        mPreview.setVisibility(View.VISIBLE);
    }

    private void refreshGalery(File file) {
        MediaScannerConnection.scanFile(getContext(),
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        mPreview.setVisibility(View.GONE);
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void startTabActivity(String RegNumber) {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        mainIntent.putExtra("reg", RegNumber);
        startActivity(mainIntent);
    }


}
