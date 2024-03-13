package com.example.healthtrack.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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


    // Autres méthodes et variables de votre fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        dbHelper = new DBHelper(getActivity());

        // Récupérer une référence vers le bouton Logout
        buttonLogout = view.findViewById(R.id.buttonLogout);
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

                Log.d("test5", String.valueOf(selectedLanguage));

                String selectedLanguageCode;
                if (selectedLanguage.equals("Francais")) {
                    selectedLanguageCode = "fr";
                } else if (selectedLanguage.equals("English")) {
                    selectedLanguageCode = "en";
                } else {
                    selectedLanguageCode = "en";
                }

                Log.d("test3", String.valueOf(selectedLanguageCode));

                if(!Objects.equals(sessionManager.getLanguagePref(), selectedLanguageCode)) {
                    sessionManager.setLanguagePref(selectedLanguageCode);
                    Utils.setLocale(getContext(),selectedLanguageCode);
                    getActivity().recreate();
                }
            }

            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(getActivity());
                sessionManager.logoutUser();

                ((MainActivity) getActivity()).updateMenuVisibility();
                Toast.makeText(getActivity(), "Logout successful", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete the account ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       deleteAccount();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
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