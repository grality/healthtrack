    package com.example.healthtrack;

    import static com.example.healthtrack.utils.Utils.setLocale;

    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.ActionBar;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import com.example.healthtrack.databinding.ActivityMainBinding;
    import com.example.healthtrack.fragments.AccountFragment;
    import com.example.healthtrack.fragments.HomeFragment;
    import com.example.healthtrack.fragments.HomeLoginFragment;
    import com.example.healthtrack.fragments.LoginFragment;
    import com.example.healthtrack.fragments.MapFragment;
    import com.example.healthtrack.fragments.NotesFragment;
    import com.example.healthtrack.fragments.RegisterFragment;
    import com.example.healthtrack.utils.SessionManager;
    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.model.LatLng;
    import com.google.android.gms.maps.model.MarkerOptions;

    import java.util.Locale;
    import java.util.Objects;

    public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
        private ActivityMainBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);



            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            updateMenuVisibility();
            binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    replaceFragment(new HomeFragment());
                } else if (itemId == R.id.action_register) {
                    replaceFragment(new RegisterFragment());
                } else if (itemId == R.id.action_login) {
                    replaceFragment(new LoginFragment());
                } else if (itemId == R.id.action_home_login) {
                    replaceFragment(new HomeLoginFragment());
                } else if (itemId == R.id.action_account) {
                    replaceFragment(new AccountFragment());
                } else if (itemId == R.id.action_map) {
                    replaceFragment(new MapFragment());
                } else if (itemId == R.id.action_notes) {
                    replaceFragment(new NotesFragment());
                }
                return true;
            });



                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayShowTitleEnabled(true);
                    actionBar.setTitle("HealthTrack");
                    actionBar.setLogo(R.drawable.logo_health_track);
                    actionBar.setDisplayUseLogoEnabled(true);
                }
        }





        public void updateMenuVisibility() {
            Menu menu = binding.bottomNavigationView.getMenu();
            SessionManager sessionManager = new SessionManager(this);
            Log.d("LOGIN", String.valueOf(sessionManager.isLoggedIn()));

            menu.clear();

            if (sessionManager.isLoggedIn()) {
                replaceFragment(new HomeLoginFragment());
                binding.bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu_login);
            }
            else {
                replaceFragment(new HomeFragment());
                binding.bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu);
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            updateMenuVisibility();
            SessionManager sessionManager = new SessionManager(this);
            setLocale(this, sessionManager.getLanguagePref());
            if(!Objects.equals(sessionManager.getLanguagePref(), Locale.getDefault().getLanguage())) {
                recreate();
            }
        }

        protected void replaceFragment(Fragment fragment) {
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
