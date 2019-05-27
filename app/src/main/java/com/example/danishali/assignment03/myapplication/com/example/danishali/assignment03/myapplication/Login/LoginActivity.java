//package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.example.danishali.assignment03.myapplication.R;
//
//
//
//public class LoginActivity extends AppCompatActivity {
//
//    private EditText username;
//    private EditText password;
//    private TextView errorText;
//    private Button loginButton;
//    private TextView signUpButton;
//    private TextView forgotPasswordButton;
//
//    private ProgressDialog progressDialog;
//    private LoginLocalDAO loginLocalDAO;
//    private LoginRemoteDAO loginRemoteDAO;
//
//    /**
//     * Method to set view of activity
//     * @param savedInstanceState
//     */
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        init();
//        loginButtonHandler();
//        signUpButtonHandler();
//        forgotPasswordButtonHandler();
//    }
//
//    /**
//     * Method to check if user is already logged in while app starts
//     */
//    @Override
//    protected void onStart() {
//        super.onStart();
//        loginIfTokenExist();
//    }
//
//    /**
//     * Method to initialize all variables of this class
//     */
//    private void init(){
//        username = (EditText) findViewById(R.identity.username);
//        password = (EditText) findViewById(R.identity.password);
//        errorText = (TextView) findViewById(R.identity.error_text);
//        loginButton = (Button) findViewById(R.identity.login_button);
//        signUpButton = (TextView) findViewById(R.identity.sign_up_button);
//        forgotPasswordButton = (TextView) findViewById(R.identity.forgot_password_button);
//
//        progressDialog = new ProgressDialog(this);
//        loginLocalDAO = new LoginLocalDAO(this);
//        loginRemoteDAO = new LoginRemoteDAO(this);
//    }
//
//    /**
//     * Method to handle login button click
//     */
//    private void loginButtonHandler(){
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //To-Do check if username and password input is done
//                showProgressDialog("Validating Credentials");
//                if(isLoginFormFilled()){
//                    errorText.setText("");
//                    loginRequest();
//                } else {
//                    dismissProgressDialog();
//                }
//                //To-Do then send a request a to api
//                //T0-Do handle response
//            }
//        });
//    }
//
//    /**
//     * Method to handle sign up button click
//     */
//    private void signUpButtonHandler(){
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);
//                startActivity(signUpActivity);
//            }
//        });
//    }
//
//    /**
//     * Method to handle forgot button click
//     */
//    private void forgotPasswordButtonHandler(){
//        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //To-Do take to new activity for password reset
//            }
//        });
//    }
//
//    /**
//     * Method to check if login username an dpassword fields filled properly
//     * @return boolean
//     */
//    private boolean isLoginFormFilled(){
//        Boolean formFilled = true;
//
//        if(!isEditTextFilled(password))
//            formFilled = false;
//        if(!isEditTextFilled(username))
//            formFilled = false;
//
//        return formFilled;
//    }
//
//    /**
//     * Method to check if edit text field is propely filled.
//     * If not filled properly then set error on that field.
//     * @param editText
//     * @return boolean
//     */
//    private boolean isEditTextFilled( EditText editText){
//        if(editText == null){
//            editText.setError("Required");
//            editText.requestFocus();
//            return false;
//        } else if(editText.getText().toString().isEmpty()){
//            editText.setError("Required");
//            editText.requestFocus();
//            return false;
//        } else if(editText.getText().toString().contains(" ")){
//            editText.setError("Spaces not allowed");
//            editText.requestFocus();
//            return false;
//        } else{
//            editText.clearFocus();
//            return true;
//        }
//    }
//
//    /**
//     * Method to make login request to server
//     */
//    private void loginRequest(){
//        loginRemoteDAO.loginRequestHandler(username.getText().toString(), password.getText().toString());
//    }
//
//    /**
//     * Method to change activity to Home Activity
//     */
//    private void startHomePage(){
//        Intent bottomNavigationActivity = new Intent(LoginActivity.this, BottomNavigation.class);
//        startActivity(bottomNavigationActivity);
//    }
//
//    /**
//     * Method to check if token already exist in databse for a user
//     * @return boolean
//     */
//    private boolean checkToken(){
//        Login login = loginLocalDAO.getLogin();
//
//        if(login == null)
//            return false;
//        else if(login.getId().isEmpty() || login.getId() == null)
//            return false;
//        else if(login.getToken().isEmpty() || login.getToken() == null)
//            return false;
//        else
//            return true;
//    }
//
//    /**
//     * Method to login if token already exist
//     */
//    public void loginIfTokenExist(){
//        if(checkToken())
//            startHomePage();
//    }
//
//    /**
//     * Method to set error text in view
//     * @param error
//     */
//    public void setErrorText(String error){
//        errorText.setText(error);
//    }
//
//    /**
//     * Method to show progress dialog box
//     * @param message
//     */
//    public void showProgressDialog(String message){
//        if(progressDialog.isShowing())
//            progressDialog.setMessage(message);
//        else{
//            progressDialog.setTitle("Logging in...");
//            progressDialog.setMessage(message);
//            progressDialog.show();
//        }
//    }
//
//    /**
//     * Method to dismiss progress dialog
//     */
//    public void dismissProgressDialog(){
//        progressDialog.dismiss();
//    }
//
//    /**
//     * Method to show snackbar in activity
//     * @param message
//     */
//    public void snackBar(String message){
//        Snackbar.make(findViewById(android.R.identity.content), message, Snackbar.LENGTH_LONG).show();
//    }
//
//}
