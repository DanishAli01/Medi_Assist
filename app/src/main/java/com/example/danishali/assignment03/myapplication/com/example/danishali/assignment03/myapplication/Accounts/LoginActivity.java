package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts;
//My Push & Commit Check
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.ProfileRemoteDAO;

import org.json.JSONObject;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private  static final int REQUEST_SWITCH = 0;
    private EditText edittextview;
    private EditText passwordtextview;
    private Button loginbutton;
    private TextView signuppath;
    private ProfileRemoteDAO profileRemoteDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        edittextview = (EditText) findViewById(R.id.input_email);
        passwordtextview = (EditText) findViewById(R.id.input_password);
        loginbutton = (Button) findViewById(R.id.btn_login);
        signuppath = (TextView) findViewById(R.id.link_signup);

        profileRemoteDAO = new ProfileRemoteDAO(this);


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                   profileRemoteDAO.profileRequestHandler();
                    Toast.makeText(LoginActivity.this, "Got it :", Toast.LENGTH_LONG).show();
//                    Toast.makeText(LoginActivity.this, "Got it :"+ SecurePassword.FOLD("Dansiha"), Toast.LENGTH_LONG).show();
                   // Toast.makeText(LoginActivity.this, "Got it :"+SecurePassword.UNFOLD(""+SecurePassword.FOLD("Dansiha")), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

               login();
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
            return;
        }

        loginbutton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        String emial = edittextview.getText().toString();
        String password = passwordtextview.getText().toString();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoginSuccess();
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
    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginbutton.setEnabled(true);
    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Intent mainswitch = new Intent(LoginActivity.this, MainActivity.class);
        mainswitch.putExtra("Profile", (Serializable) profileRemoteDAO.getProfile());
        LoginActivity.this.startActivity(mainswitch);
        loginbutton.setEnabled(true);
    }
}