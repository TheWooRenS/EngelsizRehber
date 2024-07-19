package com.example.engelsizrehber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.engelsizrehber.databinding.ActivityNavbarBinding;
import com.example.engelsizrehber.util.ModeTheme;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class NavbarActivity extends ModeTheme {

    ActivityNavbarBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);
        binding = ActivityNavbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(zamanMesaji());

        if (intent.getBooleanExtra("snack",false) == true) {
            Snackbar.make(findViewById(android.R.id.content), intent.getStringExtra("message"), Snackbar.LENGTH_SHORT).show();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        replaceFragment(new EventsFragment());
        binding.bottomNavigationView.setBackground(null);


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.events){
                replaceFragment(new EventsFragment());
                toolbar.setVisibility(View.VISIBLE);
            }
            else if (itemId == R.id.park){
                replaceFragment(new ParkFragment());
                toolbar.setVisibility(View.VISIBLE);
            }
            else if (itemId == R.id.forum){
                replaceFragment(new ForumFragment());
                toolbar.setVisibility(View.VISIBLE);
            }
            else if (itemId == R.id.places){
                replaceFragment(new PlacesFragment());
                toolbar.setVisibility(View.VISIBLE);
            }
            else if (itemId == R.id.ai){
                replaceFragment(new AiFragment());
                toolbar.setVisibility(View.VISIBLE);

            }
            return true;
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.notifications){
            Intent intent = new Intent(getApplicationContext(),NotificationsActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.account){
            Intent intent = new Intent(getApplicationContext(),AccountActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.settings){
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

    }

    public String zamanMesaji() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 10) {
            return "Günaydın, " + sharedPreferences.getString("loginusername", "");
        } else if (hour >= 10 && hour < 17) {
            return "Tünaydın, " + sharedPreferences.getString("loginusername", "");
        } else if (hour >= 17 && hour < 21) {
            return "İyi Akşamlar, " + sharedPreferences.getString("loginusername", "");
        } else {
            return "İyi Geceler, " + sharedPreferences.getString("loginusername", "");
        }
    }

}