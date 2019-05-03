package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Mailer.Mail;

public class Booking extends AppCompatActivity {

    private TextView mTextMessage;
    private CalendarView calendarView;
    private Button date;
    String datetext;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customcalender);

        mTextMessage = (TextView) findViewById(R.id.message);
        calendarView = (CalendarView)findViewById(R.id.calenderid);
        date = (Button) findViewById(R.id.date);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                datetext = (i1 + 1) + "/" + i2 + "/" + i;
                //Toast.makeText(getApplicationContext(), date,Toast.LENGTH_SHORT).show();


            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (datetext.equals(null)) {
                    Toast.makeText(getApplicationContext(), "Please Select Date First", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent myIntent = new Intent(Booking.this, Mail.class);
                    Booking.this.startActivity(myIntent);
                }

            }
        });



    }

}
