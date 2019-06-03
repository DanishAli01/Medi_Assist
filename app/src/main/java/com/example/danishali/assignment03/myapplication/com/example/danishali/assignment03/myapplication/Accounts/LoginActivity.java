package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.PersonalProfile;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.ProfileRemoteDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.SecurePassword.SecurePassword;
import com.google.android.gms.vision.L;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.RequiresApi;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private  static final int REQUEST_SWITCH = 0;
    private EditText edittextview;
    private EditText passwordtextview;
    private Button loginbutton;
    private TextView signuppath;
    private ProfileRemoteDAO profileRemoteDAO;
    private LoginLocalDAO loginLocalDAO;
    private VolleyRequestHandler volleyRequestHandler;
    private String loginendpointcheck;
    private SecurePassword securePassword;
    private String afterLogingetprofile_endpoint;
    private PersonalProfile personalProfile;
    private int countfailedlogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        edittextview = (EditText) findViewById(R.id.input_login_email);
        passwordtextview = (EditText) findViewById(R.id.input_login_password);
        loginbutton = (Button) findViewById(R.id.btn_login);
        signuppath = (TextView) findViewById(R.id.link_signup);
        profileRemoteDAO = new ProfileRemoteDAO(this);
        loginLocalDAO = new LoginLocalDAO(this);
        volleyRequestHandler = new VolleyRequestHandler(this);
        loginendpointcheck = this.getResources().getString(R.string.logincheck);
        afterLogingetprofile_endpoint = this.getResources().getString(R.string.afterLogingetprofile_endpoint);
        personalProfile = new PersonalProfile();
        countfailedlogin = 0;



        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(countfailedlogin<3) {
                    login();
                }
                else {
                    final AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(LoginActivity.this);
                    View profileview = getLayoutInflater().inflate(R.layout.forgot_password_window,null);

                    final TextView forgot_DOB = profileview.findViewById(R.id.forgot_password_dateofbirth);
                    final TextView forgot_email = profileview.findViewById(R.id.forgot_password_email);
                    final TextView forgot_pin = profileview.findViewById(R.id.forgot_password_pin);
                    profile_dialogue.setView(profileview);


                    profile_dialogue.setPositiveButton("Access", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {




                            volleyRequestHandler.getRequest(afterLogingetprofile_endpoint+forgot_email.getText().toString(),new Response.Listener<JSONObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {


                                        personalProfile.setHome(response.getString("home"));
                                        personalProfile.setDateofbirth(response.getString("eircode"));
                                        personalProfile.setPassword(response.getString("password"));

                                        String[] parts = personalProfile.getHome().split("-");
                                        String part2 = parts[1]; // 034556

                                        Log.i("pin",""+part2);
                                        Log.i("pin",""+personalProfile);


                                            Log.i("pin","done");
                                            passwordtextview.setText(securePassword.UNFOLD(personalProfile.getPassword()));
                                            countfailedlogin=0;


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


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


                        }
                    });
                    AlertDialog dialog = profile_dialogue.create();
                    dialog.show();


                }

            }
        });

        signuppath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signupswitch = new Intent(LoginActivity.this, SignupActvity.class);
                LoginActivity.this.startActivity(signupswitch);

            }
        });

    }

    public void login(){

        if(!validate()){
            onLoginFailed();
            Log.e("Invaild Login","Invaild Login");
            edittextview.setError("Invalid Credentials");
            passwordtextview.setError("Invalid Credentials");
            return;
        }

        loginbutton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        volleyRequestHandler.stringrequest(loginendpointcheck+edittextview.getText().toString(),new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    if( passwordtextview.getText().toString().equals(securePassword.UNFOLD(response))){
                        onLoginSuccess();
                    }


                    else onLoginFailed();

                } catch (Exception e) {
                    e.printStackTrace();
                    edittextview.setError(""+e);
                }


            }
        },edl);


        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                progressDialog.dismiss();
            }
        }, 3000);


    }


    public boolean validate() {
        boolean valid = true;

        String email = edittextview.getText().toString();
        String password = passwordtextview.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edittextview.setError("enter a valid email address");
            valid = false;
        } else {
            edittextview.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordtextview.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordtextview.setError(null);
        }

        return valid;
    }


    public void onLoginFailed() {
        countfailedlogin +=1;
    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        edittextview.setError("Invalid Credentials");
        passwordtextview.setError("Invalid Credentials");
        loginbutton.setEnabled(true);
    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {


        Intent mainswitch = new Intent(LoginActivity.this, MainActivity.class);
        mainswitch.putExtra("Profile",edittextview.getText().toString());
        LoginActivity.this.startActivity(mainswitch);
        loginbutton.setEnabled(true);

    }


    protected void onStart() {
        super.onStart();

    }

Response.ErrorListener edl = new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {

        Log.e("Volley Error ",""+error);
    }
};

}
