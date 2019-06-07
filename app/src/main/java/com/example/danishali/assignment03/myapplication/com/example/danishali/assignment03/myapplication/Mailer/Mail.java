package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Mailer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.TimePicker.TimePick;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.RequiresApi;

public class Mail extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button buttonSend;
    private LoginLocalDAO loginLocalDAO;
    private TextView header;
    String time;
    String date;
    String email;
    String gp;
    AlertDialog.Builder builder;
    private VolleyRequestHandler volleyRequestHandler;
    private Booking bks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailer_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");
        email = intent.getStringExtra("email");
        gp = intent.getStringExtra("gp");


        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        header = (TextView)findViewById(R.id.mailerhead);

        loginLocalDAO = new LoginLocalDAO(this);
        volleyRequestHandler = new VolleyRequestHandler(this);


        sendEmail();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainswitch = new Intent(Mail.this, MainActivity.class);
                mainswitch.putExtra("Profile",loginLocalDAO.getLogin().getEmail());
                Mail.this.startActivity(mainswitch);
                onStop();


            }
        }, 10000);




    }

    private void sendEmail() {


        String code = randomAlphaNumeric(10);

        header.setText("REF:"+code);



        //Creating SendMail object
        SendMail sm = new SendMail(this, loginLocalDAO.getLogin().getEmail(), "MEDIASSIST APPOINTMENT REF: "+code, "" +
                "Name : "+loginLocalDAO.getLogin().getUsername()+
                "\nTime : "+time+
                "\nDate : " + date+
                "\nGP : " + gp+
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "");

        //Executing sendmail to send email
        sm.execute();

        final JSONObject jsonbody = new JSONObject();

        try {
            jsonbody.put("ref",code);
            jsonbody.put("name",loginLocalDAO.getLogin().getUsername());
            jsonbody.put("date",date);
            jsonbody.put("time",time);

        }
        catch (JSONException e){
            e.printStackTrace();
        }
        volleyRequestHandler.stringrequestdelete("bookigs/delete/"+loginLocalDAO.getLogin().getId(),new Response.Listener<String>()
                       {
                           @Override
                           public void onResponse(String response) {
                               Toast.makeText(getApplicationContext(),"DELETED: "+response,Toast.LENGTH_SHORT).show();
                               if(volleyRequestHandler.hasActiveInternetConnection()){
                                   volleyRequestHandler.postRequest("bookigs/add/"+loginLocalDAO.getLogin().getId(),jsonbody,listenerResponse,listenerError);
                               } else{
                                   Log.i("No Connection","NO  INTERNET ACCESS");
                               }
                           }
                       },new Response.ErrorListener()
                       {
                           @Override
                           public void onErrorResponse(VolleyError error) {
                               // error.

                           }
                       });





        editTextMessage.setText("MEDIASSIST APPOINTMENT REF: "+code+
                "\nPlease Keep eye on your email in order to get appointment confirmation.");
        editTextMessage.setEnabled(false);
    }


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
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


}
