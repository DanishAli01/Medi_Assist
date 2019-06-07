package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Medications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.LoginActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Adapters.RecyclerViewAdapter;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.Medication;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Vitals.Vitals;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.RequiresApi;

public class medication_listview_activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private TapBarMenu tapBarMenu;
    private VolleyRequestHandler volleyRequestHandler;
    private ImageView qr;
    private ImageView bookings;
    private LoginLocalDAO loginLocalDAO;
    private String gellallmeds;
    private ArrayList<Medication> mEntries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_listview_activity);
        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenuMedi);
        volleyRequestHandler = new VolleyRequestHandler(this);
        loginLocalDAO = new LoginLocalDAO(this);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);
        gellallmeds = this.getResources().getString(R.string.getallmeds);
        recyclerView = findViewById(R.id.recyclerViewmedicine);
        mEntries = new ArrayList<>();



        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(medication_listview_activity.this, QR.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                medication_listview_activity.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(medication_listview_activity.this, Booking.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                medication_listview_activity.this.startActivity(mainswitch);
                onStop();

            }
        });




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);




//        volleyRequestHandler.getRequest(gellallmeds+loginLocalDAO.getLogin().getId(),new Response.Listener<JSONObject>() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    medication.setName(response.getString("name"));
//                    medication.setDate(response.getString("date"));
//                    medication.setTreatment_for(response.getString("prescribed_by"));
//                    medication.setPrescribed_by(response.getString("treatment_for"));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            Log.i("medications",medication.toString());
////                arrayList.add("Name : ");
////                arrayList.add("Gender : ");
////                arrayList.add("Email : ");
////                arrayList.add("Age : ");
////                arrayList.add("Date of birth : ");
////                arrayList.add("Eircode : ");
////                arrayList.add("Mobile : ");
////                arrayList.add("Home : ");
////                arrayList.add("Bloodgroup : "+loginLocalDAO.getLogin().getId());
//
//                initAdapter();
//
//
//            }
//
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try{
//                    String errorRes = new String(error.networkResponse.data);
//                    JSONObject result = new JSONObject(errorRes);
//                    Log.e("Profile Error",result.toString());
//
//                    if(result.getInt("status") == 500){
//
//                    }
//                    else{
//
//                    }
//                } catch (JSONException e){
//                    e.printStackTrace();
//                } catch (NullPointerException e){
//                    e.printStackTrace();
//                }
//            }
//        });

        volleyRequestHandler.JSONobjectHandler(gellallmeds+loginLocalDAO.getLogin().getId(),
                 new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);



//                                Medication movie = new Medication();
//                                movie.setName(jsonObject.getString("title"));
//                                movie.(jsonObject.getInt("rating"));
//                                movie.setYear(jsonObject.getInt("releaseYear"));

                                Medication medi = new Medication();
                                medi.setName(jsonObject.getString("name"));
                               medi.setDate(jsonObject.getString("date"));
                               medi.setTreatment_for(jsonObject.getString("prescribed_by"));
                              medi.setPrescribed_by(jsonObject.getString("treatment_for"));

                              arrayList.add("Name: "+medi.getName()+"\n"+
                                              "Date: "+medi.getDate()+"\n"+
                                              "Prescribed By: "+medi.getTreatment_for()+"\n"+
                                              "Treatment For: "+medi.getPrescribed_by()+"\n"

                                      );
                              initAdapter();

                                    mEntries.add(medi);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                            Log.i("medi", mEntries.toString());
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());

                    }
                });


    }

    private void initAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_heart) {
            Log.i("Heart", loginLocalDAO.getLogin().toString());
            Intent mainswitch = new Intent(medication_listview_activity.this, Vitals.class);
            medication_listview_activity.this.startActivity(mainswitch);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
