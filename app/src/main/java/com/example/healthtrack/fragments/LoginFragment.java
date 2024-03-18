package com.example.healthtrack.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthtrack.MainActivity;
import com.example.healthtrack.R;
import com.example.healthtrack.database.DBHelper;
import com.example.healthtrack.models.User;
import com.example.healthtrack.utils.SessionManager;

public class LoginFragment extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    private DBHelper dbHelper;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize DBHelper
        dbHelper = new DBHelper(getActivity());

        // Initialize views
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);

        // Set click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        return view;
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        User user = dbHelper.getUserByEmail(email);

        if (user != null && dbHelper.checkPassword(email, password)) {
            SessionManager sessionManager = new SessionManager(getActivity());
            sessionManager.loginUser(email, user.getUsername());

            ((MainActivity) getActivity()).updateMenuVisibility();
            Toast.makeText(getActivity(), R.string.login_successfull, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show();
        }
    }
}
