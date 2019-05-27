package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.ChangeEmail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.danishali.assignment03.myapplication.R;

public class ConfrimEmail extends AppCompatActivity {

    private TextView mTextMessage;
    private EditText name;
    private EditText passcode;
    private EditText confirm_passcode;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.identity.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.identity.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.identity.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrim_email);

        name = (EditText) findViewById(R.id.input_name);
        passcode = (EditText) findViewById(R.id.input_email);
        confirm_passcode = (EditText) findViewById(R.id.input_password_confirm);

        name.setEnabled(false);
        passcode.setEnabled(false);




    }

}
