package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.SlideAdapter;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.LoginActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.PersonalProfile;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile.GivenAddress;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile.GivenContact;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile.PersonProfile;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.ProfileRemoteDAO;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private SlideAdapter slideAdapter;
    private TextView nav_header_title;
    private ProfileRemoteDAO profileRemoteDAO;
    private PersonalProfile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // String profile= getIntent().getStringExtra("Profile");

//receive
        profile = (PersonalProfile) getIntent().getSerializableExtra("Profile");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        slideAdapter = new SlideAdapter(this);
        viewPager.setAdapter(slideAdapter);
        nav_header_title = (TextView)  findViewById(R.id.nav_header_profile);
       profileRemoteDAO = new ProfileRemoteDAO(this);
        slideAdapter.setLst_descriptions(profile.toString());

        //nav_header_title.setText();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    @Override
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

   GivenAddress address = new GivenAddress("16 Summer Street","Dublin","Dublin 03","TW20D03");
    GivenContact contact = new GivenContact("0892-324","421321","danishali158@gmail.com");
   PersonProfile personalprofile = new PersonProfile("123",contact,"A+ve","Danish Ali","Male","30/09/1994",address);

}