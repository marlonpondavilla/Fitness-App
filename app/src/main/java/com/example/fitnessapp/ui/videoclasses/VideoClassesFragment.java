package com.example.fitnessapp.ui.videoclasses;

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
import com.example.fitnessapp.databinding.FragmentVideoClassesBinding;

public class VideoClassesFragment extends Fragment {

    private FragmentVideoClassesBinding binding;
    private VideoClassesAdapter videoClassesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get ViewModel instance
        VideoClassesViewModel videoClassesViewModel =
                new ViewModelProvider(this).get(VideoClassesViewModel.class);

        // Set up RecyclerView
        RecyclerView recyclerView = binding.recyclerView;
        videoClassesAdapter = new VideoClassesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(videoClassesAdapter);

        // Observe LiveData from ViewModel
        videoClassesViewModel.getVideoClassTitles().observe(getViewLifecycleOwner(), titles -> {
            String[] descriptions = videoClassesViewModel.getVideoClassDescs().getValue();
            String[] prices = videoClassesViewModel.getVideoClassPrices().getValue();
            videoClassesAdapter.setVideoClassData(titles, descriptions, prices);
        });

        // Sample data (can be fetched from an API or database)
        String[] videoClassTitles = {"Basic Plan", "Standard Plan", "Premium Plan"};
        String[] videoClassDescriptions = {
                "Start your yoga journey with this beginner-friendly course.",
                "Build strength and flexibility with Pilates sessions.",
                "Push your limits with high-intensity strength workouts."
        };
        String[] videoClassPrices = {"₱500/month", "₱700/month", "₱600/month"};

        videoClassesViewModel.setVideoClassData(videoClassTitles, videoClassDescriptions, videoClassPrices);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
