package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.TimePicker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.ChangeEmail.ConfrimEmail;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.GPList.GpList;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Vitals.Vitals;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

public class TimePick extends AppCompatActivity {

    private TextView mTextMessage;
    private TimePicker picker;
    private Button btnGet;
    private TapBarMenu tapBarMenu;
    private ImageView qr;
    private ImageView bookings;
    private LoginLocalDAO loginLocalDAO;
    private String id;
    private String date;
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pick);
        picker=(TimePicker)findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);
        btnGet=(Button)findViewById(R.id.btn_login);
        loginLocalDAO = new LoginLocalDAO(this);
        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenuMedi);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        date = intent.getStringExtra("date");

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = picker.getHour();
                    minute = picker.getMinute();
                }
                else{
                    hour = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }


                time = + hour +":"+ minute+" "+am_pm;
                Toast toast=Toast.makeText(getApplicationContext(),"Selected Date: "+ hour +":"+ minute+" "+am_pm,Toast.LENGTH_SHORT);
//                Toast toast=Toast.makeText(getApplicationContext(),id+" "+date,Toast.LENGTH_SHORT);
                toast.show();

                Intent mainswitch = new Intent(TimePick.this, GpList.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                mainswitch.putExtra("date",date);
                mainswitch.putExtra("time",time);
                TimePick.this.startActivity(mainswitch);
                onStop();

            }
        });


        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(TimePick.this, QR.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                TimePick.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(TimePick.this, Booking.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                TimePick.this.startActivity(mainswitch);
                onStop();

            }
        });




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
            Intent mainswitch = new Intent(TimePick.this, Vitals.class);
            TimePick.this.startActivity(mainswitch);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
