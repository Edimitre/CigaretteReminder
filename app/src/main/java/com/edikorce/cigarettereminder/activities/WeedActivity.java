package com.edikorce.cigarettereminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import com.edikorce.cigarettereminder.R;
import com.edikorce.cigarettereminder.dateTimeUtilities.DateTimeUtilities;
import com.edikorce.cigarettereminder.systemServices.Reminder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WeedActivity extends AppCompatActivity {

    FloatingActionButton btnLightWeed;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switcher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weed);

        loadObjects();

        setListeners();

    }

    private void loadObjects(){
        btnLightWeed = findViewById(R.id.btn_light_weed);

        switcher = findViewById(R.id.switcher_weed);

    }

    private void setListeners(){
        btnLightWeed.setOnClickListener(v->{

            lightCigar();

        });
    }

    private void lightCigar(){

        Reminder reminder = new Reminder(getApplicationContext());

        DateTimeUtilities dt = new DateTimeUtilities();
        if (switcher.isChecked()){

            reminder.setReminder(getApplicationContext(), dt.getHourInt() + 5, 0);
            Toast.makeText(getApplicationContext(), "Do kujtoheni per 5 ore", Toast.LENGTH_LONG).show();

        }else{

            reminder.setReminder(getApplicationContext(), dt.getHourInt() + 3, dt.getMinuteInt());
            Toast.makeText(getApplicationContext(), "Do kujtoheni per 3 ore", Toast.LENGTH_LONG).show();

        }


    }
}