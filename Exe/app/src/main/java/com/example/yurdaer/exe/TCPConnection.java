package com.example.yurdaer.exe;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
/**
 * Created by Yurdaer on 2017-11-02.
 */

public class TCPConnection extends Service {
    public static final String IP = "IP", PORT = "PORT"; //
    private RunOnThread thread;
    private Receive receive;
    private Buffer<String> receiveBuffer; //
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private DataOutputStream dos;
    private InetAddress address;
    private int connectionPort;
    private String ip;
    private Exception exception;


    private boolean isConnected = false;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.ip = intent.getStringExtra(IP);
        this.connectionPort = Integer.parseInt(intent.getStringExtra(PORT));
        thread = new RunOnThread();
        receiveBuffer = new Buffer<String>();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return new LocalService();
    }

    public void connect() {
        thread.start();
        thread.execute(new Connect());
    }

    public void disconnect() {
        thread.execute(new Disconnect());
    }

    public void send(String Jmessage) {
        Log.i("Send", Jmessage);
        thread.execute(new Send(Jmessage));
    }

    public String receive() throws InterruptedException {

        return receiveBuffer.get();


    }


    public Exception getException() {
        Exception result = exception;
        exception = null;
        return result;
    }

    public boolean isConnected() {
        return isConnected;
    }


    public class LocalService extends Binder {
        public TCPConnection getService() {
            return TCPConnection.this;
        }
    }

    private class Receive extends Thread {
        public void run() {
            String result;
            try {
                while (receive != null) {
                    result = dis.readUTF();
                    receiveBuffer.put(result);
                }
            } catch (Exception e) { // IOException, ClassNotFoundException
                receive = null;
            }
        }
    }

    private class Connect implements Runnable {
        public void run() {
            try {
                Log.i(ip, Integer.toString(connectionPort));
                address = InetAddress.getByName(ip);
                socket = new Socket(address, connectionPort);
                is = socket.getInputStream();
                dis = new DataInputStream(is);
                os = socket.getOutputStream();
                dos = new DataOutputStream(os);
                dos.flush();
                receive = new Receive();
                receive.start();
                isConnected = true;
            } catch (Exception e) { // SocketException, UnknownHostException
                exception = e;
                isConnected = false;

            }
        }
    }

    private class Disconnect implements Runnable {
        public void run() {
            try {

                if (dis != null)
                    dis.close();
                if (dos != null)
                    dos.close();
                if (socket != null)
                    socket.close();
                thread.stop();
                isConnected = false;
            } catch (IOException e) {
                exception = e;
            }
        }
    }

    private class Send implements Runnable {
        private String JsonMessage;

        public Send(String JsonMessage) {
            this.JsonMessage = JsonMessage;
        }

        public void run() {
            try {
                dos.writeUTF(JsonMessage);
                dos.flush();
            } catch (IOException e) {
                exception = e;
            }
        }
    }
}
