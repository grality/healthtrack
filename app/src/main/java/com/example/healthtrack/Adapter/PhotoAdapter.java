package com.example.healthtrack.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthtrack.R;
import com.example.healthtrack.models.Photo;

import java.util.List;

public class PhotoAdapter extends ArrayAdapter<Photo> {
    private List<Photo> photoList;
    private Context context;

    public PhotoAdapter(Context context, List<Photo> photoList) {
        super(context, 0);
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView;
        TextView imageDescription;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_photo, parent, false);
            imageView = convertView.findViewById(R.id.photo_image);
            imageDescription = convertView.findViewById(R.id.photo_description);
            convertView.setTag(imageView);
            convertView.setTag(imageDescription);
        } else {
            imageView = (ImageView) convertView.getTag();
            imageDescription =(TextView) convertView.getTag();
        }

        Photo photo = getItem(position);

        imageView.setImageBitmap(photo.getPhoto());
        imageDescription.setText(photo.getDescription());

        return convertView;
    }
}

