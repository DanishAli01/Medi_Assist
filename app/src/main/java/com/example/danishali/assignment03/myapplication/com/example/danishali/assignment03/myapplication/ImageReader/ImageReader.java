package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.ImageReader;



import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.SignupActvity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.insurance;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ImageReader extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private final int RequestCameraPermissionID = 1001;
    private SurfaceView surfaceView_signup;
    private TextView textView_signup_medicalcard;
    private CameraSource cameraSource;
    private VolleyRequestHandler volleyRequestHandler;
    private Button finalbutton;
    private String helper;
    private String endpoint;
    private LoginLocalDAO loginLocalDAO;
    private insurance Insurance;
    private String person_id;
    private String idendpoint;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)  {
        switch (requestCode){
            case RequestCameraPermissionID:
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    else{
                        try {
                            cameraSource.start(surfaceView_signup.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfaceview);

        final Intent intent = getIntent();
        person_id = intent.getStringExtra("email");
        surfaceView_signup = (SurfaceView) findViewById(R.id.surfaceview_medicalcard);
        textView_signup_medicalcard = (TextView) findViewById(R.id.textview_medicalcard);
        finalbutton = (Button) findViewById(R.id.btn_scan_qr);
        volleyRequestHandler = new VolleyRequestHandler(this);
        endpoint = this.getResources().getString(R.string.endpointinsurance);
        loginLocalDAO = new LoginLocalDAO(this);
        Insurance = new insurance();
        idendpoint = this.getResources().getString(R.string.findbyemial);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational())
            Log.e("!RECOGNIZER WORKING", "Detector dependencies are not yet available");

        else {

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(8.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            surfaceView_signup.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ImageReader.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID
                            );
                            return;
                        }
                        cameraSource.start(surfaceView_signup.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                    cameraSource.stop();

                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();

                    if(items.size() != 0){
                        textView_signup_medicalcard.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();

                                for (int i = 0; i <items.size() ; i++) {

                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");

                                }

                                textView_signup_medicalcard.setText(stringBuilder.toString());
                                helper = stringBuilder.toString();

                            }
                        });
                    }

                }
            });

        }
        finalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(ImageReader.this);
                View profileview = getLayoutInflater().inflate(R.layout.insurancecam,null);
                final EditText rawtext = (EditText) profileview.findViewById(R.id.input_raw);
                final EditText number = (EditText) profileview.findViewById(R.id.input_id);
                final EditText company = (EditText) profileview.findViewById(R.id.input_company);
                profile_dialogue.setView(profileview);
                rawtext.setText(helper);
                profile_dialogue.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        volleyRequestHandler.stringrequest(idendpoint+"/"+person_id,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {



                                Insurance.setNumber(number.getText().toString());
                                Insurance.setCompany(company.getText().toString());

                                JSONObject jsonbody = new JSONObject();
                                try {
                                    jsonbody.put("number",Insurance.getNumber());
                                    jsonbody.put("company",Insurance.getCompany());


                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }


                                if(volleyRequestHandler.hasActiveInternetConnection()){
                                    volleyRequestHandler.postRequest(endpoint+response,jsonbody, listenerResponse, listenerError);
                                } else{
                                    Log.i("No Connection","NO  INTERNET ACCESS");
                                }


                            }
                        },edl);



                        Toast toast=Toast.makeText(getApplicationContext(),"Insurance Details: "+number.getText().toString()+" "+company.getText().toString(),Toast.LENGTH_SHORT);
                        toast.show();
                        Intent mainswitch = new Intent(ImageReader.this, SignupActvity.class);
                        ImageReader.this.startActivity(mainswitch);







                    }
                });
                AlertDialog dialog = profile_dialogue.create();
                dialog.show();
            }
        });

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

    Response.ErrorListener edl = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };


}