package com.example.healthtrack.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.healthtrack.MainActivity;
import com.example.healthtrack.R;
import com.example.healthtrack.utils.SessionManager;


public class HomeFragment extends Fragment {


    private Button buttonLoginGuest;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        buttonLoginGuest = view.findViewById(R.id.buttonLoginGuest);

        ///Utilisateur Guest
        buttonLoginGuest.setOnClickListener(v -> {
            SessionManager sessionManager = new SessionManager(getActivity());
            sessionManager.loginUser("guest", "Guest");
            ((MainActivity) getActivity()).updateMenuVisibility();
        });

        return view;
    }
}