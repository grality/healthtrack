package com.example.healthtrack.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthtrack.R;
import com.example.healthtrack.models.Exercise;

import java.util.List;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {

    private Context mContext;
    private List<Exercise> mExercises;

    public ExerciseAdapter(Context context, List<Exercise> exercises) {
        super(context, 0, exercises);
        mContext = context;
        mExercises = exercises;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.exercise_item, parent, false);
        }

        Exercise currentExercise = mExercises.get(position);

        TextView titleTextView = listItem.findViewById(R.id.titleTextView);
        titleTextView.setText(currentExercise.getTitle());

        TextView descriptionTextView = listItem.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(currentExercise.getDescription());

        TextView muscleGroupsTextView = listItem.findViewById(R.id.muscleGroupsTextView);
        muscleGroupsTextView.setText(currentExercise.getMuscleType());

        ImageView imageView = listItem.findViewById(R.id.imageView);
        imageView.setImageResource(currentExercise.getImageResource());

        return listItem;
    }
}
