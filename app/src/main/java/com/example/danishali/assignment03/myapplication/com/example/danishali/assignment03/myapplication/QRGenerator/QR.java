package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.LoginActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.SignupActvity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.PersonalProfile;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.RequiresApi;

public class QR extends AppCompatActivity {

    private TextView qrmessage;
    private LoginLocalDAO loginLocalDAO;
    private VolleyRequestHandler volleyRequestHandler;
    private PersonalProfile personalProfile;
    private String nigrokdomain;
    private TapBarMenu tapBarMenu;
    private ImageView qr;
    private ImageView bookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volleyRequestHandler = new VolleyRequestHandler(this);
        personalProfile = new PersonalProfile();
        loginLocalDAO = new LoginLocalDAO(this);
        nigrokdomain = this.getResources().getString(R.string.domain);


        //////////////////////////////////////
        volleyRequestHandler.getRequest("profile/find/"+loginLocalDAO.getLogin().getId(),new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    personalProfile.setId(response.getString("id"));
                    personalProfile.setDateofbirth(response.getString("eircode"));
                    personalProfile.setMobile(response.getString("mobile"));
                    personalProfile.setEmail(response.getString("email"));
                    personalProfile.setHome(response.getString("home"));
                    personalProfile.setBloodgroup(response.getString("bloodgroup"));
                    personalProfile.setGender(response.getString("gender"));
                    personalProfile.setEircode(response.getString("dateofbirth"));
                    personalProfile.setToken(response.getString("tokengiven"));
                    personalProfile.setName(response.getString("name"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                loginLocalDAO.resetDb();
//                Log.i("qwe",personalProfile.getEmail());
                Login login = new Login(personalProfile.getId(), personalProfile.getName(), personalProfile.getToken(), personalProfile.getEmail());
                loginLocalDAO.insertLogin(login);

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
        /////////////////////////////////////
        setContentView(R.layout.activity_qrgenerator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        qrmessage = (TextView) findViewById(R.id.qrmessage);
        loginLocalDAO = new LoginLocalDAO(this);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);
        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenuqr);
        Intent intent = getIntent();

        final AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(QR.this);
        View profileview = getLayoutInflater().inflate(R.layout.qr_pinpad,null);
        final EditText pin_pass = profileview.findViewById(R.id.pin_pass);
        profile_dialogue.setView(profileview);
        profile_dialogue.setPositiveButton("Access", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d("QRCHECK", "onClick: "+pin_pass.getText().toString());
                if(pin_pass.getText().toString().equals("1234")) {
                    ImageView qr = (ImageView) findViewById(R.id.qr);
                    String text = nigrokdomain+"qrtoken?token="+loginLocalDAO.getLogin().getToken();// Whatever you need to encode in the QR code
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qr.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 1900, 1900, false));
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                    new CountDownTimer(10000, 1000) {

                        public void onTick(long millisUntilFinished) {


                            qrmessage.setText(getApplicationContext().getResources().getString(R.string.QR_messageOne)+" "+millisUntilFinished / 1000+" "+getApplicationContext().getResources().getString(R.string.QR_messageTwo));

                        }

                        @Override
                        public void onFinish() {

                        }


                    }.start();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            Log.i("Loginqr","ads"+loginLocalDAO.getLogin().getEmail());
                            Intent i = new Intent(QR.this, MainActivity.class);
                            i.putExtra("Profile",loginLocalDAO.getLogin().getEmail());
                            startActivity(i);
                            finish();
                        }
                    }, 10000);
                }
                else {
                    Toast.makeText(QR.this, "Access Denied", Toast.LENGTH_SHORT).show();
                }

            }
        });
        AlertDialog dialog = profile_dialogue.create();
        dialog.show();

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(QR.this, QR.class);
                mainswitch.putExtra("ID",personalProfile.getId());
                QR.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(QR.this, Booking.class);
                mainswitch.putExtra("ID",personalProfile.getId());
                QR.this.startActivity(mainswitch);
                onStop();

            }
        });


        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });



    }

}
