package com.example.quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private final List<Person> personList;
    private static Context context; // Add this line


    public PersonAdapter(List<Person> personList, Context context) {
        this.personList = personList;
        this.context = context; // Assign the context here
    }

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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return personList.size();
    }
}

