package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Mailer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.TimePicker.TimePick;

public class Mail extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button buttonSend;
    private LoginLocalDAO loginLocalDAO;
    String time;
    String date;
    String email;

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


        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        loginLocalDAO = new LoginLocalDAO(this);
//        buttonSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////               // Toast toast=Toast.makeText(getApplicationContext(),time+" "+date+" "+email,Toast.LENGTH_SHORT);
////                Toast toast=Toast.makeText(getApplicationContext(),randomAlphaNumeric(10)+" "+date,Toast.LENGTH_SHORT);
////                toast.show();
//                sendEmail();
//            }
//        });

        sendEmail();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainswitch = new Intent(Mail.this, MainActivity.class);
                mainswitch.putExtra("Profile",loginLocalDAO.getLogin().getEmail());
                Mail.this.startActivity(mainswitch);
                onStop();


            }
        }, 5000);

    }

    private void sendEmail() {
        //Getting content for email
//        String email = editTextEmail.getText().toString().trim();
//        String subject = editTextSubject.getText().toString().trim();
//        String message = editTextMessage.getText().toString().trim();
        String code = randomAlphaNumeric(10);

        //Creating SendMail object
        SendMail sm = new SendMail(this, loginLocalDAO.getLogin().getEmail(), "MEDIASSIST APPOINTMENT REF: "+code, "" +
                "<b>Name : "+loginLocalDAO.getLogin().getUsername()+"</b>"+
                "\nTime : "+time+
                "\nDate : " + date+
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "");

        //Executing sendmail to send email
        sm.execute();

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

}
