package com.example.fitnessapp.ui.trainers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;

public class TrainerAdapter extends RecyclerView.Adapter<TrainerAdapter.TrainerViewHolder> {

    private int[] trainerImgs;
    private String[] trainerNames;
    private String[] trainerDescs;

    // ViewHolder for each trainer item
    public static class TrainerViewHolder extends RecyclerView.ViewHolder {
        ImageView trainerImage;
        TextView trainerName;
        TextView trainerDesc;

        public TrainerViewHolder(View itemView) {
            super(itemView);
            trainerImage = itemView.findViewById(R.id.trainerImage);
            trainerName = itemView.findViewById(R.id.trainerName);
            trainerDesc = itemView.findViewById(R.id.trainerDesc);
        }
    }

    @NonNull
    @Override
    public TrainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trainer_card, parent, false);
        return new TrainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerViewHolder holder, int position) {
        holder.trainerName.setText(trainerNames[position]);
        holder.trainerDesc.setText(trainerDescs[position]);
        holder.trainerImage.setImageResource(trainerImgs[position]);
    }

    @Override
    public int getItemCount() {
        return trainerImgs != null ? trainerImgs.length : 0;
    }

    // Set trainer data in the adapter
    public void setTrainersData(int[] imgs, String[] names, String[] descs) {
        this.trainerImgs = imgs;
        this.trainerNames = names;
        this.trainerDescs = descs;
        notifyDataSetChanged();
    }
}
