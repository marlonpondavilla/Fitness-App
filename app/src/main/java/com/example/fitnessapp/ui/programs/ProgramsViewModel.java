package com.example.fitnessapp.ui.programs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProgramsViewModel extends ViewModel {

    private final MutableLiveData<String[]> programTitles = new MutableLiveData<>();
    private final MutableLiveData<String[]> programDescs = new MutableLiveData<>();

    // Expose LiveData for program titles and descriptions
    public LiveData<String[]> getProgramTitles() {
        return programTitles;
    }

    public LiveData<String[]> getProgramDescs() {
        return programDescs;
    }

    // Set program data
    public void setProgramData(String[] titles, String[] descriptions) {
        programTitles.setValue(titles);
        programDescs.setValue(descriptions);
    }
}
