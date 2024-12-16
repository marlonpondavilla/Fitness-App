package com.example.fitnessapp.ui.trainers;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;

public class TrainersFragment extends Fragment {

    private TrainersViewModel mViewModel;
    private RecyclerView recyclerView;
    private TrainerAdapter adapter;

    public static TrainersFragment newInstance() {
        return new TrainersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_trainers, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TrainerAdapter();
        recyclerView.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this).get(TrainersViewModel.class);

        mViewModel.getTrainerImg().observe(getViewLifecycleOwner(), new Observer<int[]>() {
            @Override
            public void onChanged(int[] trainerImgs) {
                // Get trainer names and descriptions
                String[] names = mViewModel.getTrainerName().getValue();
                String[] desc = mViewModel.getTrainerDesc().getValue();

                // Pass the data to the adapter
                adapter.setTrainersData(trainerImgs, names, desc);
            }
        });

        int[] trainerImages = {R.drawable.yulo, R.drawable.brandon, R.drawable.pacquiao};
        String[] trainerNames = {"Carlos Yulo", "Nadine Marie", "Manny Paquiao"};
        String[] trainerDescs = {
                "Filipino artistic gymnast, 2024 Olympic Gold Medalist.",
                "Professional Yoga Instructor specializing in Hatha Yoga.",
                "Manny Paquiao is a professional boxer. Nicknamed \"PacMan\", he is widely regarded as one of the greatest professional boxers of all time.",

        };

        // Set the trainer data in the ViewModel
        mViewModel.setTrainerData(trainerImages, trainerNames, trainerDescs);

        return rootView;
    }
}
