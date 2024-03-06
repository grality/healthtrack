package com.example.healthtrack.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthtrack.R;

import java.util.List;

public class PhotoAdapter extends ArrayAdapter<Bitmap> {
    private List<Bitmap> photoList;
    private Context context;

    public PhotoAdapter(Context context, List<Bitmap> photoList) {
        super(context, 0, photoList);
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_photo, parent, false);
            imageView = convertView.findViewById(R.id.photo_image);
            convertView.setTag(imageView);
        } else {
            imageView = (ImageView) convertView.getTag();
        }

        Bitmap photo = getItem(position);
        imageView.setImageBitmap(photo);

        return convertView;
    }
}

