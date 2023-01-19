package com.example.whatsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatsapp.databinding.ActivitySplashBinding;

public class splashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);

                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(splashActivity.this,Sign_in.class);
                    startActivity(intent);

                }
            }
        }; thread.start();
    }
}