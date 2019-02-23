package com.example.souravmunjal.doctorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button b1;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
           new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                android.content.Intent intent = new android.content.Intent(MainActivity.this,Login.class);
                //Intent intent = new Intent(Mainactivity1.this, Search2.class);
                startActivity(intent);
                finish();
            }

        }, 2000);
    }
}
