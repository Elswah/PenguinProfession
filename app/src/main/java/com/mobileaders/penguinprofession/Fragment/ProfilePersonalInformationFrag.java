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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Utilities.Fonts;
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

public class ProfilePersonalInformationFrag extends Fragment implements View.OnClickListener {
    //private final String url="http://www.penguinprofessions.com/activities/insertUsersEducation";
     String gender="female";
    ImageView genderMale,genderFemale,genderOther;
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9;
    EditText eCountryCode, eMobileNumber, eBirthDate, eHomeTown;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datePickerListener;
    String ecountryCode ,emobileNumber,ehomeTown,ebirthDate;
    View view;
    SharedPreferences sharedPres;
    SharedPreferences.Editor edit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.profile_personal_information, container, false);
        setUpReferences(view);
        setUpUtilities();
        setBirthDate();
        setClickViews();
        return view;
    }

    private void setClickViews() {
        onClick(view);


    }



    private void setBirthDate() {
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                eBirthDate.setText(sdf.format(myCalendar.getTime()));
            }};
        eBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void setUpReferences(View view) {
        t1 = (TextView) view.findViewById(R.id.t1);
        t2 = (TextView) view.findViewById(R.id.t2);
        t3 = (TextView) view.findViewById(R.id.t3);
        t4 = (TextView) view.findViewById(R.id.t4);
        t5 = (TextView) view.findViewById(R.id.t5);
        t6 = (TextView) view.findViewById(R.id.t6);
        t7 = (TextView) view.findViewById(R.id.t7);
        t8 = (TextView) view.findViewById(R.id.t8);
        t9 = (TextView) view.findViewById(R.id.t9);
        genderMale=(ImageView)view.findViewById(R.id.gender_male);
        genderFemale=(ImageView)view.findViewById(R.id.gender_female);
        genderOther=(ImageView)view.findViewById(R.id.gender_other);
        genderMale.setOnClickListener(this);
        genderFemale.setOnClickListener(this);
        genderOther.setOnClickListener(this);


        eCountryCode = (EditText) view.findViewById(R.id.country_code);
        eMobileNumber = (EditText) view.findViewById(R.id.mobile_number);
        eHomeTown = (EditText) view.findViewById(R.id.home_town);
        eBirthDate = (EditText) view.findViewById(R.id.birth_date);
        //---------------------------------------------Set Listener


    }

    public boolean validation(){
        eCountryCode = (EditText) view.findViewById(R.id.country_code);
        eMobileNumber = (EditText) view.findViewById(R.id.mobile_number);
        eHomeTown = (EditText) view.findViewById(R.id.home_town);
        eBirthDate = (EditText) view.findViewById(R.id.birth_date);
         ecountryCode = eCountryCode.getText().toString().trim();
         emobileNumber=eMobileNumber.getText().toString().trim();
         ehomeTown=eHomeTown.getText().toString().trim();
         ebirthDate=eBirthDate.getText().toString().trim();
        boolean valid=true;
        if (ecountryCode.equals("") && emobileNumber.equals("") && ehomeTown.equals("")&& ebirthDate.equals("")) {
            eCountryCode.setError(getString(R.string.filed));
            eMobileNumber.setError(getString(R.string.filed));
            eHomeTown.setError(getString(R.string.filed));
            eBirthDate.setError(getString(R.string.filed));
            valid=false;

        } else if(ecountryCode.equals("")){
            eCountryCode.setError(getString(R.string.filed));
            valid=false;
        }
        else if(emobileNumber.equals("")){
            eMobileNumber.setError(getString(R.string.filed));
            valid=false;

        }
        else if(ehomeTown.equals("")) {
            eHomeTown.setError(getString(R.string.filed));
            valid=false;
        }
        else if (ebirthDate.equals("")){
            eBirthDate.setError(getString(R.string.filed));
            valid=false;
        }
        return valid;
    }



    public void postData(final String userId, final String password){
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLS.userEdu,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                           String status= jsonResponse.getString("state");
                           if(status =="1"){
                               String eduId = jsonResponse.getString("eduId");
                               Log.d("eduid",eduId);
                               edit.putString("keyeduId",eduId).apply();




                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId",userId);
                params.put("penguinUserId",userId);
                params.put("penguinUserPass",password);
                params.put("usersEducationStudiedIn","Mansoura");
                params.put("usersEducationDepartment","Computer Science");
                params.put("usersEducationTotalGrade","70");
                params.put("usersEducationFrom","2016-01-10");
                params.put("usersEducationTo","2016-05-10");
                params.put("educationDescritpion","android developer");

                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(postRequest);

    }

    private void setUpUtilities (){
        Fonts.set(new TextView[]{t1, t2, t3, t4, t5, t6, t7, t8, t9},getActivity(),1);
        Fonts.set(new EditText[]{eCountryCode, eMobileNumber, eBirthDate, eHomeTown},getActivity(),1);
        sharedPres = this.getActivity().getSharedPreferences("myData", Context.MODE_PRIVATE);
        edit  = sharedPres.edit();





    }

    public void getData (){
        Toast.makeText(getActivity(),"profile Personal Information",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gender_male:
                Toast.makeText(getActivity(), "male", Toast.LENGTH_SHORT).show();
                genderFemale.setImageDrawable(getResources().getDrawable(R.drawable.female));
                genderMale.setImageDrawable(getResources().getDrawable(R.drawable.male_active));
                genderOther.setImageDrawable(getResources().getDrawable(R.drawable.other));
                gender="male";
                break;
            case R.id.gender_female:
                Toast.makeText(getActivity(), "female", Toast.LENGTH_SHORT).show();
                genderFemale.setImageDrawable(getResources().getDrawable(R.drawable.female_active));
                genderMale.setImageDrawable(getResources().getDrawable(R.drawable.male));
                genderOther.setImageDrawable(getResources().getDrawable(R.drawable.other));
                gender="female";
                break;
            case R.id.gender_other:
                Toast.makeText(getActivity(), "other", Toast.LENGTH_SHORT).show();
                genderFemale.setImageDrawable(getResources().getDrawable(R.drawable.female));
                genderMale.setImageDrawable(getResources().getDrawable(R.drawable.male));
                genderOther.setImageDrawable(getResources().getDrawable(R.drawable.other_active));
                gender="other";
                break;
        }

    }


}
