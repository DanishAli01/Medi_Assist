package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Illness;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Adapters.RecyclerViewAdapter;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Medications.medication_listview_activity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.MedicalHistory;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.Medication;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Vitals.Vitals;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class illness extends AppCompatActivity {

    private TapBarMenu tapBarMenu;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private VolleyRequestHandler volleyRequestHandler;
    private ImageView qr;
    private ImageView bookings;
    private ArrayList<MedicalHistory> mEntries;
    private LoginLocalDAO loginLocalDAO;
    private String gellallill;
    private float x1,x2,y1,y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illness);

        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenuMedi);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);
        recyclerView = findViewById(R.id.recyclerViewmedicine);
        mEntries = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewIllness);
        loginLocalDAO = new LoginLocalDAO(this);
        volleyRequestHandler = new VolleyRequestHandler(this);
        gellallill =  this.getResources().getString(R.string.gellallill);

        //Log.i("details",gellallill+loginLocalDAO.getLogin().getId());


        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(illness.this, QR.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                illness.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(illness.this, Booking.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                illness.this.startActivity(mainswitch);
                onStop();

            }
        });

        setSupportActionBar(toolbar);

        volleyRequestHandler.JSONobjectHandler(gellallill+loginLocalDAO.getLogin().getId(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);





                                MedicalHistory medi = new MedicalHistory();
                                medi.setIllness(jsonObject.getString("illness"));
                                medi.setDate(jsonObject.getString("date"));
                                medi.setDetails(jsonObject.getString("details"));

                                arrayList.add("Name: "+medi.getIllness()+"\n"+
                                        "Date: "+medi.getDate()+"\n"+
                                        "Details: "+medi.getDetails()


                                );
                                initAdapter();

                                mEntries.add(medi);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                            Log.i("medi", mEntries.toString()+"\n"+loginLocalDAO.getLogin().getId());
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
            Intent mainswitch = new Intent(illness.this, Vitals.class);
            illness.this.startActivity(mainswitch);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();

                if(x1 >  x2){
                    Log.i("Swiped","S");
                    Intent i = new Intent(illness.this, medication_listview_activity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }


}
