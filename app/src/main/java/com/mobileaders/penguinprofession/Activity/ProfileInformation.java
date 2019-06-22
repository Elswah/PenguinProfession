package com.mobileaders.penguinprofession.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobileaders.penguinprofession.Fragment.ProfileKindJob;
import com.mobileaders.penguinprofession.Fragment.ProfilePersonalInformationFrag;
import com.mobileaders.penguinprofession.Fragment.ProfileWorkExperience;
import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Utilities.Posting;
import com.mobileaders.penguinprofession.Utilities.PrefSingleton;

public class ProfileInformation extends AppCompatActivity {
    String userID,currentUserPassword;
    int fragCount = 0;
    Button bNext, bBack;
    ImageView i1, i2, i3;
    ProfilePersonalInformationFrag profilePersonalInformationFrag;
    ProfileWorkExperience profileWorkExperience;
    ProfileKindJob profileKindJob;
   // boolean check=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_personal_information);
        bNext = (Button) findViewById(R.id.next);
        bBack = (Button) findViewById(R.id.back);
        i1 = (ImageView) findViewById(R.id.i1);
        i2 = (ImageView) findViewById(R.id.i2);
        i3 = (ImageView) findViewById(R.id.i3);
        userID= PrefSingleton.getPref("keyuserid",ProfileInformation.this);
        currentUserPassword= PrefSingleton.getPref("keypasswordd",ProfileInformation.this);
        Log.v("uuuuuuuuuuuuuuuuu",""+userID);
        Log.v("ppppppppppppppppp",""+currentUserPassword);
        setFragmentForward();

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("xxx", "clicked");
                fragCount++;
                setFragmentForward();
//
//                if(check==true){
//
//                }else {
//                   Toast.makeText(ProfileInformation.this, "Fill All Field", Toast.LENGTH_SHORT).show();
//                }



            }
        });
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragCount--;
                setFragmentBackword();
            }
        });
    }



    //    transparent to next fragment and get data
    private void setFragmentForward() {

        switch (fragCount) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.Profile_container, new ProfilePersonalInformationFrag(), "profilePersonalInformation")
                        .commit();
                if (bBack.getVisibility() == View.VISIBLE) {
                    bBack.setVisibility(View.GONE);
                }
                break;
            case 1:
                profilePersonalInformationFrag = (ProfilePersonalInformationFrag) getSupportFragmentManager().findFragmentByTag("profilePersonalInformation");
                //profilePersonalInformationFrag.getData();
                if (!profilePersonalInformationFrag.validation()) {
                     //check=false;
                }
                else  {
                    profilePersonalInformationFrag.postData(userID, currentUserPassword);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Profile_container, new ProfileWorkExperience(), "profileWorkExperience")
                            .commit();
                    bBack.setVisibility(View.VISIBLE);
                    i2.setImageResource(R.drawable.pagination);
                    i1.setImageResource(R.drawable.pagination2);
                }
                SharedPreferences prefs = this.getSharedPreferences("myData",Context.MODE_PRIVATE);
                String  text1 = prefs.getString("keyeduId", null);
                Toast.makeText(ProfileInformation.this,"keyeduId "+text1, Toast.LENGTH_SHORT).show();
                  break;
            case 2:
                 profileWorkExperience = (ProfileWorkExperience) getSupportFragmentManager().findFragmentByTag("profileWorkExperience");


                  // profileWorkExperience.getData();
                if (!profileWorkExperience.validation()){
                    //check=false;

                }else {
                    profileWorkExperience.postData(userID, currentUserPassword);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Profile_container, new ProfileKindJob(), "ProfileKindJob")
                            .commit();
                    bNext.setText("Done");
                    bBack.setVisibility(View.VISIBLE);
                    i2.setImageResource(R.drawable.pagination2);
                    i3.setImageResource(R.drawable.pagination);
                }
                SharedPreferences pref = this.getSharedPreferences("myData",Context.MODE_PRIVATE);
                String  text2 = pref.getString("keyexperienceId", null);
                Toast.makeText(ProfileInformation.this,"keyexperienceId"+text2, Toast.LENGTH_SHORT).show();

                          break;

            case 3:
                 profileKindJob = (ProfileKindJob) getSupportFragmentManager().findFragmentByTag("ProfileKindJob");
                // profileKindJob.getData();
                if (!profileKindJob.validation()){
                    //check=false;
                }else {

                    profileKindJob.postData(userID, currentUserPassword);
                    bBack.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(this, Home.class);
                    startActivity(intent);
                }
                SharedPreferences preff = this.getSharedPreferences("myData",Context.MODE_PRIVATE);
                String  text3 = preff.getString("keyskillId", null);
                Toast.makeText(ProfileInformation.this,"keyskillId"+text3, Toast.LENGTH_SHORT).show();
                break;

        }
    }

//  transparent to previous fragment

    private void setFragmentBackword() {
        switch (fragCount) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Profile_container, new ProfilePersonalInformationFrag(), "profilePersonalInformation")
                        .commit();
                bBack.setVisibility(View.GONE);
                bNext.setText("Next");
                i1.setImageResource(R.drawable.pagination);
                i2.setImageResource(R.drawable.pagination2);
                break;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Profile_container, new ProfileWorkExperience(), "profileWorkExperience")
                        .commit();
                bNext.setText("Next");
                i2.setImageResource(R.drawable.pagination);
                i3.setImageResource(R.drawable.pagination2);
                break;
        }
    }



}
