package com.example.healthtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.healthtrack.databinding.ActivityMainBinding;
import com.example.healthtrack.fragments.HomeFragment;
import com.example.healthtrack.fragments.LoginFragment;
import com.example.healthtrack.fragments.RegisterFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.bottomNavigationView.setOnItemSelectedListener(item->{
            if (item.getItemId() == R.id.action_home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.action_register) {
                replaceFragment(new RegisterFragment());
            } else if (item.getItemId() == R.id.action_login) {
                replaceFragment(new LoginFragment());
            }else if (item.getItemId() == R.id.action_map) {
                replaceMapFragment();
            }
            return true;
        });
    }

    private void replaceMapFragment() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mapFragment)
                .commit();
    }

    protected void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng location = new LatLng(47.5872965,1.3338788);
        googleMap.addMarker(new MarkerOptions().position(location).title("Blois"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12));
    }
}
