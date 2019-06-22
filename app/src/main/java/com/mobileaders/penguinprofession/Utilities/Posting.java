package com.mobileaders.penguinprofession.Utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahmed on 1/11/2017.
 */
public class Posting {
    private Activity ctx;
    public  Posting(Activity context){
        this.ctx=context;
    }
    String url="www.penguinprofessions.com/activities/insertUsersEducation";
    public    void post(final String user, final String pass){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.v("responsefirst",response);
                            JSONObject jsonResponse = new JSONObject(response);
//                            String status= jsonResponse.getString("state");
//                            if(status =="1"){
//                                String eduId = jsonResponse.getString("eduId");
//                                PrefSingleton.putPref("keyeduId",eduId,getActivity());
//
//
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("userId",user);
                params.put("penguinUserId",user);
                params.put("penguinUserPass",pass);
                params.put("usersEducationStudiedIn","Mansoura");
                params.put("usersEducationDepartment","Computer Science");
                params.put("usersEducationTotalGrade","70");
                params.put("usersEducationFrom","2016-01-10");
                params.put("usersEducationTo","2016-05-10");
                params.put("educationDescritpion","android developer");
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(postRequest);

    }
}
