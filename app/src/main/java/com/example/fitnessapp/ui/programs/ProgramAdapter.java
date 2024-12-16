package com.example.fitnessapp.ui.programs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder> {

    private String[] programTitles;
    private String[] programDescriptions;

    // ViewHolder for each program item
    public static class ProgramViewHolder extends RecyclerView.ViewHolder {
        TextView programTitle;
        TextView programDescription;

        public ProgramViewHolder(View itemView) {
            super(itemView);
            programTitle = itemView.findViewById(R.id.offerTitle);
            programDescription = itemView.findViewById(R.id.offerDescrription);
        }
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_program_card, parent, false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        holder.programTitle.setText(programTitles[position]);
        holder.programDescription.setText(programDescriptions[position]);
    }

    @Override
    public int getItemCount() {
        return programTitles != null ? programTitles.length : 0;
    }

    public void setProgramData(String[] titles, String[] descriptions) {
        this.programTitles = titles;
        this.programDescriptions = descriptions;
        notifyDataSetChanged();
    }
}
