package com.edikorce.cigarettereminder.activities;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edikorce.cigarettereminder.R;
import com.edikorce.cigarettereminder.dateTimeUtilities.DateTimeUtilities;
import com.edikorce.cigarettereminder.entities.PacketCigarettes;
import com.edikorce.cigarettereminder.systemServices.Reminder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnLightCigar;
    TextView nrOfCigars, valueSpendedOnCigars;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    private int remainingCigars;
    private int spendedValue;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadObjects();

        if (emptyPacket()){
            openAddPacketDialog();
        }

        createNotificationChannel();
        setListeners();
        showUserValues();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weed_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.btn_weed_activity) {
            startActivity(new Intent(MainActivity.this, WeedActivity.class));
        }else if (item.getItemId() == R.id.btn_reset_everything){
            openResetEverythingDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNotificationChannel() {
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("cigarette_reminder_channel",
                    "expenseTrackerNotificationChannel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void loadObjects(){
        btnLightCigar = findViewById(R.id.btn_light_cigar);
        nrOfCigars = findViewById(R.id.nr_of_cigars_text);
        valueSpendedOnCigars = findViewById(R.id.value_spended_text);
        switcher = findViewById(R.id.switcher);
        sharedPreferences = getSharedPreferences("cigarDatabase", MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    private void setListeners(){
        btnLightCigar.setOnClickListener(v->{

            lightCigar();

        });
    }

    private boolean emptyPacket(){
        int nrOfCigarettes = sharedPreferences.getInt("nrOfCigars", 0);
        return nrOfCigarettes == 0;
    }

    private void openAddPacketDialog() {

        EditText inputValue = new EditText(getApplication());
        inputValue.setInputType(InputType.TYPE_CLASS_NUMBER);


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(inputValue);
        dialog.setTitle("Nr i cigereve eshte 0 ");
        dialog.setMessage("Ju lutem shtoni pakete \nbashke me vleren e saj");
        dialog.setNegativeButton("Mbyll", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setPositiveButton("Shto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = inputValue.getText().toString();
                if (!value.isEmpty()) {
                    PacketCigarettes packetCigarettes = new PacketCigarettes(20, Integer.parseInt(value));
                    addPacket(packetCigarettes);
                } else {
                    Toast.makeText(getApplicationContext(), "Vlera e paketes nuk mund te jete bosh", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();

    }

    private void openResetEverythingDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Cigarette Reminder ");
        dialog.setMessage("Are U Sure U Want To Delete Everyting ?");
        dialog.setNegativeButton("Jo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setPositiveButton("Po", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               resetEverything();
            }
        });
        dialog.show();

    }

    private void addPacket(PacketCigarettes packetCigarettes){

        editor.putInt("nrOfCigars", packetCigarettes.getNrOfCigars());


        int valueSpended = sharedPreferences.getInt("valueSpended", 20) + packetCigarettes.getValue();
        editor.putInt("valueSpended", valueSpended);

        editor.apply();
        showUserValues();

    }

    private void lightCigar(){

        Reminder reminder = new Reminder(getApplicationContext());
        sharedPreferences = getSharedPreferences("cigarDatabase", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        remainingCigars = sharedPreferences.getInt("nrOfCigars",20) - 1;

        editor.putInt("nrOfCigars", remainingCigars);
        editor.apply();
        showUserValues();
        DateTimeUtilities dt = new DateTimeUtilities();
        if (switcher.isChecked()){
            editor.putInt("timestamp", 2);
            editor.apply();
            reminder.setReminder(getApplicationContext(), dt.getHourInt() + 1, dt.getMinuteInt() + 30);
            Toast.makeText(getApplicationContext(), "Do kujtoheni per 1 ore e 30 min " , Toast.LENGTH_LONG).show();

        }else{
            editor.putInt("timestamp", 1);
            editor.apply();
            reminder.setReminder(getApplicationContext(), dt.getHourInt() + 1, dt.getMinuteInt());
            Toast.makeText(getApplicationContext(), "Do kujtoheni per 1 ore" , Toast.LENGTH_LONG).show();

        }


    }

    private void showUserValues(){

        Thread thread = new Thread(()->{

            remainingCigars = sharedPreferences.getInt("nrOfCigars", 20);

            spendedValue = sharedPreferences.getInt("valueSpended", 230);

        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        nrOfCigars.setText(String.valueOf(remainingCigars));

        valueSpendedOnCigars.setText("Vlera shpenzuar eshte : \n" + spendedValue + " lek");
    }

    private void resetEverything(){

        Thread thread = new Thread(()->{

            editor.putInt("nrOfCigars", 0);
            editor.putInt("valueSpended", 0);
            editor.apply();


        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Reminder reminder = new Reminder(getApplicationContext());
        reminder.clearReminder();

        showUserValues();
        Toast.makeText(getApplicationContext(), "Te dhenat u fshijne", Toast.LENGTH_SHORT).show();

    }

}