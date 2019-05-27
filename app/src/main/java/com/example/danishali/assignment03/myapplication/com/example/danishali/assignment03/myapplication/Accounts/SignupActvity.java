package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.ImageReader.ImageReader;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile.MedicalHistory;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile.Medication;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.PersonalProfile.PersonalProfile;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.SecurePassword.SecurePassword;
import org.json.JSONException;
import org.json.JSONObject;

public class SignupActvity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private CardView profile;
    private CardView medicalcard;
    private PersonalProfile personalProfile;
    private SecurePassword securePassword;
    private VolleyRequestHandler volleyRequestHandler;
    private String endpoint;
    private String idendpoint;
    private String idpass;
    private JSONObject jsonbody;
    private  CardView stethoscope;
    private MedicalHistory medicalHistory;
    private  CardView medication;
    private String medicationpass;
    private Medication medics;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);

        volleyRequestHandler = new VolleyRequestHandler(this);
        profile = (CardView) findViewById(R.id.profile);
        stethoscope = (CardView) findViewById(R.id.stethoscope);
        medicalcard = (CardView)findViewById(R.id.medicalcard);
        personalProfile = new PersonalProfile();
        medicalHistory = new MedicalHistory();
        securePassword = new SecurePassword();
        endpoint = this.getResources().getString(R.string.addprofileendpoint);
        idendpoint = this.getResources().getString(R.string.getidendpoint);
        idpass = this.getResources().getString(R.string.idpass);
        medicationpass = this.getResources().getString(R.string.medicationpass);
        jsonbody = new JSONObject();
        medication = (CardView)findViewById(R.id.medication);
        medics = new Medication();



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(SignupActvity.this);
                View profileview = getLayoutInflater().inflate(R.layout.profile_dialogue,null);
                final EditText name = profileview.findViewById(R.id.input_name);
                final EditText email = profileview.findViewById(R.id.input_email);
                final EditText password = profileview.findViewById(R.id.input_password);
                final EditText confirm_password = profileview.findViewById(R.id.password_confirm);
                final EditText home = profileview.findViewById(R.id.input_home);
                final EditText mobile = profileview.findViewById(R.id.input_mobile);
                final EditText bloodgroup = profileview.findViewById(R.id.input_bloodgroup);
                final EditText eircode = profileview.findViewById(R.id.input_eircode);
                final EditText gender = profileview.findViewById(R.id.input_gender);
                final EditText dob = profileview.findViewById(R.id.input_dob);

                profile_dialogue.setView(profileview);
                profile_dialogue.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        personalProfile.setName(name.getText().toString());
                        personalProfile.setEmail(email.getText().toString());
                        try {
                            personalProfile.setId(securePassword.FOLD(password.getText().toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        personalProfile.setHome(home.getText().toString());
                        personalProfile.setMobile(mobile.getText().toString());
                        personalProfile.setBloodgroup(bloodgroup.getText().toString());
                        personalProfile.setDateofbirth(eircode.getText().toString());
                        personalProfile.setGender(gender.getText().toString());
                        personalProfile.setEircode(dob.getText().toString());


                        try {
                            jsonbody.put("name",personalProfile.getName());
                            jsonbody.put("dateofbirth",personalProfile.getDateofbirth());
                            jsonbody.put("password",personalProfile.getId());
                            jsonbody.put("gender",personalProfile.getGender());
                            jsonbody.put("bloodgroup",personalProfile.getBloodgroup());
                            jsonbody.put("home",personalProfile.getHome());
                            jsonbody.put("email",personalProfile.getEmail());
                            jsonbody.put("mobile",personalProfile.getMobile());
                            jsonbody.put("eircode",personalProfile.getEircode());
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }


                        Log.d("QRCHECK", "onClick: "+personalProfile.toString());

                        if(volleyRequestHandler.hasActiveInternetConnection()){
                            volleyRequestHandler.postRequest(endpoint,jsonbody, listenerResponse, listenerError);
                        } else{
                            Log.i("No Connection","NO  INTERNET ACCESS");
                        }

                    }
                });
                AlertDialog dialog = profile_dialogue.create();
                dialog.show();
            }
        });

        stethoscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(SignupActvity.this);
                View profileview = getLayoutInflater().inflate(R.layout.healthrecord_dialogue,null);
                final EditText illness = (EditText) profileview.findViewById(R.id.input_illness);
                final EditText date = (EditText) profileview.findViewById(R.id.input_date);
                final EditText details = (EditText) profileview.findViewById(R.id.input_details);


                profile_dialogue.setView(profileview);
                profile_dialogue.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    volleyRequestHandler.stringrequest(idendpoint+"/"+personalProfile.getMobile(),new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                medicalHistory.setId(response);
                                medicalHistory.setIllness(illness.getText().toString());
                                medicalHistory.setDetails(details.getText().toString());
                                medicalHistory.setDate(date.getText().toString());

                                try {
                                    jsonbody.put("map",medicalHistory.getId());
                                    jsonbody.put("illness",medicalHistory.getIllness());
                                    jsonbody.put("date",medicalHistory.getDate());
                                    jsonbody.put("details",medicalHistory.getDetails());

                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }

                                if(volleyRequestHandler.hasActiveInternetConnection()){
                                    volleyRequestHandler.postRequest(idpass+medicalHistory.getId(),jsonbody, listenerResponse, listenerError);
                                } else{
                                    Log.i("No Connection","NO  INTERNET ACCESS");
                                }


                            }
                        },edl);





                    }
                });
                AlertDialog dialog = profile_dialogue.create();
                dialog.show();

            }
        });

        medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder profile_dialogue = new AlertDialog.Builder(SignupActvity.this);
                View profileview = getLayoutInflater().inflate(R.layout.medication_dialogue,null);
                final EditText medicine = (EditText) profileview.findViewById(R.id.input_medication_name);
                final EditText date = (EditText) profileview.findViewById(R.id.input_medication_date);
                final EditText prescribed_by = (EditText) profileview.findViewById(R.id.input_medication_prescribed_by);
                final EditText treatment_for = (EditText) profileview.findViewById(R.id.input_medication_treatmentfor);


                profile_dialogue.setView(profileview);
                profile_dialogue.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        volleyRequestHandler.stringrequest(idendpoint+"/"+personalProfile.getMobile(),new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                medics.setMap(response);
                                medics.setDate(date.getText().toString());
                                medics.setName(medicine.getText().toString());
                                medics.setTreatment_for(treatment_for.getText().toString());
                                medics.setPrescribed_by(prescribed_by.getText().toString());

                                try {
                                    jsonbody.put("map",medics.getMap());
                                    jsonbody.put("date",medics.getDate());
                                    jsonbody.put("treatment_for",medics.getTreatment_for());
                                    jsonbody.put("prescribed_by",medics.getPrescribed_by());

                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }

                                if(volleyRequestHandler.hasActiveInternetConnection()){
                                    volleyRequestHandler.postRequest(medicationpass+medics.getMap(),jsonbody, listenerResponse, listenerError);
                                } else{
                                    Log.i("No Connection","NO  INTERNET ACCESS");
                                }


                            }
                        },edl);





                    }
                });
                AlertDialog dialog = profile_dialogue.create();
                dialog.show();

            }
        });


        medicalcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainswitch = new Intent(SignupActvity.this, ImageReader.class);
                SignupActvity.this.startActivity(mainswitch);

            }
        });



//
//        nametextview = (EditText) findViewById(R.identity.input_name);
//        emailtextview = (EditText) findViewById(R.identity.input_email);
//        passwordtextview = (EditText) findViewById(R.identity.input_password);
//        signupbutton = (Button) findViewById(R.identity.btn_signup);
//        loginpath = (TextView) findViewById(R.identity.link_login);
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
