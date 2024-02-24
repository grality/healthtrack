package com.example.healthtrack.fragments;

import static com.example.healthtrack.utils.Utils.hashPassword;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthtrack.R;
import com.example.healthtrack.database.DBHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterFragment extends Fragment {

    private EditText editTextEmail, editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;

    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize views
        editTextEmail = view.findViewById(R.id.editTextEmailRegister);
        editTextUsername = view.findViewById(R.id.editTextUsernameRegister);
        editTextPassword = view.findViewById(R.id.editTextPasswordRegister);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPasswordRegister);
        buttonRegister = view.findViewById(R.id.buttonRegister);

        dbHelper = new DBHelper(getActivity());

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        return view;
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (password.equals(confirmPassword)) {
            // Hash the password
            String hashedPassword = hashPassword(password);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("email", email);
            values.put("username", username);
            values.put("password", hashedPassword); // Store the hashed password

            long newRowId = db.insert("users", null, values);

            if (newRowId != -1) {
                Toast.makeText(getActivity(), "User registered successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error registering user!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }
    }
}
