package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/*
 * This class is the adapter for the RecyclerView in the GalleryActivity.
 * It takes a list of Person objects and creates a ViewHolder for each item in the list.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private final List<Person> personList;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public PersonAdapter(List<Person> personList, Context context) {
        this.personList = personList;
        PersonAdapter.context = context;
    }

    /*
     * This class is the ViewHolder for the RecyclerView.
     * It holds the views for each item in the list and sets the data to the views.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.person_image);
            textView = view.findViewById(R.id.person_name);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Call the method in the activity to show the removal dialog
                    if (context instanceof GalleryActivity) {
                        ((GalleryActivity) context).showRemovalDialog(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    /*
     * This method creates a new ViewHolder for the RecyclerView.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

    /*
     * This method sets the data to the views in each ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.textView.setText(person.getName());

        if (person.hasImageUri()) {
            // Load the image from the Uri
            holder.imageView.setImageURI(person.getImageUri());
        } else if (person.hasImageResId()) {
            // Load the image from the drawable resource
            holder.imageView.setImageResource(person.getImageResId());
        } else {
            // Handle the case where there's no image
            holder.imageView.setImageResource(R.drawable.default_image); // Replace with your default image
        }
    }

    /*
     * This method returns the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return personList.size();
    }
}
