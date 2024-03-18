package com.example.healthtrack.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.healthtrack.MainActivity;
import com.example.healthtrack.R;
import com.example.healthtrack.database.DBHelper;
import com.example.healthtrack.database.FavorisDatabaseHelper;
import com.example.healthtrack.database.NoteDatabaseHelper;
import com.example.healthtrack.database.PhotoDatabaseHelper;
import com.example.healthtrack.utils.SessionManager;
import com.example.healthtrack.utils.Utils;

import java.util.Locale;
import java.util.Objects;

public class AccountFragment extends Fragment {
    private Button buttonLogout;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSave;

    private Button buttonDelete;
    private DBHelper dbHelper;

    private Spinner languageSpinner;

    public AccountFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        dbHelper = new DBHelper(getActivity());

        buttonLogout = view.findViewById(R.id.buttonLogout);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonDelete = view.findViewById(R.id.buttonDeleteAccount);
        languageSpinner = view.findViewById(R.id.languageSpinner);

        SessionManager sessionManager = new SessionManager(getActivity());

        if (sessionManager.getEmail().equals("guest")) {
            editTextUsername.setEnabled(false);
            editTextEmail.setEnabled(false);
            editTextPassword.setEnabled(false);
            editTextConfirmPassword.setEnabled(false);
            buttonSave.setEnabled(false);
        }

        editTextUsername.setHint(sessionManager.getUsername());
        editTextEmail.setHint(sessionManager.getEmail());
        editTextPassword.setHint("******");
        editTextConfirmPassword.setHint("******");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        String defaultLanguage = Locale.getDefault().getLanguage();
        int defaultLanguageIndex = -1;
        String[] languagesArray = getResources().getStringArray(R.array.languages);
        for (int i = 0; i < languagesArray.length; i++) {
            if (languagesArray[i].toLowerCase().startsWith(defaultLanguage)) {
                defaultLanguageIndex = i;
                break;
            }
        }

        if (defaultLanguageIndex != -1) {
            languageSpinner.setSelection(defaultLanguageIndex);
        }

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedLanguage = parentView.getItemAtPosition(position).toString();

                Log.d("test5", selectedLanguage);

                String selectedLanguageCode;
                if (selectedLanguage.equals("Francais")) {
                    selectedLanguageCode = "fr";
                } else if (selectedLanguage.equals("English")) {
                    selectedLanguageCode = "en";
                } else {
                    selectedLanguageCode = "en";
                }

                Log.d("test3", selectedLanguageCode);

                if(!Objects.equals(sessionManager.getLanguagePref(), selectedLanguageCode)) {
                    sessionManager.setLanguagePref(selectedLanguageCode);
                    Utils.setLocale(getContext(),selectedLanguageCode);
                    getActivity().recreate();
                }
            }

            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        buttonLogout.setOnClickListener(v -> {
            SessionManager sessionManager1 = new SessionManager(getActivity());
            sessionManager1.logoutUser();

            ((MainActivity) getActivity()).updateMenuVisibility();
            Toast.makeText(getActivity(), "Logout successful", Toast.LENGTH_SHORT).show();
        });

        buttonSave.setOnClickListener(v -> saveChanges());

        buttonDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to delete the account ?");
            builder.setPositiveButton("Yes", (dialog, which) -> deleteAccount());
            builder.setNegativeButton("No", (dialog, which) -> {
            });
            builder.show();
        });


        return view;
    }

    private void saveChanges() {
        String newUsername = editTextUsername.getText().toString().trim();
        String newEmail = editTextEmail.getText().toString().trim();
        String newPassword = editTextPassword.getText().toString().trim();
        String newConfirmPassword = editTextConfirmPassword.getText().toString().trim();
        SessionManager sessionManager = new SessionManager(getActivity());

        if (!newPassword.isEmpty() && !newConfirmPassword.isEmpty() && !newPassword.equals(newConfirmPassword)) {
            Toast.makeText(getActivity(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newUsername.isEmpty() && !newUsername.equals(sessionManager.getUsername())) {
            dbHelper.updateUsername(sessionManager.getEmail(), newUsername);
            sessionManager.setUsername(newUsername);
        }

        if (!newEmail.isEmpty() && !newEmail.equals(sessionManager.getEmail())) {
            dbHelper.updateEmail(sessionManager.getEmail(), newEmail);
            sessionManager.setEmail(newEmail);
        }

        if (!newPassword.isEmpty()) {
            dbHelper.updatePassword(sessionManager.getEmail(), newPassword);
        }

        Toast.makeText(getActivity(), "Modifications enregistrées avec succès", Toast.LENGTH_SHORT).show();
    }

    private void deleteAccount() {
        SessionManager sessionManager = new SessionManager(getActivity());
        NoteDatabaseHelper noteDatabaseHelper = new NoteDatabaseHelper(getActivity());
        PhotoDatabaseHelper photoDatabaseHelper = new PhotoDatabaseHelper(getActivity());
        FavorisDatabaseHelper favorisDatabaseHelper = new FavorisDatabaseHelper(getActivity());
        noteDatabaseHelper.deleteAllNotesForUser(sessionManager.getEmail());
        photoDatabaseHelper.deleteAllPhotosFromUser(sessionManager.getEmail());
        favorisDatabaseHelper.deleteAllFavorisForUser(sessionManager.getEmail());
        dbHelper.deleteUser(sessionManager.getEmail());
        sessionManager.logoutUser();
        ((MainActivity) getActivity()).updateMenuVisibility();
        Toast.makeText(getActivity(), "Account deleted successfully", Toast.LENGTH_SHORT).show();
    }
}