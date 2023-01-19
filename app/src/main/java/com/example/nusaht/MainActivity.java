package com.example.nusaht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.nusaht.pages.LandAct;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("NUSAH_SEC",0);
        boolean hasLoggedIn = pref.getBoolean("lg",false);

        if (hasLoggedIn)
        {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LandAct.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }
}