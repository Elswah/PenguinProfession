package com.mobileaders.penguinprofession.Utilities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 02/11/2016.
 */

public class SocialLogin extends Activity{
    CallbackManager callbackManager;
    public SocialLogin(){}
    public static void initializeFacebook (Context context , Application application){
        FacebookSdk.sdkInitialize(context);
        AppEventsLogger.activateApp(application);
    }

    public static void onFacebookSuccess (LoginResult loginResult){
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                // Get data from Facebook
                Log.e("fbData", "" + object);

                try {
                    String Uid = object.getString("id");
                    String Uname = object.getString("name");
                    Log.e("fbData", "ID : " + Uid + "\n" + "Name : " + Uname);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//      what is that ?!!
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id , name, birthday");
        request.setParameters(parameters);
        request.executeAsync();
        Log.e("fbData", "success");
    }

    public static GoogleSignInOptions getGso (){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }



    public static void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String id = acct.getId();
            String email = acct.getEmail();
            String name = acct.getDisplayName();
            Log.e("googleplus", "id : " + id + " email : " + email + " name : " + name);
        }
    }
}
