package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile.PersonalProfile;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.ProfileRemoteDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Adapters.RecyclerViewAdapter;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.RequiresApi;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private TextView nav_header_title;
    private ProfileRemoteDAO profileRemoteDAO;
    private PersonalProfile personalProfile;
    private ImageView qr;
    private VolleyRequestHandler volleyRequestHandler;
    private String afterLogingetprofile_endpoint;

    FloatingActionButton fab;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<String> arrayList = new ArrayList<>();

    int[] animationList = {R.anim.layout_animation_up_to_down, R.anim.layout_animation_right_to_left, R.anim.layout_animation_down_to_up, R.anim.layout_animation_left_to_right};

    int i = 0;


    TapBarMenu tapBarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        nav_header_title = (TextView)  findViewById(R.id.nav_header_profile);
        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenu);
        qr = (ImageView) findViewById(R.id.qr);
        afterLogingetprofile_endpoint = this.getResources().getString(R.string.afterLogingetprofile_endpoint);
        personalProfile = new PersonalProfile();
        volleyRequestHandler = new VolleyRequestHandler(this);
        volleyRequestHandler.getRequest(afterLogingetprofile_endpoint+getIntent().getStringExtra("Profile"),new Response.Listener<JSONObject>() {
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
                String s = personalProfile.getDateofbirth();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date d = null;
                try {
                    d = sdf.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int date = c.get(Calendar.DATE);
                LocalDate l1 = LocalDate.of(year, month, date);
                LocalDate now1 = LocalDate.now();
                Period diff1 = Period.between(l1, now1);

                recyclerView = findViewById(R.id.recyclerView);
                arrayList.add("Name : " + personalProfile.getName());
                arrayList.add("Gender : " +  personalProfile.getGender());
                arrayList.add("Age : " + diff1.getYears());
                arrayList.add("Date of birth : " +  personalProfile.getDateofbirth());
                arrayList.add("Eircode : "+ personalProfile.getEircode() );
                arrayList.add("Mobile : "+  personalProfile.getMobile() );
                arrayList.add("Home : "+  personalProfile.getHome() );
                arrayList.add("Bloodgroup : "+personalProfile.getBloodgroup()   );
                initAdapter();

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


        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(MainActivity.this, QR.class);
                mainswitch.putExtra("ID",personalProfile.getId());
                MainActivity.this.startActivity(mainswitch);
                onStop();

            }
        });


        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };


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
            Intent myIntent = new Intent(MainActivity.this, QR.class);
            MainActivity.this.startActivity(myIntent);

        } else if (id == R.id.nav_send) {
            Intent myIntent = new Intent(MainActivity.this, Booking.class);
            MainActivity.this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void populateData() {


        arrayList.add("Name : " + "Danish Ali");
        arrayList.add("Gender : " + "Male");
        arrayList.add("Age : " + "25");
        arrayList.add("Date of birth : " + "30/09/1994");
        arrayList.add("Eircode : "+ "D03TW20" );
        arrayList.add("Mobile : "+ "0892-434393" );
        arrayList.add("Home : "+ "01-434393" );
        arrayList.add("Bloodgroup : "+ "A+v" );

    }

    private void initAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void runAnimationAgain() {

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, animationList[i]);

        recyclerView.setLayoutAnimation(controller);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }

    @Override
    protected void onStart() {



        volleyRequestHandler.getRequest(afterLogingetprofile_endpoint+"danishali2158@gmail.co",new Response.Listener<JSONObject>() {
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
                    Log.i("Onstart",personalProfile.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String s = personalProfile.getDateofbirth();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date d = null;
                try {
                    d = sdf.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int date = c.get(Calendar.DATE);
                LocalDate l1 = LocalDate.of(year, month, date);
                LocalDate now1 = LocalDate.now();
                Period diff1 = Period.between(l1, now1);

                recyclerView = findViewById(R.id.recyclerView);
                arrayList.add("Name : " + personalProfile.getName());
                arrayList.add("Gender : " +  personalProfile.getGender());
                arrayList.add("Age : " + diff1.getYears());
                arrayList.add("Date of birth : " +  personalProfile.getDateofbirth());
                arrayList.add("Eircode : "+ personalProfile.getEircode() );
                arrayList.add("Mobile : "+  personalProfile.getMobile() );
                arrayList.add("Home : "+  personalProfile.getHome() );
                arrayList.add("Bloodgroup : "+personalProfile.getBloodgroup()   );
                initAdapter();

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



        super.onStart();
    }

    @Override
    protected void onResume() {
        volleyRequestHandler.getRequest(afterLogingetprofile_endpoint+getIntent().getStringExtra("Profile"),new Response.Listener<JSONObject>() {
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
                    Log.i("OnResume",personalProfile.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String s = personalProfile.getDateofbirth();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date d = null;
                try {
                    d = sdf.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int date = c.get(Calendar.DATE);
                LocalDate l1 = LocalDate.of(year, month, date);
                LocalDate now1 = LocalDate.now();
                Period diff1 = Period.between(l1, now1);

                recyclerView = findViewById(R.id.recyclerView);
                arrayList.add("Name : " + personalProfile.getName());
                arrayList.add("Gender : " +  personalProfile.getGender());
                arrayList.add("Age : " + diff1.getYears());
                arrayList.add("Date of birth : " +  personalProfile.getDateofbirth());
                arrayList.add("Eircode : "+ personalProfile.getEircode() );
                arrayList.add("Mobile : "+  personalProfile.getMobile() );
                arrayList.add("Home : "+  personalProfile.getHome() );
                arrayList.add("Bloodgroup : "+personalProfile.getBloodgroup()   );
                initAdapter();

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
        super.onResume();
    }

    @Override
    protected void onRestart() {
        volleyRequestHandler.getRequest(afterLogingetprofile_endpoint+getIntent().getStringExtra("Profile"),new Response.Listener<JSONObject>() {
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
                    Log.i("OnRestart",personalProfile.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String s = personalProfile.getDateofbirth();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date d = null;
                try {
                    d = sdf.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int date = c.get(Calendar.DATE);
                LocalDate l1 = LocalDate.of(year, month, date);
                LocalDate now1 = LocalDate.now();
                Period diff1 = Period.between(l1, now1);

                recyclerView = findViewById(R.id.recyclerView);
                arrayList.add("Name : " + personalProfile.getName());
                arrayList.add("Gender : " +  personalProfile.getGender());
                arrayList.add("Age : " + diff1.getYears());
                arrayList.add("Date of birth : " +  personalProfile.getDateofbirth());
                arrayList.add("Eircode : "+ personalProfile.getEircode() );
                arrayList.add("Mobile : "+  personalProfile.getMobile() );
                arrayList.add("Home : "+  personalProfile.getHome() );
                arrayList.add("Bloodgroup : "+personalProfile.getBloodgroup()   );
                initAdapter();

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
        super.onRestart();
    }

    @Override
    protected void onPause() {

        volleyRequestHandler.getRequest(afterLogingetprofile_endpoint+getIntent().getStringExtra("Profile"),new Response.Listener<JSONObject>() {
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
                    Log.i("OnRestart",personalProfile.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String s = personalProfile.getDateofbirth();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date d = null;
                try {
                    d = sdf.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int date = c.get(Calendar.DATE);
                LocalDate l1 = LocalDate.of(year, month, date);
                LocalDate now1 = LocalDate.now();
                Period diff1 = Period.between(l1, now1);

                recyclerView = findViewById(R.id.recyclerView);
                arrayList.add("Name : " + personalProfile.getName());
                arrayList.add("Gender : " +  personalProfile.getGender());
                arrayList.add("Age : " + diff1.getYears());
                arrayList.add("Date of birth : " +  personalProfile.getDateofbirth());
                arrayList.add("Eircode : "+ personalProfile.getEircode() );
                arrayList.add("Mobile : "+  personalProfile.getMobile() );
                arrayList.add("Home : "+  personalProfile.getHome() );
                arrayList.add("Bloodgroup : "+personalProfile.getBloodgroup()   );
                initAdapter();

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

        super.onPause();
    }


}
