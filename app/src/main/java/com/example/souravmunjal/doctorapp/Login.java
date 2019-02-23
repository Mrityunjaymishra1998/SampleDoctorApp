package com.example.souravmunjal.doctorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        skip=(TextView) findViewById(R.id.textView16);
        Animation animation2 = new TranslateAnimation(-170,0, 0, 0);
        Animation animation3 = new TranslateAnimation(width-170,0, 0, 0);
        animation3.setDuration(1000);
        animation3.setFillAfter(true);
        animation2.setDuration(1000);
        animation2.setFillAfter(true);
        Button login,register;
        login=(Button) findViewById(R.id.button2);
        register=(Button) findViewById(R.id.button);
        login.startAnimation(animation2);
        register.startAnimation(animation3);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Home.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Login.this,Login2.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(Login.this,Createaccount.class);
            startActivity(i);
            }
        });
    }
}
