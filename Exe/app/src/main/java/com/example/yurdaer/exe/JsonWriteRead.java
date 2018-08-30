package com.example.yurdaer.exe;

import android.util.JsonWriter;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by YURDAER on 2017-11-01.
 */

public class JsonWriteRead {


    public String askCurrentGroups() {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        try {
            writer.beginObject().name("type").value("groups").endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public String askRegistration(String groupName, String memberName) throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        writer.beginObject().name("type").value("register").name("group").value(groupName).name("member").value(memberName).endObject();
        return stringWriter.toString();
    }

    public String askMembers(String groupName) throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        writer.beginObject().name("type").value("members").name("group").value(groupName).endObject();
        return stringWriter.toString();
    }

    public String askDeregistration(String ID) throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        writer.beginObject().name("type").value("unregister").name("id").value(ID).endObject();
        return stringWriter.toString();
    }

    public String sendPosition(String ID, String LONGITUDE, String LATITUDE) throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        writer.beginObject().name("type").value("location").name("id").value(ID).name("longitude").value(LONGITUDE).name("latitude").value(LATITUDE).endObject();
        return stringWriter.toString();
    }

    public ArrayList<String> readCurrentGroups(JSONObject obj) {
        ArrayList<String> strArray = new ArrayList();

        try {
            JSONObject temp;
            JSONArray jArray = obj.getJSONArray("groups");
            for (int i = 0; i < jArray.length(); i++) {
                temp = jArray.getJSONObject(i);
                strArray.add(temp.getString("group"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strArray;
    }

    public String[] readRegistration(JSONObject obj) {
        String[] strArray = new String[2];
        try {
            strArray[0] = obj.getString("group");
            strArray[1] = obj.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strArray;
    }

    public ArrayList<Person> readLocations(JSONObject obj) {
        ArrayList<Person> array = new ArrayList();

        try {
            JSONObject temp;
            JSONArray jArray = obj.getJSONArray("location");
            for (int i = 0; i < jArray.length(); i++) {
                temp = jArray.getJSONObject(i);
                array.add(new Person(temp.getString("member"), new LatLng(Double.parseDouble(temp.getString("latitude")), Double.parseDouble(temp.getString("longitude")))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }


}
