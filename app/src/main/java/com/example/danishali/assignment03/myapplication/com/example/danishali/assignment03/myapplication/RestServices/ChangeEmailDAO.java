package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices;

import android.content.Context;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;


import org.json.JSONException;
import org.json.JSONObject;

public class ChangeEmailDAO {

    private final String endpoint;
    private Context context;
    private VolleyRequestHandler volleyRequestHandler;
    String r = "";


    public ChangeEmailDAO(Context context){
        this.endpoint = context.getResources().getString(R.string.endpointemailchange);
        this.context = context;
        this.volleyRequestHandler = new VolleyRequestHandler(context);


    }

    public void profileRequestHandler(){
        android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                Response.Listener<String> listenerResponse = new Response.Listener<String>() {
                    /**
                     * Called when a response is received.
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(String response) {

                        r = response;
                    }


                };

                Response.ErrorListener listenerError = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try{
                            String errorRes = new String(error.networkResponse.data);
                            JSONObject result = new JSONObject(errorRes);
//                            Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("Profile Error",result.toString());

//                            if(result.getInt("status") == 500){
//
//                            }
//                            else{
//
//                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        } catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                };

                if(volleyRequestHandler.hasActiveInternetConnection()){
                    volleyRequestHandler.patchRequest(endpoint,listenerResponse,listenerError);
                } else{
                    Log.i("No Connection","NO  INTERNET ACCESS");
                }
            }
        };

        mainHandler.post(myRunnable);
    }



}

