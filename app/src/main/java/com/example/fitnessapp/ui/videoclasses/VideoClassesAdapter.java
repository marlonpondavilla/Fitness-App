package com.example.fitnessapp.ui.videoclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;

public class VideoClassesAdapter extends RecyclerView.Adapter<VideoClassesAdapter.ViewHolder> {

    private String[] titles;
    private String[] descriptions;
    private String[] prices;

    public void setVideoClassData(String[] titles, String[] descriptions, String[] prices) {
        this.titles = titles;
        this.descriptions = descriptions;
        this.prices = prices;
        notifyDataSetChanged(); // Notify the adapter when data changes
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_class, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTextView.setText(titles[position]);
        holder.descriptionTextView.setText(descriptions[position]);
        holder.priceTextView.setText(prices[position]);
    }

    @Override
    public int getItemCount() {
        return titles != null ? titles.length : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        Button signUpButton;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.classTitle);
            descriptionTextView = view.findViewById(R.id.classDetail);
            priceTextView = view.findViewById(R.id.classPrice);
            signUpButton = view.findViewById(R.id.signUpButton);
        }
    }
}
