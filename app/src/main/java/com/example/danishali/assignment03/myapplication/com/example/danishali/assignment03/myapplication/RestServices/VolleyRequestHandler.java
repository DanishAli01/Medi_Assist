package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.danishali.assignment03.myapplication.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class VolleyRequestHandler {

    private final String domain;

    private Context context;
    private RequestQueue requestQueue;

    public VolleyRequestHandler(Context context) {
        this.domain = context.getResources().getString(R.string.domain);
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    private boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = connectivityManager.getAllNetworkInfo();

        if(netInfo == null || netInfo.length == 0)
            return false;
        else{
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            return haveConnectedWifi || haveConnectedMobile;
        }
    }

    public boolean hasActiveInternetConnection() {
        if (isNetworkAvailable()) {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
            } catch (IOException e) {
                e.getMessage();
            }
        } else {
            return false;
        }
        return false;
    }

    public void getRequest(String endpoint, Response.Listener<JSONObject> listenerResponse, Response.ErrorListener listenerError){
        final String url = domain + endpoint;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listenerResponse, listenerError);
        requestQueue.add(request);
    }

    public void postRequest(String endpoint, JSONObject jsonBody, Response.Listener listenerResponse, Response.ErrorListener listenerError){
        final String url = domain + endpoint;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody, listenerResponse, listenerError);
        requestQueue.add(request);
    }

    public void patchRequest(String endpoint,Response.Listener<String> listenerResponse, Response.ErrorListener listenerError ){
        final String url = domain + endpoint;
        StringRequest request = new StringRequest(Request.Method.PATCH,url,listenerResponse,listenerError);
        requestQueue.add(request);

    }


    public void stringrequest(String endpoint,Response.Listener<String> idl,Response.ErrorListener edl){

        final String url = domain + endpoint;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,idl,edl);
        requestQueue.add(stringRequest);
    }
    public void stringrequestdelete(String endpoint,Response.Listener<String> idl,Response.ErrorListener edl){

        final String url = domain + endpoint;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,idl,edl);
        requestQueue.add(stringRequest);
    }

    public void  JSONobjectHandler(String endpoint, Response.Listener<JSONArray> listenerResponse, Response.ErrorListener listenerError){
        final String url = domain + endpoint;
        JsonArrayRequest request = new JsonArrayRequest(url,listenerResponse ,listenerError );
        requestQueue.add(request);

    }



}
