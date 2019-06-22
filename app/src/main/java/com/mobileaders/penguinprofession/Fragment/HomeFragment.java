package com.mobileaders.penguinprofession.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobileaders.penguinprofession.Activity.JobDetailsFragment;
import com.mobileaders.penguinprofession.Adapter.RecyclerHome;
import com.mobileaders.penguinprofession.AppController;
import com.mobileaders.penguinprofession.Module.ModelHome;
import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Utilities.URLS;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by user on 13/11/2016.
 */


public class HomeFragment extends Fragment implements RecyclerHome.ClickeListener{
    RecyclerView recyclerView;
    RecyclerHome recyclerHome;
    Fragment fragment;
    //String url="http://penguinprofessions.com/activities/getJobs";
    ProgressDialog progress;
    View view;
    ArrayList<ModelHome>recyclerHomes;
    ArrayList<ModelHome>myList;
    public static final String KEY_SEARCH="key";
    String itemSearch;
    ArrayList<ModelHome>newList2;
    public static String sDefSystemLanguage;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        myList=new ArrayList<>();
        newList2=new ArrayList<>();
        recyclerHomes=new ArrayList<>();
        myList= (ArrayList<ModelHome>) getData(URLS.userJob);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerHome);
        recyclerHome=new RecyclerHome(getActivity(),myList);
        recyclerView.setAdapter(recyclerHome);
        recyclerHome.setClickeListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sDefSystemLanguage = Locale.getDefault().getLanguage();
        Log.v("language",sDefSystemLanguage);
        if (sDefSystemLanguage.equals("ar")){
            Log.e("lang","arabic");
        }else {
            Log.e("lang","english");
        }
        return view;
    }



    private void showDialog() {
        progress = new ProgressDialog(getActivity());
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
    public void clearData() {
        myList.clear(); //clear list
        recyclerHome.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }


    public ArrayList<ModelHome> searchJob(String url){
        clearData();
        newList2.clear();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String jobDescription=jsonObject.getString("jobDescription");
                                String jobTitle=jsonObject.getString("jobTitle");
                                String companyPic=jsonObject.getString("companyPic");
                                String companyName=jsonObject.getString("companyName");
                                String companyLocation= jsonObject.getString("jobAddress");
                                ModelHome modelHome=new ModelHome();
                                modelHome.setTxtLocation(companyLocation);
                                modelHome.setTitle(jobTitle);
                                modelHome.setTxtTag(jobTitle);
                                modelHome.setDescription(jobDescription);
                                modelHome.setTxtWork(companyName);
                                modelHome.setLogo(companyPic);
                                newList2.add(modelHome);
                                recyclerHome=new RecyclerHome(getActivity(),newList2);
                                recyclerView.setAdapter(recyclerHome);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recyclerHome.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Please check your internet connection !", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("location","mans");
                params.put("degrees","1");
                params.put("contractTypes","2");
                params.put("rates","1");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(postRequest);
        return recyclerHomes;
    }

    public  List<ModelHome> getData(String url){
        recyclerHomes.clear();
        showDialog();
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("data",response.toString());
                hideDialog();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String jobDescription=jsonObject.getString("jobDescription");
                        String jobTitle=jsonObject.getString("jobTitle");
                        String companyPic=jsonObject.getString("companyPic");
                        String companyName=jsonObject.getString("companyName");
                        String companyLocation= jsonObject.getString("jobAddress");
                        String jobTimeType= jsonObject.getString("jobTimeType");
                        String jobSalary= jsonObject.getString("jobSalary");
                        String jobDetails= jsonObject.getString("jobDetails");
                        String jobID=  jsonObject.getString("jobId");
                        ModelHome modelHome=new ModelHome();
                        modelHome.setTxtLocation(companyLocation);
                        modelHome.setTitle(jobTitle);
                        modelHome.setTxtTag(jobTitle);
                        modelHome.setDescription(jobDescription);
                        modelHome.setTxtWork(companyName);
                        modelHome.setLogo(companyPic);
                        modelHome.setJobSalary(jobSalary);
                        modelHome.setJobTimeType(jobTimeType);
                        modelHome.setJobDetials(jobDetails);
                        modelHome.setJobID(jobID);

                        recyclerHomes.add(modelHome);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerHome.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Please check your internet connection !", Toast.LENGTH_SHORT).show();
                hideDialog();

            }
        });

       // Volley.newRequestQueue(getActivity()).add(request);
        AppController.getInstance().addToRequestQueue(request);
        return recyclerHomes;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        sDefSystemLanguage = newConfig.locale.getLanguage();
    }

    @Override
    public void itemClick(View view, int position) {
      //  startActivity(new Intent(getActivity(),JobDetailsFragment.class));

    }

}
