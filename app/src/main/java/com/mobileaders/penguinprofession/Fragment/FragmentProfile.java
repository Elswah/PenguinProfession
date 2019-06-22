package com.mobileaders.penguinprofession.Fragment;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobileaders.penguinprofession.Interface.ConectionProfile;
import com.mobileaders.penguinprofession.Module.ProfileModel;
import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Sqllite.Data;
import com.mobileaders.penguinprofession.Sqllite.DatabaseHandler;
import com.mobileaders.penguinprofession.Sqllite.ModleData;
import com.mobileaders.penguinprofession.Utilities.PrefSingleton;
import com.mobileaders.penguinprofession.Utilities.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by user on 13/11/2016.
 */
public class FragmentProfile extends Fragment {
    private Retrofit retrofit;
    private ConectionProfile callRet;
    private ProgressDialog progressDialog;
    TextView txtJobName ,txtEductionalTitle ,txtEductionalDescribtion,profileName,birthdayDay,genderType,salaryNum,email,phoneNum,location;
    private String postContent;
    private String educationDep;
    private String educationFrom;
    private String educationTo;
    private String edcationStudyIn;
    private String educationgrade;
    private String keyuserid , keypassword;
    private String educationDescription;
    private String skillTitle;
    private String skillPercent;
    private String companyName;
    private String experienceTo;
    private String experienceFrom;
    private String experienceResposibilities;
    //private  String url="http://penguinprofessions.com/activities/getUserData";
    private String userId,userFullName,userEmail,userPayed,userDescription,
            userAge,userHeadLine,userPhoneNumber,userWebsite,userAddress,userCreationDate;

    ArrayList<ModleData> myList=new ArrayList<>();
    private DatabaseHandler db;
    private String  log ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
         db = new DatabaseHandler(getActivity());

        showDialog(view);
        setUpReferences(view);
        getUserIdPass(view);
        setUpConnection();
        getUserWork();
        getUserEducation();
        getUserSkills();
        getUserData();
        getData();
        return view;
    }

    private void getUserIdPass(View v) {
        keyuserid = PrefSingleton.getPref("keyuserid", v.getContext());
        keypassword = PrefSingleton.getPref("keypasswordd", v.getContext());

       // Log.e("asfkjaskfa",keyuserid);
       Log.e("safasfas",keypassword);


    }

    private void showDialog(View v) {
        progressDialog = ProgressDialog.show(v.getContext(), "Please wait.",
                "Loading...!", true);
    }

    private void setUpReferences(View view) {
        
        txtJobName = (TextView) view.findViewById(R.id.txtJobName);
        txtEductionalTitle = (TextView) view.findViewById(R.id.txtEductionalTitle);
        txtEductionalDescribtion = (TextView) view.findViewById(R.id.txtEductionalDescribtion);
        profileName= (TextView) view.findViewById(R.id.profileName);
        birthdayDay= (TextView) view.findViewById(R.id.birthdayDay);
        genderType= (TextView) view.findViewById(R.id.genderType);
        salaryNum= (TextView) view.findViewById(R.id.salaryNum);
        email= (TextView) view.findViewById(R.id.email);
        phoneNum= (TextView) view.findViewById(R.id.phoneNum);
        location= (TextView) view.findViewById(R.id.Location);
    }
    private void setUpConnection() {
        retrofit =new Retrofit.Builder()
                .baseUrl("http://penguinprofessions.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        callRet = retrofit.create(ConectionProfile.class);

    }
    private void getUserWork() {
        Call<List<ProfileModel>> connection = callRet.getUserWorks(keyuserid,keypassword);


        connection.enqueue(new Callback<List<ProfileModel>>() {
            @Override
            public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {

                List<ProfileModel> body = response.body();
                for (int i = 0;i < body.size();i++){
                   // postContent = body.get(i).getUserExprienceJobTitle();
                    companyName =body.get(i).getUserExprienceCompanyName();
                    experienceTo =body.get(i).getUserExprienceTo();
                    experienceFrom =body.get(i).getUserExprienceFrom();
                    experienceResposibilities =body.get(i).getUserExprienceResponsibilities();
                }
                
             //   txtJobName.setText(postContent);

            }

            @Override
            public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                Log.e("gross" , t.getMessage());

            }
        });
    }
    private void getUserEducation() {
        Call<List<ProfileModel>> connection = callRet.getUserEducation(keyuserid,keypassword);


        connection.enqueue(new Callback<List<ProfileModel>>() {


            @Override
            public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {
                List<ProfileModel> body = response.body();
                for (int i = 0;i < body.size();i++){
                    educationDep = body.get(i).getUsersEducationDepartment();
                    educationFrom = body.get(i).getUsersEducationFrom();
                    educationTo = body.get(i).getUsersEducationTo();
                    edcationStudyIn = body.get(i).getUsersEducationStudiedIn();
                    educationgrade = body.get(i).getUsersEducationTotalGrade();
                    educationDescription = body.get(i).getEducationDescritpion();

                }

                txtEductionalTitle.setText(educationDep);
                txtEductionalDescribtion.setText(educationDescription);

            }

            @Override
            public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                Log.e("gross" , t.getMessage());

            }
        });
    }
    private void getUserSkills() {
        Call<List<ProfileModel>> connection = callRet.getUserSkills(keyuserid,keypassword);
        connection.enqueue(new Callback<List<ProfileModel>>() {
            @Override
            public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {
                List<ProfileModel> body = response.body();
                for (int i = 0;i<body.size();i++){
                   skillTitle= body.get(i).getUserSkillTitle();
                    skillPercent =body.get(i).getUserSkillPercent();
                }
            }

            @Override
            public void onFailure(Call<List<ProfileModel>> call, Throwable t) {

            }
        });


        progressDialog.dismiss();
    }

    public  void getUserData() {

        StringRequest request = new StringRequest(Request.Method.POST, URLS.userData, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                        String state = jsonObject.getString("state");
                        JSONObject userData = jsonObject.getJSONObject("userData");
                         userId = userData.getString("userId");
                         userFullName = userData.getString("userFullName");
                         userEmail = userData.getString("userEmail");
                         userCreationDate = userData.getString("userCreationDate");
                         userPayed = userData.getString("userPayed");
                         userDescription = userData.getString("userDescription");
                         userAge = userData.getString("userAge");
                         userHeadLine = userData.getString("userHeadLine");
                         userPhoneNumber = userData.getString("userPhoneNumber");
                         userWebsite = userData.getString("userWebsite");
                         userAddress = userData.getString("userAddress");
                        profileName.setText(userFullName);
                        txtJobName.setText(userHeadLine);
                        birthdayDay.setText(userAge);
                        email.setText(userEmail);
                        salaryNum.setText(userPayed);
                        phoneNum.setText(userPhoneNumber);
                        location.setText(userAddress);
                    // pass data to model
                    ModleData modle=new ModleData();
                    modle.setId(userId);
                    modle.setUserFullName(userFullName);
                    modle.setUserEmail(userEmail);
                    modle.setUserPayed(userPayed);
                    modle.setUserDescription(userDescription);
                    modle.setUserAge(userAge);
                    modle.setUserHeadLine(userHeadLine);
                    modle.setUserPhoneNumber(userPhoneNumber);
                    modle.setUserWebsite(userWebsite);
                    modle.setUserAddress(userAddress);
                    db.addData(modle);
                    Log.d("Insert", "Inserting ..");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId",keyuserid);
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);

        progressDialog.dismiss();
    }

    private void  getData() {
        List<ModleData> getData = db.getAlLdata();
        for (ModleData cn : getData) {
             log = "id: " + cn.getId() + " ,name: " + cn.getUserFullName() + " ,email: " + cn.getUserEmail() + " ,payed: " +cn.getUserPayed()
                    + " ,description : " + cn.getUserDescription() + " ,age: " + cn.getUserAge() + " ,headline: " +cn.getUserHeadLine() +
                    " ,phone: " + cn.getUserWebsite() +" ,website : " + cn.getUserPhoneNumber() +" ,address: " +cn.getUserAddress() ;
        }
        Log.d("nnnnnnnnnnnn", log);


    }
    }


