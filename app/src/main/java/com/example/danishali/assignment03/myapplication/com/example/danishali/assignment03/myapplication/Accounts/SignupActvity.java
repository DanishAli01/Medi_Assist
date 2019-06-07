package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.ImageReader.ImageReader;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.Login;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.MedicalHistory;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.Medication;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.PersonalProfile;
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
    private Login login;
    private LoginLocalDAO loginLocalDAO;






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
        loginLocalDAO = new LoginLocalDAO(this);
        loginLocalDAO.resetDb();



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
                        login = new Login(randomAlphaNumeric(9),personalProfile.getMobile(),randomAlphaNumeric(9),personalProfile.getEmail());
                        loginLocalDAO.insertLogin(login);

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
                    volleyRequestHandler.stringrequest(idendpoint+loginLocalDAO.getLogin().getUsername(),new Response.Listener<String>() {
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
                        volleyRequestHandler.stringrequest(idendpoint+loginLocalDAO.getLogin().getUsername(),new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                medics.setMap(response);
                                medics.setDate(date.getText().toString());
                                medics.setName(medicine.getText().toString());
                                medics.setTreatment_for(treatment_for.getText().toString());
                                medics.setPrescribed_by(prescribed_by.getText().toString());

                                try {
                                    jsonbody.put("map",medics.getMap());
                                    jsonbody.put("name",medics.getName());
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
                mainswitch.putExtra("email",loginLocalDAO.getLogin().getEmail());
                SignupActvity.this.startActivity(mainswitch);


            }
        });


    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
            loginLocalDAO.resetDb();
        Toast toast=Toast.makeText(getApplicationContext(),"SESSION LOST: Start Signup process Again....",Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

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


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }


}
