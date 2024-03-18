package com.example.healthtrack.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.healthtrack.R;
import com.example.healthtrack.database.PhotoDatabaseHelper;
import com.example.healthtrack.models.Photo;

import java.util.List;

public class PhotoAdapter extends ArrayAdapter<Photo> {
    private List<Photo> photoList;
    private Context context;
    private PhotoDatabaseHelper dbHelper;

    public PhotoAdapter(Context context, List<Photo> photoList) {
        super(context, 0, photoList);
        this.context = context;
        this.photoList = photoList;
        this.dbHelper = new PhotoDatabaseHelper(getContext());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_photo, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.photo_image);
        TextView descriptionTextView = convertView.findViewById(R.id.photo_description);
        TextView dateTextView = convertView.findViewById(R.id.photo_date);

        Photo currentPhoto = photoList.get(position);
        imageView.setImageBitmap(currentPhoto.getPhoto());
        descriptionTextView.setText(currentPhoto.getDescription());
        dateTextView.setText(currentPhoto.getDate());

        imageView.setOnClickListener(v -> showFullImage(photoList.get(position).getPhoto(), position));

        return convertView;
    }

    private void showFullImage(Bitmap imageBitmap,int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.display_photo, null);
        ImageView fullImageView = dialogView.findViewById(R.id.display_photo_image);
        Button deleteButton = dialogView.findViewById(R.id.btn_delete_photo);
        Button hideButton = dialogView.findViewById(R.id.btn_hide_photo);

        fullImageView.setImageBitmap(imageBitmap);

        fullImageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        fullImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        fullImageView.setAdjustViewBounds(true);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        deleteButton.setOnClickListener(v -> {
            dbHelper.deletePhoto(photoList.get(position).getId());
            photoList.remove(photoList.get(position));
            notifyDataSetChanged();
            dialog.dismiss();
        });

        hideButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
