package com.mobileaders.penguinprofession.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Utilities.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 05/11/2016.
 */

public class ProfileKindJob extends Fragment {

    EditText positionTitle,careerLevel;
    private String position;
    private String career;
    //private String url="http://www.penguinprofessions.com/activities/insertUsersSkill";
    SharedPreferences sharedPres;
    SharedPreferences.Editor edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_job_kind,container,false);
        initialization(view);
        return view;
    }

    public void getData(){
        Toast.makeText(getActivity(),"Profile Kind Job",Toast.LENGTH_LONG).show();
    }

    public void initialization(View view){

        positionTitle = (EditText) view.findViewById(R.id.position_titel);
        careerLevel = (EditText) view.findViewById(R.id.career_level);
        sharedPres = this.getActivity().getSharedPreferences("myData",Context.MODE_PRIVATE);
        edit  = sharedPres.edit();

    }

    public boolean validation(){
        position  = positionTitle.getText().toString().trim();
        career    =careerLevel.getText().toString().trim();

        boolean valid=true;
        if (position.equals("") && career.equals("") ) {
            positionTitle.setError(getString(R.string.filed));
            careerLevel.setError(getString(R.string.filed));
            valid=false;

        } else if(position.equals("")){
            positionTitle.setError(getString(R.string.filed));
            valid=false;
        }
        else if(career.equals("")){
            careerLevel.setError(getString(R.string.filed));
            valid=false;

        }

        return valid;
    }
    public void postData(final String userId, final String password){
        final StringRequest postRequest = new StringRequest(Request.Method.POST, URLS.userSkill,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status= jsonResponse.getString("state");
                            if(status =="1"){
                                String skillId = jsonResponse.getString("skillId");
                                Log.d("skid",skillId);
                                edit.putString("keyskillId",skillId).apply();

                            }
                            JSONObject errors = jsonResponse.getJSONObject("errors");
                            String userSkillPercent = errors.getString("userSkillPercent");
                            if(userSkillPercent.equals("The Skill Percent field must contain only numbers.")){
                                positionTitle.setError(getString(R.string.numberfield));
                            }



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
                params.put("penguinUserId",userId);
                params.put("userId",userId);
                params.put("penguinUserPass",password);
                params.put("userSkillPercent",position);
                params.put("userSkillTitle",career);


                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(postRequest);

    }





}
