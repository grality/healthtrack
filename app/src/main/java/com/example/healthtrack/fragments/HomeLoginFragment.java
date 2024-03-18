package com.example.healthtrack.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthtrack.R;
import com.example.healthtrack.utils.SessionManager;


public class HomeLoginFragment extends Fragment {


    public HomeLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TextView textViewWelcome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_login, container, false);

        textViewWelcome = view.findViewById(R.id.textViewWelcome);

        displayWelcomeMessage();

        return view;
    }

    private void displayWelcomeMessage() {

        ///Affiche un message de retour
        SessionManager sessionManager = new SessionManager(getActivity());
        String username = sessionManager.getUsername();

        if (username != null && !username.isEmpty()) {
            String welcomeMessage = getString(R.string.welcome_back) + " " + username;
            textViewWelcome.setText(welcomeMessage);
        } else {
            textViewWelcome.setText(getString(R.string.welcome_back));
        }
    }
}