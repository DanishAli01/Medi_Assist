package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.LoginActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.GPList.GpList;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Medications.medication_listview_activity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.TimePicker.TimePick;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Vitals.Vitals;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

public class Booking extends AppCompatActivity {

    private TextView mTextMessage;
    private CalendarView calendarView;
    private Button date;
    String datetext;
    private TapBarMenu tapBarMenu;
    private ImageView qr;
    private ImageView bookings;
    private LoginLocalDAO loginLocalDAO;

    private ImageView logout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customcalender);

        mTextMessage = (TextView) findViewById(R.id.message);
        calendarView = (CalendarView)findViewById(R.id.calenderid);
        date = (Button) findViewById(R.id.date);
        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenubooking);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);
        loginLocalDAO = new LoginLocalDAO(this);



        Intent intent = getIntent();
        final Uri data = intent.getData();

        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {


                datetext = (i1 + 1) + "/" + i2 + "/" + i;
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (datetext == null) {
                            Toast.makeText(getApplicationContext(), "Please Select Date First", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast toast=Toast.makeText(getApplicationContext(),"Selected Date: "+datetext,Toast.LENGTH_SHORT);
//                            toast.setMargin(50,50);
                            toast.show();
//                            Intent myIntent = new Intent(Booking.this, GpList.class);
//                            Booking.this.startActivity(myIntent);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent mainswitch = new Intent(Booking.this, TimePick.class);
                                    mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                                    mainswitch.putExtra("date",datetext);
                                    Booking.this.startActivity(mainswitch);
                                    onStop();


                                }
                            }, 4000);
                        }

                    }
                });











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

                Intent mainswitch = new Intent(Booking.this, QR.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                Booking.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(Booking.this, Booking.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                Booking.this.startActivity(mainswitch);
                onStop();

            }
        });

        logout = (ImageView)findViewById(R.id.log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(Booking.this, LoginActivity.class);
                loginLocalDAO.resetDb();
                Booking.this.startActivity(mainswitch);
                onStop();

            }
        });

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarbooking);
//        setSupportActionBar(toolbar);




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
            Intent mainswitch = new Intent(Booking.this, Vitals.class);
            Booking.this.startActivity(mainswitch);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
