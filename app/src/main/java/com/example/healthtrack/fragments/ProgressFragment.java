package com.example.healthtrack.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.healthtrack.Adapter.PhotoAdapter;
import com.example.healthtrack.R;
import com.example.healthtrack.database.PhotoDatabaseHelper;
import com.example.healthtrack.models.Photo;
import com.example.healthtrack.utils.SessionManager;
import com.example.healthtrack.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private Button btnTakePhoto;
    private ListView photoListView;
    private List<Photo> photoList;
    private PhotoAdapter photoAdapter;
    private PhotoDatabaseHelper dbHelper;

    public ProgressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        btnTakePhoto = view.findViewById(R.id.btn_take_photo);
        photoListView = view.findViewById(R.id.photoListView);

        photoList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(getContext(), photoList);
        photoListView.setAdapter(photoAdapter);

        dbHelper = new PhotoDatabaseHelper(getContext());

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        SessionManager sessionManager = new SessionManager(getContext());
        List<Photo> dbPhotos = dbHelper.getAllPhotos(sessionManager.getEmail());

        photoList.clear();
        photoList.addAll(dbPhotos);
        photoAdapter.notifyDataSetChanged();

        return view;
    }

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else {
            startCameraIntent();
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    startCameraIntent();
                }
            });

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        showDescriptionDialog(imageBitmap);
                    }
                }
            }
    );

    private void startCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            cameraLauncher.launch(takePictureIntent);
        }
    }

    private void showDescriptionDialog(Bitmap imageBitmap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_photo_description, null);
        builder.setView(dialogView);

        ImageView imageView = dialogView.findViewById(R.id.imageView);
        EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        imageView.setImageBitmap(imageBitmap);
        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editTextDescription.getText().toString();
                String currentDate = Utils.getCurrentDate();

                if (imageBitmap != null) {
                    Photo photo = new Photo(imageBitmap, description, currentDate);
                    SessionManager sessionManager = new SessionManager(getContext());
                    dbHelper.addPhoto(photo, sessionManager.getEmail());
                    photoList.add(0, photo);
                    photoAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    Log.d("Progress", "ImageBitmap is null");
                    // Gérer le cas où l'image est null (peut-être afficher un message à l'utilisateur)
                }
            }
        });

        dialog.show();
    }
}

