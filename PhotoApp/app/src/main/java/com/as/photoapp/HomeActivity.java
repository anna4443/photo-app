package com.as.photoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.as.photoapp.fragments.HomeFragment;
import com.as.photoapp.fragments.PhotosFragment;
import com.as.photoapp.fragments.ProfileFragment;
import com.as.photoapp.fragments.SetupFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        setupListeners();

        getHome();

    }

    private void setupListeners() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        getHome();
                        break;
                    case R.id.action_profile:
                        getProfile();
                        break;
                    case R.id.action_photos:
                        getPhotos();
                        break;
                    case R.id.action_setup:
                        getSetup();
                        break;
                }
                return true;
            }
        });
    }

    private void getSetup() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameContainer, new SetupFragment()).commit();
    }

    private void getHome() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameContainer, new HomeFragment()).commit();
    }

    private void getPhotos() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameContainer, new PhotosFragment()).commit();
    }

    private void getProfile() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameContainer, new ProfileFragment()).commit();
    }

    private void init() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }


}
