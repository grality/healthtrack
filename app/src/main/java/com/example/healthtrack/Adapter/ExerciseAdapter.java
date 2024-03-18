package com.example.healthtrack.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.healthtrack.R;
import com.example.healthtrack.database.FavorisDatabaseHelper;
import com.example.healthtrack.fragments.NotesFragment;
import com.example.healthtrack.models.Exercise;
import com.example.healthtrack.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {

    private Context mContext;
    private List<Exercise> mExercises;
    private List<Exercise> mFilteredExercises;
    private FavorisDatabaseHelper favorisDatabaseHelper;
    private boolean showFavoriteOnly = false;

    public ExerciseAdapter(Context context, List<Exercise> exercises) {
        super(context, 0, exercises);
        mContext = context;
        mExercises = exercises;
        mFilteredExercises = new ArrayList<>();
    }

    public void updateList(List<Exercise> newExercises) {
        mExercises.clear();
        mExercises.addAll(newExercises);
        resetList();
        filterExercises();
        notifyDataSetChanged();
    }

    public void setShowFavoriteOnly(boolean showFavoriteOnly) {
        this.showFavoriteOnly = showFavoriteOnly;
        filterExercises();
        notifyDataSetChanged();
    }

    private void filterExercises() {
        mFilteredExercises.clear();

        if (!showFavoriteOnly) {
            mFilteredExercises.addAll(mExercises);
        } else {
            for (Exercise exercise : mExercises) {
                if (exercise.isFavorite()) {
                    mFilteredExercises.add(exercise);
                }
            }
        }
    }

    public void resetList() {
        mFilteredExercises.clear();
        mFilteredExercises.addAll(mExercises);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.exercise_item, parent, false);
        }

        if (!mFilteredExercises.isEmpty() && position < mFilteredExercises.size()) {
            final Exercise currentExercise = mFilteredExercises.get(position);

            TextView titleTextView = listItem.findViewById(R.id.titleTextView);
            titleTextView.setText(currentExercise.getTitle());

            TextView descriptionTextView = listItem.findViewById(R.id.descriptionTextView);
            descriptionTextView.setText(currentExercise.getDescription());

            TextView muscleGroupsTextView = listItem.findViewById(R.id.muscleGroupsTextView);
            muscleGroupsTextView.setText(currentExercise.getMuscleType());

            final ImageView starImageView = listItem.findViewById(R.id.starImageView);

            // Changer la couleur de l'étoile en fonction de l'état de favori de l'exercice
            if (currentExercise.isFavorite()) {
                starImageView.setImageResource(R.drawable.ic_star);
            } else {
                starImageView.setImageResource(R.drawable.ic_star_empty);
            }

            favorisDatabaseHelper = new FavorisDatabaseHelper(getContext());
            SessionManager sessionManager = new SessionManager(getContext());

            // OnClickListener pour l'étoile
            starImageView.setOnClickListener(v -> {
                // Inverser l'état de favori de l'exercice
                currentExercise.setFavorite(!currentExercise.isFavorite());
                if (currentExercise.isFavorite()) {
                    favorisDatabaseHelper.addFavoriteExercise(sessionManager.getEmail(), currentExercise.getId());
                    starImageView.setImageResource(R.drawable.ic_star);
                } else {
                    favorisDatabaseHelper.removeFavoriteExercise(sessionManager.getEmail(), currentExercise.getId());
                    starImageView.setImageResource(R.drawable.ic_star_empty);
                }
                // Mettre à jour l'affichage
                notifyDataSetChanged();
            });

            listItem.setOnClickListener(v -> {
                NotesFragment notesFragment = new NotesFragment();

                Bundle bundle = new Bundle();
                bundle.putString("exercise_title", currentExercise.getTitle());
                notesFragment.setArguments(bundle);

                ((AppCompatActivity) mContext).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, notesFragment)
                        .addToBackStack(null)
                        .commit();
            });


            ImageView imageView = listItem.findViewById(R.id.imageView);
            Glide.with(mContext)
                    .asGif()
                    .load(currentExercise.getImageResource())
                    .into(imageView);
        }

        return listItem;
    }

    public List<Exercise> getmExercises() {
        return mExercises;
    }

    public List<Exercise> getmFilteredExercises() {
        return mFilteredExercises;
    }
}
