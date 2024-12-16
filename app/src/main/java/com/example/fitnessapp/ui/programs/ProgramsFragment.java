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

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.FragmentProgramsBinding;

public class ProgramsFragment extends Fragment {

    private FragmentProgramsBinding binding;
    private ProgramAdapter programAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProgramsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ProgramsViewModel programsViewModel =
                new ViewModelProvider(this).get(ProgramsViewModel.class);

        RecyclerView recyclerView = binding.recyclerView;
        programAdapter = new ProgramAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(programAdapter);

        programsViewModel.getProgramTitles().observe(getViewLifecycleOwner(), titles -> {
            String[] descriptions = programsViewModel.getProgramDescs().getValue();
            programAdapter.setProgramData(titles, descriptions);
        });

        String[] programTitles = {"Weight Training", "Yoga", "Cardio"};
        String[] programDescriptions = {
                "Build muscle and strength with expert-led weight training process",
                "Relax and stretch your body with yoga sessions",
                "Improve cardiovascular health with intense cardio workouts"
        };

        programsViewModel.setProgramData(programTitles, programDescriptions);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
