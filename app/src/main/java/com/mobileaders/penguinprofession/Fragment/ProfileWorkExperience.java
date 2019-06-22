package com.mobileaders.penguinprofession.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by user on 05/11/2016.
 */

public class ProfileWorkExperience extends Fragment {
    EditText companyName,CompanyPosition,startDate,endDate;
    //String comName,comPosition,start,end;
    View view;
    RelativeLayout layoutYes,layoutNo;
    String chossen;
    ImageView imageNo,imageYes;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datePickerListener1;
    DatePickerDialog.OnDateSetListener datePickerListener2;
    //String url = "http://penguinprofessions.com/activities/insertUsersExperience";
    SharedPreferences sharedPres;
    SharedPreferences.Editor edit;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.profile_work_experience,container,false);

        initialization();
        setClickViews();
        setStartDate();
        setEndDate();
        return view;

    }
    public void postData(final String userId, final String pass){

        StringRequest postRequest = new StringRequest(Request.Method.POST, URLS.userExp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response",response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                          String status = jsonResponse.getString("state");
                          if(status =="1"){
                              String experienceId = jsonResponse.getString("experienceId");
                              Log.d("exid",experienceId);
                              edit.putString("keyexperienceId",experienceId).apply();


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
                params.put("userId",userId);
                params.put("penguinUserId",userId);
                params.put("penguinUserPass",pass);
                params.put("userExprienceCompanyName",companyName.getText().toString());
                params.put("userExprienceJobTitle", CompanyPosition.getText().toString());
                params.put("userExprienceFrom", startDate.getText().toString());
                params.put("userExprienceTo", endDate.getText().toString());
                params.put("userExprienceResponsibilities","manger");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(postRequest);

    }


    public void getData(){
        Toast.makeText(getActivity(), "ProfileWorkExperience", Toast.LENGTH_SHORT).show();
    }

    public void setStartDate(){
        datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                startDate.setText(sdf.format(myCalendar.getTime()));
            }};
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), datePickerListener1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }
    public  void setEndDate(){
        datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                endDate.setText(sdf.format(myCalendar.getTime()));
            }};
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), datePickerListener2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private   void initialization(){
         companyName=(EditText)view.findViewById(R.id.companyName);
         CompanyPosition=(EditText)view.findViewById(R.id.companyPosition);
         startDate=(EditText)view.findViewById(R.id.startDate);
         endDate=(EditText)view.findViewById(R.id.endDate);
         layoutYes=(RelativeLayout)view.findViewById(R.id.chooseOne);
         layoutNo=(RelativeLayout)view.findViewById(R.id.chooseTwo);
         imageNo=(ImageView)view.findViewById(R.id.imageNo);
         imageYes=(ImageView)view.findViewById(R.id.iamgeYes);
        //------------------------------------------------------------
        imageYes.setVisibility(View.INVISIBLE);
        sharedPres = this.getActivity().getSharedPreferences("myData", Context.MODE_PRIVATE);
        edit  = sharedPres.edit();




    }

    public    boolean validation(){
        boolean valid=true;
        if (companyName.getText().toString().equals("") && CompanyPosition.getText().toString().equals("")
                && startDate.getText().toString().equals("")&& endDate.getText().toString().equals("")) {
            companyName.setError(getString(R.string.filed));
            CompanyPosition.setError(getString(R.string.filed));
            startDate.setError(getString(R.string.filed));
            endDate.setError(getString(R.string.filed));
            valid=false;

        } else if(companyName.getText().toString().equals("")){
            companyName.setError(getString(R.string.filed));
            valid=false;
        }
        else if(CompanyPosition.getText().toString().equals("")){
            CompanyPosition.setError(getString(R.string.filed));
            valid=false;

        }
        else if(startDate.getText().toString().equals("")) {
            startDate.setError(getString(R.string.filed));
            valid=false;
        }
        else if (endDate.getText().toString().equals("")){
            endDate.setError(getString(R.string.filed));
            valid=false;
        }
        return valid;
    }


    public void setClickViews (){
        layoutYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // imageNo.setVisibility(View.GONE);
                //imageYes.setVisibility(View.VISIBLE);
               // imageYes.setImageDrawable(getResources().getDrawable(R.drawable.check));
                imageNo.setVisibility(View.INVISIBLE);
                imageYes.setVisibility(View.VISIBLE);
                chossen="yes";


            }
        });
        layoutNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // imageNo.setImageDrawable(getResources().getDrawable(R.drawable.check));
               // imageYes.setVisibility(View.GONE);
               // imageNo.setVisibility(View.VISIBLE);
                imageYes.setVisibility(View.INVISIBLE);
                imageNo.setVisibility(View.VISIBLE);
                chossen="no";

            }
        });

    }




}
