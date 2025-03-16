package com.example.fitnessapp.ui.programs;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.FragmentProgramsBinding;
import com.example.fitnessapp.classes.Program;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProgramsFragment extends Fragment implements ProgramAdapter.OnProgramInteractionListener {

    private ProgramAdapter programAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_programs, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        programAdapter = new ProgramAdapter(this);
        recyclerView.setAdapter(programAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("programs");

        fetchPrograms();

        return root;
    }

    private void fetchPrograms() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Program> programs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    String title = snapshot.child("programHeader").getValue(String.class);
                    String description = snapshot.child("programDescription").getValue(String.class);

                    Program program = new Program(id, title, description);
                    programs.add(program);
                }

                programAdapter.setProgramData(programs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to fetch programs: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onEdit(String id, EditText edtText) {
        edtText.setFocusable(true);
        edtText.setFocusableInTouchMode(true);
        edtText.setEnabled(true);
        edtText.requestFocus();
    }

    @Override
    public void onUpdate(String id, EditText edtDesc) {
        String updatedDescription = edtDesc.getText().toString();
        if (updatedDescription.isEmpty()) {
            Toast.makeText(getContext(), "Description cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child(id).child("programDescription").setValue(updatedDescription)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Program updated successfully!", Toast.LENGTH_SHORT).show();
                    fetchPrograms();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to update program.", Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    public void onDelete(String id) {
        databaseReference.child(id).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Program deleted successfully!", Toast.LENGTH_SHORT).show();
                    fetchPrograms();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to delete program.", Toast.LENGTH_SHORT).show();
                });
    }
}
