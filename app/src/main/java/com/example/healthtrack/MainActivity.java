    package com.example.healthtrack;

    import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

    import android.content.Intent;
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
    import com.example.healthtrack.fragments.ExercisesFragment;
    import com.example.healthtrack.fragments.HomeFragment;
    import com.example.healthtrack.fragments.HomeLoginFragment;
    import com.example.healthtrack.fragments.LoginFragment;
    import com.example.healthtrack.fragments.MapFragment;
    import com.example.healthtrack.fragments.ProgressFragment;
    import com.example.healthtrack.fragments.RegisterFragment;
    import com.example.healthtrack.utils.SessionManager;
    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.model.LatLng;
    import com.google.android.gms.maps.model.MarkerOptions;
    import com.example.healthtrack.service.CheckImageService;

    public class MainActivity extends AppCompatActivity {
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
                } else if (itemId == R.id.action_exercices) {
                    replaceFragment(new ExercisesFragment());
                }else if (itemId == R.id.action_progress) {
                    replaceFragment(new ProgressFragment());
                }
                return true;
            });



            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
            }

            Intent CheckImageService = new Intent(this, CheckImageService.class);
            Log.d(TAG, "TACHE DE FOND LANCE");
            startService(CheckImageService);
        }





        public void updateMenuVisibility() {
            Menu menu = binding.bottomNavigationView.getMenu();
            SessionManager sessionManager = new SessionManager(this);
            Log.d("LOGIN", String.valueOf(sessionManager.isLoggedIn()));

            menu.clear();

            if (sessionManager.isLoggedIn()) {
                replaceFragment(new HomeLoginFragment());
                binding.bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu_login);
                if ("guest".equals(sessionManager.getEmail())) {
                    menu.findItem(R.id.action_progress).setVisible(false);
                }
            }
            else {
                replaceFragment(new HomeFragment());
                binding.bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu);
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            //updateMenuVisibility();
            //SessionManager sessionManager = new SessionManager(this);
            //setLocale(this, sessionManager.getLanguagePref());
            //if(!Objects.equals(sessionManager.getLanguagePref(), Locale.getDefault().getLanguage())) {
            //    recreate();
            //}
        }

        protected void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
        }
    }
