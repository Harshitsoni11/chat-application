package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsapp.Adapter.FragmentAdapter;
import com.example.whatsapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        binding.viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }
    // menu ko java mai laikr aanai kai liye use this method

    @Override
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // menu mai sai option select

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // item ko kis hisab sai dena hai
        switch (item.getItemId()){
            case R.id.setting:
               Intent intents=new Intent(MainActivity.this,SettingActivity.class);
               startActivity(intents);
                break;

            case R.id.logout:
                auth.signOut(); // this one line use for sign out
                Intent intent=new Intent(MainActivity.this,Sign_in.class);
                startActivity(intent);
                break;
            case R.id.GroupChat:
                Intent intentt=new Intent(MainActivity.this,GroupChatActivity.class);
                startActivity(intentt);
        }
        return super.onOptionsItemSelected(item);
    }
}