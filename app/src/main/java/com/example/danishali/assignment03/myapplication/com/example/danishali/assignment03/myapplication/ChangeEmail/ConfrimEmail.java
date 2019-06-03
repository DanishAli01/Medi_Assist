package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.ChangeEmail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Mailer.Mail;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.TimePicker.TimePick;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Vitals.Vitals;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

public class ConfrimEmail extends AppCompatActivity {

    private TextView mTextMessage;
    private EditText name;
    private EditText passcode;
    private EditText confirm_passcode;
    private Button btn_login;
    private LoginLocalDAO loginLocalDAO;
    private TapBarMenu tapBarMenu;
    private ImageView qr;
    private ImageView bookings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrim_email);

        name = (EditText) findViewById(R.id.input_name);
        passcode = (EditText) findViewById(R.id.input_email);
        confirm_passcode = (EditText) findViewById(R.id.input_password_confirm);
        loginLocalDAO = new LoginLocalDAO(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenuconfirmemail);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);
        loginLocalDAO = new LoginLocalDAO(this);

        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");
        final String time = intent.getStringExtra("time");


        name.setText(loginLocalDAO.getLogin().getUsername());
        passcode.setText(loginLocalDAO.getLogin().getEmail());
        name.setEnabled(false);
        passcode.setEnabled(false);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast=Toast.makeText(getApplicationContext(),passcode.getText().toString()+"--->"+confirm_passcode.getText().toString()+" ",Toast.LENGTH_SHORT);
                toast.show();
                if(passcode.getText().toString().equals(confirm_passcode.getText().toString())){

                   // Toast toast=Toast.makeText(getApplicationContext(),loginLocalDAO.getLogin().getEmail(),Toast.LENGTH_SHORT);
//                Toast toast=Toast.makeText(getApplicationContext(),id+" "+date,Toast.LENGTH_SHORT);
                   // toast.show();
                    Intent mainswitch = new Intent(ConfrimEmail.this, Mail.class);
                    mainswitch.putExtra("date",date);
                    mainswitch.putExtra("time",time);
                    mainswitch.putExtra("email",passcode.toString());
                    ConfrimEmail.this.startActivity(mainswitch);
                    onStop();
                }
                else {
                    confirm_passcode.setError("Email Mismatched");
                }
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

                Intent mainswitch = new Intent(ConfrimEmail.this, QR.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                ConfrimEmail.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(ConfrimEmail.this, Booking.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                ConfrimEmail.this.startActivity(mainswitch);
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
            Intent mainswitch = new Intent(ConfrimEmail.this, Vitals.class);
            ConfrimEmail.this.startActivity(mainswitch);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
