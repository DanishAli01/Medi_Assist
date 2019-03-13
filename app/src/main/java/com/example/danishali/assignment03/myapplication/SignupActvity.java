package com.example.danishali.assignment03.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActvity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";


    private EditText nametextview;
    private EditText emailtextview;
    private EditText passwordtextview;
    private Button signupbutton;
    private TextView loginpath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);

//
//        nametextview = (EditText) findViewById(R.id.input_name);
//        emailtextview = (EditText) findViewById(R.id.input_email);
//        passwordtextview = (EditText) findViewById(R.id.input_password);
//        signupbutton = (Button) findViewById(R.id.btn_signup);
//        loginpath = (TextView) findViewById(R.id.link_login);
//
//        signupbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signup();
//            }
//        });
//        loginpath.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    public boolean validate(){
//        boolean valid = true;
//
//        String name = nametextview.getText().toString();
//        String email = emailtextview.getText().toString();
//        String password = passwordtextview.getText().toString();
//
//        if (name.isEmpty() || name.length() < 3) {
//            nametextview.setError("at least 3 characters");
//            valid = false;
//        } else {
//            nametextview.setError(null);
//        }
//
//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            emailtextview.setError("enter a valid email address");
//            valid = false;
//        } else {
//            emailtextview.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            passwordtextview.setError("between 4 and 10 alphanumeric characters");
//            valid = false;
//        } else {
//            passwordtextview.setError(null);
//        }
//
//        return valid;
//    }
//
//    public void onSignupSuccess() {
//        signupbutton.setEnabled(true);
//        setResult(RESULT_OK, null);
//        finish();
//    }
//
//    public void onSignupFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//
//        signupbutton.setEnabled(true);
//    }
//
//
//    public void signup() {
//
//        if (!validate()) {
//            onSignupFailed();
//            return;
//        }
//
//        signupbutton.setEnabled(false);
//
//        final ProgressDialog progressDialog = new ProgressDialog(SignupActvity.this,
//                R.style.AppTheme);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();
//
//        String name = nametextview.getText().toString();
//        String email = emailtextview.getText().toString();
//        String password = passwordtextview.getText().toString();
//
//        // TODO: Implement your own signup logic here.
//
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
//    }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
