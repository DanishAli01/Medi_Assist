package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.LoginActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.SignupActvity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(QR.this);
                View profileview = getLayoutInflater().inflate(R.layout.qr_pinpad,null);
                final EditText pin_pass = profileview.findViewById(R.id.pin_pass);
                profile_dialogue.setView(profileview);
                profile_dialogue.setPositiveButton("Access", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("QRCHECK", "onClick: "+pin_pass.getText().toString());
                        if(pin_pass.getText().toString().equals("1234")) {
                            ImageView qr = (ImageView) findViewById(R.id.qr);
                            String text = "http://bb39d3f5.ngrok.io/medicalprofilepage/";// Whatever you need to encode in the QR code
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            try {
                                BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                qr.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 1900, 1900, false));
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(QR.this, LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }, 4000);
                        }
                        else {
                            profile_dialogue.setMessage("Access Denied");
                        }

                    }
                });
                AlertDialog dialog = profile_dialogue.create();
                dialog.show();



            }
        });




    }

}
