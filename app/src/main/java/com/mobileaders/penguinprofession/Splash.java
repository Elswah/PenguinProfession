package com.mobileaders.penguinprofession;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobileaders.penguinprofession.Activity.LoginActivity;

public class Splash extends AppCompatActivity {

    Runnable run;
    Handler  handler = new Handler();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        run = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, LoginActivity.class));
//                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_out_left);
                finish();
            }
        };

        handler.postDelayed(run,2000);

    }
}
