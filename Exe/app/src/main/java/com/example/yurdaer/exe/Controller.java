package com.example.yurdaer.exe;

import android.app.*;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;


/**
 * Created by Yurdaer on 2017-11-02.
 */
public class Controller {
    private MainActivity activity;
    private FragmentMap mapFragment;
    private GroupFragment listFragment;
    private ConnectFragment connectFragment;
    private TCPConnection connection;
    private boolean connected = false;
    private ServiceConnection serviceConn;
    private Listener listener;
    private UpdatePosition updatePosition;
    private boolean bound = false;
    private ArrayList<Person> markers;
    private String myID;
    private String myGroupName;
    private final String myName = "YURDAER";
    private JsonWriteRead jsonClass;
    private ArrayList<String> groups = new ArrayList();

    public Controller(MainActivity activity, Bundle savedInstanceState) {
        this.activity = activity;
        this.jsonClass = new JsonWriteRead();
        Intent intent = new Intent(activity, TCPConnection.class);
        intent.putExtra(TCPConnection.IP, "195.178.227.53");
        intent.putExtra(TCPConnection.PORT, "7117");
        if (savedInstanceState == null)
            activity.startService(intent);
        else
            connected = savedInstanceState.getBoolean("CONNECTED", false);
        serviceConn = new ServiceConn();
        boolean result = activity.bindService(intent, serviceConn, 0);
        if (!result)
            Log.i("Controller-constructor", "No binding");

    }


    public void onDestroy() {
        if (bound) {
            activity.unbindService(serviceConn);
            listener.stopListener();
            updatePosition.stopUpdate();
            bound = false;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("CONNECTED", connected);
    }

    public void connectServer() {
        connection.connect();
    }


    public ArrayList<Person> getMarkers() {
        return markers;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }


    public void mapSlected() {
        mapFragment = new FragmentMap();
        mapFragment.setController(this);
        activity.setFragment(mapFragment, false);
    }


    public void serverSelected() throws InterruptedException, JSONException {

        if (!connection.isConnected())
            connectServer();
        connection.send(jsonClass.askCurrentGroups());
        Log.i("Groupsssss", groups.toString());
        listFragment = new GroupFragment();
        activity.setFragment(listFragment, false);
        listFragment.setController(this);

    }


    public void disconnectClicked() {
        if (myID != null) {
            try {
                connection.send(jsonClass.askDeregistration(myID));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void connectClicked(String s) {
        if (myID == null) {
            try {
                connection.send(jsonClass.askRegistration(s, myName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createSelected() {
        connectFragment = new ConnectFragment();
        connectFragment.setController(this);
        activity.setFragment(connectFragment, false);
    }

    public void makeToast(String str) {
        Toast.makeText(activity.getApplicationContext(), str, Toast.LENGTH_SHORT).show();

    }

    private class ServiceConn implements ServiceConnection {
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            TCPConnection.LocalService ls = (TCPConnection.LocalService) binder;
            connection = ls.getService();
            bound = true;
            listener = new Listener();
            listener.start();
        }

        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    }

    private class UpdatePosition extends Thread {
        public void stopUpdate() {
            interrupt();
            updatePosition = null;
        }

        public void run() {
            while (updatePosition != null) {
                LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location mylocation = locationManager.getLastKnownLocation(provider);
                if (mylocation != null) {
                    try {
                      connection.send(jsonClass.sendPosition(myID, String.valueOf(mylocation.getLongitude()), String.valueOf(mylocation.getLatitude())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                try {
                    Log.i("Long", String.valueOf(mylocation.getLongitude()));
                    Log.i("Lat", String.valueOf(mylocation.getLatitude()));
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private class Listener extends Thread {
        public void stopListener() {
            interrupt();
            listener = null;
        }

        public void run() {
            String message;
            Exception exception;
            while (listener != null) {
                try {
                    message = connection.receive();
                    Log.i("Server", message);
                    activity.runOnUiThread(new ListenServer(message));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    listener = null;
                }
            }
        }
    }

    private class ListenServer implements Runnable {
        private String message;

        public ListenServer(String message) {
            this.message = message;
        }

        public void run() {
            Exception exception = connection.getException();
            JSONObject obj = null;
            try {
                obj = new JSONObject(message);
                String temp = obj.getString("type");
                if ("groups".equals(temp)) {
                    groups = jsonClass.readCurrentGroups(obj);
                } else if ("locations".equals(temp)) {
                    markers = jsonClass.readLocations(obj);
                } else if ("register".equals(temp)) {
                    String[] arr = jsonClass.readRegistration(obj);
                    myGroupName = arr[0];
                    myID = arr[1];
                    updatePosition = new UpdatePosition();
                    updatePosition.start();
                    makeToast("Connected to " + myGroupName + " ID is " + myID);
                } else if ("unregister".equals(temp)) {
                    myGroupName = null;
                    myID = null;
                    markers = null;
                    updatePosition.stopUpdate();
                    makeToast("Unregistred");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
