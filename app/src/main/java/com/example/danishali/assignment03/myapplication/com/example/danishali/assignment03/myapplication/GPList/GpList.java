package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.GPList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Mailer.Mail;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.Vital;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.TimePicker.TimePick;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Vitals.Vitals;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

public class GpList extends AppCompatActivity {

    private TextView mTextMessage;
    private TapBarMenu tapBarMenu;
    private ImageView qr;
    private ImageView bookings;
    private LoginLocalDAO loginLocalDAO;
    private Intent intent;
    String time,date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp_list);

        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMengplist);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);
        loginLocalDAO = new LoginLocalDAO(this);
        intent = getIntent();

        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");


        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("123LQ - Adam Brayne - DUB 02","123LQ - Adam Brayne - DUB 02","324LA - Marian Yancey - DUB 18","325GQ - Laura Lock - DUB 03","901AM - Natosha Bowley - DUB 06","789PP - Iva Decato - DUB 01");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                Intent mainswitch = new Intent(GpList.this, Mail.class);
                mainswitch.putExtra("time",time);
                mainswitch.putExtra("date",date);
                GpList.this.startActivity(mainswitch);
                onStop();
            }
        });

        mTextMessage = (TextView) findViewById(R.id.message);

        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(GpList.this, QR.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                GpList.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(GpList.this, Booking.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                GpList.this.startActivity(mainswitch);
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
            Intent mainswitch = new Intent(GpList.this, Vitals.class);
            GpList.this.startActivity(mainswitch);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
