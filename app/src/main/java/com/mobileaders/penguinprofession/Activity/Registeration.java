package com.mobileaders.penguinprofession.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Utilities.Fonts;
import com.mobileaders.penguinprofession.Utilities.PrefSingleton;
import com.mobileaders.penguinprofession.Utilities.SocialLogin;
import com.mobileaders.penguinprofession.Utilities.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Registeration extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    TextView title, signupTitle, youHaveAccount, txtEmployee, txtPerson;
    EditText name, email, password, jobTitle;
    RadioButton person, employee;
    private RadioGroup genderRadioGroup;
    Button fbLogin, googleLogin, in, signup, logInNow, logIn, useWithoutAccount;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static int RC_FB_SIGN_IN;
    SignInButton signIn;
    private String userType = null;
    ProgressDialog progress;
    private String userName, emailReg, passWord, jopTitleReg;
    String id ,pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(true);
        //      initialize Facebook SDK

        SocialLogin.initializeFacebook(getApplicationContext(), getApplication());

        setContentView(R.layout.activity_registeration);
        setUpRefrences();
        setUtilies();
        setUpClick();

        RC_FB_SIGN_IN = loginButton.getRequestCode();

//  Google sign in Options
        GoogleSignInOptions gso = SocialLogin.getGso();

//   Build a GoogleApiClient with access to GoogleSignIn.API and the options above.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signup.setOnClickListener(this);
        // person.setOnCheckedChangeListener(this);
        //  employee.setOnCheckedChangeListener(this);

    }

    public void setUpRefrences() {
        title = (TextView) findViewById(R.id.donotHaveAccountTxt);
        signupTitle = (TextView) findViewById(R.id.signUpNowTxt);
        youHaveAccount = (TextView) findViewById(R.id.youHaveAccount);
        txtEmployee = (TextView) findViewById(R.id.employee);
        txtPerson = (TextView) findViewById(R.id.person);
        name = (EditText) findViewById(R.id.userNameRegister);
        email = (EditText) findViewById(R.id.emailRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        jobTitle = (EditText) findViewById(R.id.jobTitleRegister);
        employee = (RadioButton) findViewById(R.id.rdEmployer);
        person = (RadioButton) findViewById(R.id.rdPerson);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        fbLogin = (Button) findViewById(R.id.fb);
        googleLogin = (Button) findViewById(R.id.go);
        in = (Button) findViewById(R.id.in);
        loginButton = (LoginButton) findViewById(R.id.fcLogin_button);
        signup = (Button) findViewById(R.id.signup);
        logInNow = (Button) findViewById(R.id.logInNow);
        logIn = (Button) findViewById(R.id.login);
        useWithoutAccount = (Button) findViewById(R.id.useWithoutAccount);
        signIn = (SignInButton) findViewById(R.id.googleregister);


    }

    public void setUtilies() {
        Fonts.set(new Button[]{signup, logInNow, logIn, useWithoutAccount}, this, 1);
        Fonts.set(new TextView[]{title, signupTitle, youHaveAccount, txtEmployee, txtPerson}, this, 1);
        Fonts.set(new EditText[]{email, name, password, jobTitle}, this, 1);
    }

    public void setUpClick() {
        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
                facebookLogin();
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn.performClick();
                googleSignIn();

            }
        });
        logInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registeration.this, LoginActivity.class));
                finish();
            }
        });

        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setBackgroundResource(R.drawable.select_active);
                userType = "person";
                employee.setBackgroundResource(R.drawable.select);

            }
        });
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee.setBackgroundResource(R.drawable.select_active);
                userType = "employee";
                person.setBackgroundResource(R.drawable.select);
            }
        });
    }

    private void facebookLogin() {

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                SocialLogin.onFacebookSuccess(loginResult);

                Intent intent = new Intent(Registeration.this, ProfileInformation.class);
                startActivity(intent);

            }

            @Override
            public void onCancel() {
                Log.e("fbData", "canceled");

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("fbData", "Error : " + error);

            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_FB_SIGN_IN) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            SocialLogin.handleSignInResult(result);

            Intent intent = new Intent(Registeration.this, ProfileInformation.class);
            startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(Registeration.this, "Error in google plus ", Toast.LENGTH_LONG).show();

    }

    //validation of register
    private void singUP() {
        if (userName.equals("") && passWord.equals("") && jopTitleReg.equals("") && emailReg.equals("")) {

            Toast.makeText(getApplicationContext(), "PLZ fill all fields", Toast.LENGTH_LONG).show();
            if (userName.equals(""))
                name.setError(getString(R.string.filed));
            if (passWord.equals(""))
                password.setError(getString(R.string.filed));
            if (jopTitleReg.equals(""))
                jobTitle.setError(getString(R.string.filed));
            if (emailReg.equals(""))
                email.setError(getString(R.string.filed));
        } else if (userName.equals("")) {
            name.setError(getString(R.string.filed));
        } else if (passWord.equals("")) {
            password.setError(getString(R.string.filed));

        } else if (jopTitleReg.equals("")) {
            jobTitle.setError(getString(R.string.filed));
        } else if (emailReg.equals("")) {
            email.setError(getString(R.string.filed));
        } else if (!emailReg.matches("[a-zA-Z0-9._-]+@[a-z]+.com+")) {
            Toast.makeText(getApplicationContext(), "invalid email", Toast.LENGTH_LONG).show();
        } else {
            parsingData();

        }
    }

   // public String getPassword() {
   //     return pass;
   // }

   // public int getID() {
  //      return id;
  //  }

    // register data to server

    private void parsingData() {

        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, URLS.userInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("register", response);
                hideDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("state")) {
                        String status = jsonObject.getString("state");

                        if (status.equals("1")) {
                            id = jsonObject.getString("userId");
                            PrefSingleton.putPref("keyuserid", id, Registeration.this);
                            Toast.makeText(Registeration.this, ""+id, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Registeration.this, ProfileInformation.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    JSONObject error = jsonObject.getJSONObject("errors");
                    String emailError = error.getString("userEmail");
                    if (emailError.equals("Please provide an Unique email address.")) {
                        Toast.makeText(Registeration.this, "Email is Exist", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registeration.this, "Please check your internet connection !", Toast.LENGTH_SHORT).show();
                hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userFullName", userName);
                params.put("userEmail", emailReg);
                params.put("password", passWord);
                params.put("userHeadLine", jopTitleReg);
                // params.put("",userType);
                params.put("userDescription", "dsdsd");
                params.put("userPayed", "12121");
                params.put("userAge", "21");
                params.put("userPhoneNumber", "qwer");
                params.put("userWebsite", "ewq");
                params.put("userAddress", "eww");
                params.put("userImg", "dsdsd");
                PrefSingleton.putPref("keypasswordd",password.getText().toString(),Registeration.this);
                return params;
            }
        };
        // Volley.newRequestQueue(getApplicationContext()).add(postRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);


    }


    // register new user
    @Override
    public void onClick(View v) {


        userName = name.getText().toString().trim();
        emailReg = email.getText().toString().trim();
        passWord = password.getText().toString().trim();
        jopTitleReg = jobTitle.getText().toString().trim();


        singUP();


    }

    private void showDialog() {
        if (!progress.isShowing())
            progress.setMessage("Loading...");
            progress.setCanceledOnTouchOutside(false);
            progress.show();
            progress.setCancelable(false);


    }

    private void hideDialog() {

        if (progress.isShowing())

            progress.dismiss();

    }

}

