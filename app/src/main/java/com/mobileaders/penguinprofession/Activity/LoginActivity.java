package com.mobileaders.penguinprofession.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.mobileaders.penguinprofession.Utilities.GetHashKey;
import com.mobileaders.penguinprofession.Utilities.PrefSingleton;
import com.mobileaders.penguinprofession.Utilities.SocialLogin;
import com.mobileaders.penguinprofession.Utilities.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private TextView h1, h2, h3, h4, forgotPass;
    private EditText email, pass;
    private Button login, createAccount, normalUser, fbLogin, linkedInLogin, googleLogin;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static int RC_FB_SIGN_IN;
    ProgressDialog progress;
    SignInButton signInButton;
   // String url="http://penguinprofessions.com/activities/userLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //      initialize Facebook SDK

        SocialLogin.initializeFacebook(getApplicationContext(),getApplication());

        setContentView(R.layout.activity_login);

        setUpReferences();
        setUpFonts();
        setUpClick();

        GetHashKey.getTheKeyHash(this);
//  FaceBook request code
        RC_FB_SIGN_IN = loginButton.getRequestCode();

//  Google sign in Options
        GoogleSignInOptions gso = SocialLogin.getGso();

//   Build a GoogleApiClient with access to GoogleSignIn.API and the options above.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void setUpReferences() {
        h1 = (TextView) findViewById(R.id.h1);
        h2 = (TextView) findViewById(R.id.h2);
        h3 = (TextView) findViewById(R.id.h3);
        h4 = (TextView) findViewById(R.id.h4);
        forgotPass = (TextView) findViewById(R.id.forgotPass);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);

        login = (Button) findViewById(R.id.login);

        fbLogin = (Button) findViewById(R.id.fbLogin);
        linkedInLogin = (Button) findViewById(R.id.linkedInLogin);
        googleLogin = (Button) findViewById(R.id.googleLogin);

        createAccount = (Button) findViewById(R.id.createAccount);
        normalUser = (Button) findViewById(R.id.normalUser);
//        faceBook && googlePlus Default loginButton
        loginButton = (LoginButton) findViewById(R.id.login_button);
        signInButton=(SignInButton) findViewById(R.id.google);



    }

    private void setUpFonts() {
        Fonts.set(new Button[]{login, createAccount, normalUser}, this, 1);
        Fonts.set(new TextView[]{h1, h2, h3, h4, forgotPass}, this, 1);
        Fonts.set(new EditText[]{email, pass}, this, 1);

    }

    private  boolean validation(){
        boolean check=true;
        // String user_email= PrefSingleton.getPref("keyemail",LoginActivity.this);
        //String user_pasword=  PrefSingleton.getPref("keypassword",LoginActivity.this);
        if(email.getText().toString().equals("")&&pass.getText().toString().equals("")){
            email.setError(getString(R.string.filed));
            pass.setError(getString(R.string.filed));
            check=false;
        } else if(email.getText().toString().equals("")){
            email.setError(getString(R.string.filed));
            check=false;
        }else if(pass.getText().toString().equals("")){
            pass.setError(getString(R.string.filed));
            check=false;
        } else if (!email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.com+")) {
            email.setError("Invalid Email");
            check=false;
        }


        return check;
    }

    private void showDialog() {
        progress = new ProgressDialog(this);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("Loading...");
        if (!progress.isShowing())
            progress.show();
    }
    private void hideDialog() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }
    private void postData(){
        Log.d("login","loginokkkkkkkkkkkkkkkk");
        showDialog();
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLS.userLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("register", response);
                hideDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("state");
                    if (status.equals("1")) {
                       String userID= jsonObject.getString("userId");
                       String userName= jsonObject.getString("userName");
                        Log.d("username",userName);
                        PrefSingleton.putPref("keyuserName",userName,LoginActivity.this);
                        PrefSingleton.putPref("keyuserid" ,userID,LoginActivity.this);

                        Intent i=new Intent(LoginActivity.this,Home.class);
                        startActivity(i);
                    }else if(status.equals("2")){
                        Toast.makeText(LoginActivity.this, "Please check Your Data..", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Please check your internet connection !", Toast.LENGTH_SHORT).show();
                hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userEmail", email.getText().toString());
                params.put("userPassword", pass.getText().toString());

                PrefSingleton.putPref("keypasswordd",pass.getText().toString(),LoginActivity.this);



                return params;
            }
        };
        // Volley.newRequestQueue(getApplicationContext()).add(postRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);


    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void setUpClick() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validation()==false){

                }
                else if(!isOnline()){
                    Toast.makeText(LoginActivity.this, "Please Check Internet Connection!!!", Toast.LENGTH_SHORT).show();

                }
                else if(validation()==true){
                    postData();

                }

            }
        });


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Registeration.class);
                startActivity(intent);
                //animate from one activity to another activity
              //  overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
                facebookLogin();

            }
        });
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInButton.performClick();
                googleSignIn();

            }
        });
    }



    /*Method for Facebook login
    *
    * getting User information
     */
    private void facebookLogin() {

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                SocialLogin.onFacebookSuccess(loginResult);

                Intent intent = new Intent(LoginActivity.this, ProfileInformation.class);
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
        if (requestCode == RC_FB_SIGN_IN){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            SocialLogin.handleSignInResult(result);

            Intent intent = new Intent(LoginActivity.this, ProfileInformation.class);
            startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this,"Error in google plus ",Toast.LENGTH_LONG).show();
    }
}
