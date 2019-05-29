package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard;

        import android.content.Intent;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.example.danishali.assignment03.myapplication.R;
        import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Adapters.RecyclerViewAdapter;
        import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
        import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;
        import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
        import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.PersonalProfile;
        import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
        import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
        import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Vitals.Vitals;
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
{

    private PersonalProfile personalProfile;
    private ImageView qr;
    private VolleyRequestHandler volleyRequestHandler;
    private String afterLogingetprofile_endpoint;
    private LoginLocalDAO loginLocalDAO;
    private Login login;
    private ImageView bookings;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private TapBarMenu tapBarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenu);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);
        afterLogingetprofile_endpoint = this.getResources().getString(R.string.afterLogingetprofile_endpoint);
        personalProfile = new PersonalProfile();
        volleyRequestHandler = new VolleyRequestHandler(this);
        loginLocalDAO = new LoginLocalDAO(this);


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
                arrayList.add("Email : " +  personalProfile.getEmail());
                arrayList.add("Age : " + diff1.getYears());
                arrayList.add("Date of birth : " +  personalProfile.getDateofbirth());
                arrayList.add("Eircode : "+ personalProfile.getEircode() );
                arrayList.add("Mobile : "+  personalProfile.getMobile() );
                arrayList.add("Home : "+  personalProfile.getHome() );
                arrayList.add("Bloodgroup : "+personalProfile.getBloodgroup()   );
                initAdapter();
                loginLocalDAO.resetDb();
                Log.i("qwe",personalProfile.getEmail());
                login = new Login(personalProfile.getId(),personalProfile.getName(),personalProfile.getToken(),personalProfile.getEmail());
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


        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(MainActivity.this, QR.class);
                mainswitch.putExtra("ID",personalProfile.getId());
                MainActivity.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(MainActivity.this, Booking.class);
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_heart) {
            Log.i("Heart", loginLocalDAO.getLogin().toString());
            Intent mainswitch = new Intent(MainActivity.this, Vitals.class);
            MainActivity.this.startActivity(mainswitch);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
