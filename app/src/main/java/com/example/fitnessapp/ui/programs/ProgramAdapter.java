package com.example.fitnessapp.ui.programs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.classes.Program;

import java.util.ArrayList;
import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder> {

    private List<Program> programList;
    private OnProgramInteractionListener mListener;

    public interface OnProgramInteractionListener {
        void onEdit(String id, EditText edtText);
        void onUpdate(String id, EditText edtDesc);
        void onDelete(String id);
    }

    public ProgramAdapter(OnProgramInteractionListener listener) {
        this.mListener = listener;
        programList = new ArrayList<>();
    }

    public static class ProgramViewHolder extends RecyclerView.ViewHolder {
        TextView programTitle;
        EditText programDescription;
        Button editBtn, updateBtn, deleteBtn;

        public ProgramViewHolder(View itemView) {
            super(itemView);
            programTitle = itemView.findViewById(R.id.offerTitle); // Program title TextView
            programDescription = itemView.findViewById(R.id.offerDescrription); // Program description EditText
            editBtn = itemView.findViewById(R.id.editBtn); // Edit button
            updateBtn = itemView.findViewById(R.id.updateBtn); // Update button
            deleteBtn = itemView.findViewById(R.id.deleteBtn); // Delete button
        }
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item layout for each card (Program)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_program_card, parent, false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        Program program = programList.get(position);

        holder.programTitle.setText(program.getTitle());
        holder.programDescription.setText(program.getDescription());

        holder.programDescription.setFocusable(false);
        holder.programDescription.setFocusableInTouchMode(false);
        holder.programDescription.setEnabled(false);

        holder.editBtn.setOnClickListener(v -> {
            holder.programDescription.setFocusable(true);
            holder.programDescription.setFocusableInTouchMode(true);
            holder.programDescription.setEnabled(true);
            mListener.onEdit(program.getId(), holder.programDescription);
        });

        holder.updateBtn.setOnClickListener(v -> {
            mListener.onUpdate(program.getId(), holder.programDescription);
        });

        holder.deleteBtn.setOnClickListener(v -> {
            mListener.onDelete(program.getId());
        });
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public void setProgramData(List<Program> programs) {
        this.programList = programs;
        notifyDataSetChanged();
    }

}
