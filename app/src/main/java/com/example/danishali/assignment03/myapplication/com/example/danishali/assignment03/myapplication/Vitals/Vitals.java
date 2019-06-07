package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Vitals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.LoginActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.SignupActvity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.ChangeEmail.ConfrimEmail;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.GraphMaking.graphplot;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Illness.illness;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.Vital;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.RequiresApi;
import de.nitri.gauge.Gauge;


public class Vitals extends AppCompatActivity {
    private Toolbar mTopToolbar;
    private TextView heart_rate;
    private TextView respiratory_rate;
    private TextView heart_beat;
    private TextView cholesterol;
    private TextView weight;
    private TextView temprature;
    private VolleyRequestHandler volleyRequestHandler;
    private String vitalstopendpoint;
    private Vital vitals;
    private Gauge g;
    private Gauge g1;
    private LoginLocalDAO loginLocalDAO;
    private TapBarMenu tapBarMenu;
    private ImageView qr;
    private ImageView bookings;
    private String updatevitalsendpoint;
    private float x2,x1,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar_vitals);
        setSupportActionBar(mTopToolbar);


        heart_rate = (TextView) findViewById(R.id.heart_rate);
        respiratory_rate = (TextView) findViewById(R.id.respiratory_rate);
        heart_beat = (TextView) findViewById(R.id.heart_beat);
        cholesterol = (TextView) findViewById(R.id.cholesterol);
        weight = (TextView) findViewById(R.id.weight);
        temprature = (TextView) findViewById(R.id.temperature);
        volleyRequestHandler = new VolleyRequestHandler(this);
        vitalstopendpoint = this.getResources().getString(R.string.vitalstopendpoint);
        updatevitalsendpoint = this.getResources().getString(R.string.vitalupdateendpoint);
        vitals = new Vital();
        g = findViewById(R.id.gauge);
        g1 = findViewById(R.id.gauge1);
        loginLocalDAO = new LoginLocalDAO(this);
        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenuMedivitals);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);

        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(Vitals.this, QR.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                Vitals.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(Vitals.this, Booking.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                Vitals.this.startActivity(mainswitch);
                onStop();

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vital, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_heart) {
//            Intent mainswitch = new Intent(Vitals.this,  graphplot.class);
//            Vitals.this.startActivity(mainswitch);
//            return true;

//            final AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(Vitals.this);
//            View profileview = getLayoutInflater().inflate(R.layout.content_vitals,null);
//
//            final TextView forgot_DOB = profileview.findViewById(R.id.forgot_password_dateofbirth);
//            final TextView forgot_email = profileview.findViewById(R.id.forgot_password_email);
//            final TextView forgot_pin = profileview.findViewById(R.id.forgot_password_pin);
//            profile_dialogue.setView(profileview);
//
//
//            profile_dialogue.setPositiveButton("Access", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//
//
//
//
//                    volleyRequestHandler.getRequest(afterLogingetprofile_endpoint+forgot_email.getText().toString(),new Response.Listener<JSONObject>() {
//                        @RequiresApi(api = Build.VERSION_CODES.O)
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//
//
//                                personalProfile.setHome(response.getString("home"));
//                                personalProfile.setDateofbirth(response.getString("eircode"));
//                                personalProfile.setPassword(response.getString("password"));
//
//                                String[] parts = personalProfile.getHome().split("-");
//                                String part2 = parts[1]; // 034556
//
//                                Log.i("pin",""+part2);
//                                Log.i("pin",""+personalProfile);
//
//
//                                Log.i("pin","done");
//                                passwordtextview.setText(securePassword.UNFOLD(personalProfile.getPassword()));
//                                countfailedlogin=0;
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//
//
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            try{
//                                String errorRes = new String(error.networkResponse.data);
//                                JSONObject result = new JSONObject(errorRes);
//                                Log.e("Profile Error",result.toString());
//
//                                if(result.getInt("status") == 500){
//
//                                }
//                                else{
//
//                                }
//                            } catch (JSONException e){
//                                e.printStackTrace();
//                            } catch (NullPointerException e){
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//
//
//                }
//            });
//            AlertDialog dialog = profile_dialogue.create();
//            dialog.show();


            AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(Vitals.this);
            View profileview = getLayoutInflater().inflate(R.layout.vitalsupdate,null);

            final EditText bloodpressure = profileview.findViewById(R.id.bloodpressure_up);
            final EditText heartrate = profileview.findViewById(R.id.heart_rate_up);
            final EditText heartbeat = profileview.findViewById(R.id.heart_beat_up);
            final EditText cholesterol = profileview.findViewById(R.id.cholesterol_up);
            final EditText weight = profileview.findViewById(R.id.weight_up);
            final EditText temperature = profileview.findViewById(R.id.temperature_up);
            final EditText respiratoryrate = profileview.findViewById(R.id.respiratory_rate_up);

            profile_dialogue.setView(profileview);
            profile_dialogue.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    vitals.setTime(getCurrentTimeUsingDate());
                    vitals.setVitalbloodpressure(bloodpressure.getText().toString());
                    vitals.setVitalbodytemperature(temperature.getText().toString());
                    vitals.setVitalcholesterol(cholesterol.getText().toString());
                    vitals.setVitalheartbeat(heartbeat.getText().toString());
                    vitals.setVitalheartrate(heartrate.getText().toString());
                    vitals.setVitalrespiratoryrate(respiratoryrate.getText().toString());
                    vitals.setVitalweightkgsbmi(weight.getText().toString());
                    vitals.setMap(loginLocalDAO.getLogin().getId());

                    Log.i("Updates Vitals",vitals.toString());

                    JSONObject jsonbody= new JSONObject();

                    try {
                        jsonbody.put("vitalbloodpressure",vitals.getVitalbloodpressure());
                        jsonbody.put("vitalbodytemperature",vitals.getVitalbodytemperature());
                        jsonbody.put("vitalcholesterol",vitals.getVitalcholesterol());
                        jsonbody.put("vitalheartbeat",vitals.getVitalheartbeat());
                        jsonbody.put("vitalheartrate",vitals.getVitalheartrate());
                        jsonbody.put("vitalrespiratoryrate",vitals.getVitalrespiratoryrate());
                        jsonbody.put("vitalweightkgsbmi",vitals.getVitalweightkgsbmi());

                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                    Log.i("Json",jsonbody.toString());


                    if(volleyRequestHandler.hasActiveInternetConnection()){
                        volleyRequestHandler.postRequest(updatevitalsendpoint+loginLocalDAO.getLogin().getId(),jsonbody, listenerResponse, listenerError);
                        Intent intent = new Intent(Vitals.this,
                                Vitals.class);

                        startActivity(intent);
                    } else{
                        Log.i("No Connection","NO  INTERNET ACCESS");
                    }
                }
            });
            AlertDialog dialog = profile_dialogue.create();
            dialog.show();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {

        Login login = loginLocalDAO.getLogin();
        Log.i("Onstart",login.getId());

        volleyRequestHandler.getRequest(vitalstopendpoint+login.getId(),new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    vitals.setTime(response.getString("time"));
                    vitals.setVitalbloodpressure(response.getString("vitalbloodpressure"));
                    vitals.setVitalbodytemperature(response.getString("vitalbodytemperature"));
                    vitals.setVitalcholesterol(response.getString("vitalcholesterol"));
                    vitals.setVitalheartbeat(response.getString("vitalheartbeat"));
                    vitals.setVitalheartrate(response.getString("vitalheartrate"));
                    vitals.setVitalrespiratoryrate(response.getString("vitalrespiratoryrate"));
                    vitals.setVitalweightkgsbmi(response.getString("vitalweightkgsbmi"));

                    Log.i("Onstart",vitals.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String systolic = vitals.getVitalbloodpressure().substring(0,vitals.getVitalbloodpressure().lastIndexOf('/'));
                String diastolic = vitals.getVitalbloodpressure().substring(vitals.getVitalbloodpressure().lastIndexOf('/')+1,vitals.getVitalbloodpressure().length());
                Log.i("Pressure",systolic); Log.i("Pressure2",diastolic);
                g.moveToValue(Float.parseFloat(systolic));
                g1.moveToValue(Float.parseFloat(diastolic));
                heart_rate.setText(vitals.getVitalheartrate());
                respiratory_rate.setText(vitals.getVitalrespiratoryrate());
                heart_beat.setText(vitals.getVitalheartbeat());
                cholesterol.setText(vitals.getVitalcholesterol());
                weight.setText(vitals.getVitalweightkgsbmi());
                temprature.setText(vitals.getVitalbodytemperature());




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String errorRes = new String(error.networkResponse.data);
                    JSONObject result = new JSONObject(errorRes);
                    Log.e("Profile Error",result.toString());

                    if(result.getInt("status") == 500){

                    }
                    else{

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

        super.onStart();
    }



    public String  getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    Response.Listener<String> listenerResponse = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if(response == null)
                Log.d("profile null","null response");
            Log.d("Profile Response",response);
        }
    };

    Response.ErrorListener listenerError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            try{
                String errorRes = new String(error.networkResponse.data);
                JSONObject result = new JSONObject(errorRes);
                Log.e("Profile Error",result.toString());
            } catch (JSONException e){
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    };

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
                    Intent i = new Intent(Vitals.this, graphplot.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

}
