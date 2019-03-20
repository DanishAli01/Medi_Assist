package com.example.danishali.assignment03.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class ImageReader extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private final int RequestCameraPermissionID = 1001;
    private SurfaceView surfaceView_signup;
    private TextView textView_signup_medicalcard;
    private CameraSource cameraSource;
    private String helper = " ";
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

                AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(ImageReader.this);
                View profileview = getLayoutInflater().inflate(R.layout.surfaceview,null);
                surfaceView_signup = (SurfaceView) findViewById(R.id.surfaceview_medicalcard);
                textView_signup_medicalcard = (TextView) findViewById(R.id.textview_medicalcard);
                profile_dialogue.setView(profileview);
                AlertDialog dialog = profile_dialogue.create();
                dialog.show();

                TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if (!textRecognizer.isOperational())
                    Log.e("!RECOGNIZER WORKING", "Detector dependencies are not yet available");

                else {

                    cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                            .setFacing(CameraSource.CAMERA_FACING_BACK)
                            .setRequestedPreviewSize(1280, 1024)
                            .setRequestedFps(2.0f)
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
                                        helper = stringBuilder.toString();
                                        Toast.makeText(ImageReader.this, "Got it :"+helper, Toast.LENGTH_SHORT).show();
                                        Intent mainswitch = new Intent(ImageReader.this,SignupActvity.class);
                                        ImageReader.this.startActivity(mainswitch);
                                        //textView_signup_medicalcard.setText(stringBuilder.toString());

                                    }
                                });
                            }

                        }
                    });

                }

            }

}
