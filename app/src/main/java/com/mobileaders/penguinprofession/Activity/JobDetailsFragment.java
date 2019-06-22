package com.mobileaders.penguinprofession.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.mobileaders.penguinprofession.Adapter.SkillsRecyclerAdapter;
import com.mobileaders.penguinprofession.Module.ModelHome;
import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Utilities.Fonts;
import com.mobileaders.penguinprofession.Utilities.PrefSingleton;
import com.mobileaders.penguinprofession.Utilities.URLS;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 26/11/2016.
 */

public class JobDetailsFragment extends AppCompatActivity {
    ImageView back_arrow,company_logo;
    TextView jobTitle,companyName,jobLocation,jobTime,jobSalary,jobDescrption,jobTag,jobDetails;
    String jobType="";
    String jobID;
    RecyclerView mSkillsRecyclerView;
    SkillsRecyclerAdapter skillsAdapter;
    String userID,currentUserPassword;
    //Static Skils Data
    String mSkillsData[] = {"Java", "Android", "C++", "HTML5", "CSS3", "Adobe PhotoShop"
            , "Android", "C++", "HTML5", "CSS3", "Adobe PhotoShop",
            "Android", "C++", "HTML5", "CSS3", "Adobe PhotoShop"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetails);
        initialization();
        getDetailData();


    }
    private void initialization(){
        back_arrow=(ImageView)findViewById(R.id.back);
        company_logo=(ImageView)findViewById(R.id.companyLogo);
        jobTitle=(TextView)findViewById(R.id.jobCompnyTitle);
        companyName=(TextView)findViewById(R.id.companyName);
        jobLocation=(TextView)findViewById(R.id.jobLocation);
        jobTime=(TextView)findViewById(R.id.jobwork);
        jobSalary=(TextView)findViewById(R.id.jobSalary);
        jobDescrption=(TextView)findViewById(R.id.describtionDetails);
        jobTag=(TextView)findViewById(R.id.jobTag);
        jobDetails=(TextView)findViewById(R.id.jobDetails);
        mSkillsRecyclerView = (RecyclerView) findViewById(R.id.skills_recyclerView);
        //----------------------------------
        //ChipsLayoutManager Custem Layout
        ChipsLayoutManager spanLayoutManager = ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL).build();

        //Add spacing between items in recyclerView
        mSkillsRecyclerView.addItemDecoration(new SpacingItemDecoration(getResources().getDimensionPixelOffset(R.dimen.item_space),
                getResources().getDimensionPixelOffset(R.dimen.item_space)) );
        mSkillsRecyclerView.setLayoutManager(spanLayoutManager);
        skillsAdapter = new SkillsRecyclerAdapter(mSkillsData,this);
        mSkillsRecyclerView.setAdapter(skillsAdapter);
        // get id and password from shared
        userID= PrefSingleton.getPref("keyuserid",JobDetailsFragment.this);
        currentUserPassword= PrefSingleton.getPref("keypasswordd",JobDetailsFragment.this);
        Log.v("iddddddd",""+userID);
        Log.v("passsssssssssss",""+currentUserPassword);

    }
    private void getDetailData(){
        Bundle bundle = getIntent().getExtras();
        ModelHome modelHome= bundle.getParcelable("Model_Key");
       // Log.v("newData",modelHome.getTitle().toString());
       String job_Title= modelHome.getTitle().toString();
       String company_Name= modelHome.getTxtWork().toString();
       String logo= modelHome.getLogo().toString();
       String location=  modelHome.getTxtLocation().toString();
       String salary= modelHome.getJobSalary().toString();
       String description= modelHome.getDescription().toString();
       String tag= modelHome.getTxtTag().toString();
        jobID=  modelHome.getJobID().toString();
        jobType=  modelHome.getJobTimeType().toString();
        Log.v("jobType",jobType);
        //Log.v("jobID",jobID);
         String type=null;
        if(jobType.equals("1")){
            type="Full-time";
        }else if(jobType.equals("2")){
            type="Part-time";
        }else if(jobType.equals("3")){
            type="Internship";
        }else if(jobType.equals("4")){
            type="Freelance";
        }else if(jobType.equals("5")){
            type="Remote";
        }else {
            type="Not Found";
        }
        jobTitle.setText(job_Title);
        companyName.setText(company_Name);
        String url= URLS.imageUrl+logo;
        Picasso.with(this).load(url).into(company_logo);
        jobLocation.setText(location);
        jobTime.setText(type);
        jobSalary.setText(salary+" $");
        jobDescrption.setText(description);
        jobTag.setText(tag);
       // jobDetails.setText(Html.fromHtml(modelHome.getJobDetials().toString()));
    }
private void alertDialog(){
    // custom dialog
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.custom_alert);
    TextView text1 = (TextView) dialog.findViewById(R.id.text1);
    TextView text2 = (TextView) dialog.findViewById(R.id.text2);
    TextView text3 = (TextView) dialog.findViewById(R.id.text3);
    TextView text4 = (TextView) dialog.findViewById(R.id.text4);
    Fonts.set(new TextView[]{text1, text2,text3,text4}, JobDetailsFragment.this, 1);

    Button ButtonNo = (Button) dialog.findViewById(R.id.button_no);
    Button ButtonYes = (Button) dialog.findViewById(R.id.button_yes);
    Fonts.set(new Button[]{ButtonNo,ButtonYes}, JobDetailsFragment.this, 1);
    // if button is clicked, close the custom dialog
    ButtonNo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    });

    // if button is clicked, close the custom dialog
    ButtonYes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    });

    dialog.show();
}

    public void applyForJob(View view) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLS.applyJob,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                           String state= jsonObject.getString("state");
                            if(state.equals("1")){
                                String examID= jsonObject.getString("examId");
                                Toast.makeText(JobDetailsFragment.this, examID, Toast.LENGTH_SHORT).show();
                                alertDialog();


                            }else if(state.equals("2")){
                                Toast.makeText(JobDetailsFragment.this, "Error, Please try again", Toast.LENGTH_SHORT).show();
                            }else if(state.equals("3")){
                                Toast.makeText(JobDetailsFragment.this, "You are Already Applied", Toast.LENGTH_SHORT).show();
                                alertDialog();
                            }else if(state.equals("4")){
                                Toast.makeText(JobDetailsFragment.this, "There is no job with this ID, Please try again", Toast.LENGTH_SHORT).show();
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
                params.put("jobId", jobID);
                params.put("penguinUserId", userID);
                params.put("penguinUserPass", currentUserPassword);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);

    }

    public void backTo(View view) {
        finish();

    }
}