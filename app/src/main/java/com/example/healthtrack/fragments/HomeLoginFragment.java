package com.example.healthtrack.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthtrack.R;
import com.example.healthtrack.utils.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeLoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeLoginFragment newInstance(String param1, String param2) {
        HomeLoginFragment fragment = new HomeLoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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