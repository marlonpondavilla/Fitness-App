package com.example.fitnessapp.ui.programs;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.FragmentHomeBinding;
import com.example.fitnessapp.databinding.FragmentProgramsBinding;
import com.example.fitnessapp.ui.home.HomeViewModel;

import java.util.ArrayList;

public class ProgramsFragment extends Fragment {

    private FragmentProgramsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_programs, container, false);

        ProgramsViewModel programsViewModel =
                new ViewModelProvider(this).get(ProgramsViewModel.class);

        binding = FragmentProgramsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}