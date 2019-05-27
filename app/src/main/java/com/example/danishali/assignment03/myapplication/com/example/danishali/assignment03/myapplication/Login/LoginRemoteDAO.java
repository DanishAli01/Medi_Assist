//package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login;
//
//import android.content.Context;
//
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.example.danishali.assignment03.myapplication.R;
//import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// * @author Gurcharn Singh Sikka
// * @version 1.0
// *
// * Object to handle API requests for login
// */
//public class LoginRemoteDAO {
//    private final String endpoint;
//
//    private Context context;
//    private LoginLocalDAO loginLocalDAO;
//    private VolleyRequestHandler volleyRequestHandler;
//    private LoginActivity loginActivity;
//
//    /**
//     * Constructor
//     * @param context
//     */
//    LoginRemoteDAO(Context context){
//        this.endpoint = context.getResources().getString(R.string.login_endpoint);
//        this.context = context;
//        this.loginLocalDAO = new LoginLocalDAO(context);
//        this.volleyRequestHandler = new VolleyRequestHandler(context);
//        this.loginActivity = (LoginActivity) context ;
//    }
//
//    /**
//     * Methgd to send login requests
//     * @param username
//     * @param password
//     */
//    public void loginRequestHandler(final String username, final String password){
//        android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
//
//        Runnable myRunnable = new Runnable() {
//            @Override
//            public void run() {
//                JSONObject jsonBody = new JSONObject();
//
//                try{
//                    jsonBody.put("username", username);
//                    jsonBody.put("password", password);
//                } catch (JSONException e){
//                    e.printStackTrace();
//                }
//
//                Response.Listener<JSONObject> listenerResponse = new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Login login = new Login(response.getString("identity"), response.getString("username"), response.getString("token"));
//
//                            loginLocalDAO.insertLogin(login);
//                            loginActivity.setErrorText("");
//                            loginActivity.dismissProgressDialog();
//                            loginActivity.loginIfTokenExist();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                Response.ErrorListener listenerError = new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        try{
//                            String errorRes = new String(error.networkResponse.data);
//                            JSONObject result = new JSONObject(errorRes);
//
//                            if(result.getInt("status") == 500)
//                                loginActivity.setErrorText("Error : " + result.get("message"));
//                            else
//                                loginActivity.setErrorText(context.getResources().getString(R.string.server_error_0));
//
//                            loginActivity.dismissProgressDialog();
//                        } catch (JSONException e){
//                            e.printStackTrace();
//                            loginActivity.dismissProgressDialog();
//                        } catch (NullPointerException e){
//                            e.printStackTrace();
//                            loginActivity.setErrorText(context.getResources().getString(R.string.server_error_1));
//                            loginActivity.dismissProgressDialog();
//                        }
//                    }
//                };
//
//                loginActivity.showProgressDialog(context.getResources().getString(R.string.checking_internet));
//
//                if(volleyRequestHandler.hasActiveInternetConnection()){
//                    loginActivity.showProgressDialog("Authenticating Credentials");
//                    volleyRequestHandler.postRequest(endpoint, jsonBody, listenerResponse, listenerError, null);
//                } else{
//                    loginActivity.snackBar(context.getResources().getString(R.string.poor_connection));
//                    loginActivity.dismissProgressDialog();
//                }
//            }
//        };
//
//        mainHandler.post(myRunnable);
//    }
//}
