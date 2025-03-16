package com.example.fitnessapp.ui.programs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessapp.classes.Program;

import java.util.List;

public class ProgramsViewModel extends ViewModel {

    private final MutableLiveData<List<Program>> programsList = new MutableLiveData<>();

    public LiveData<List<Program>> getPrograms() {
        return programsList;
    }

    public void setProgramData(List<Program> programs) {
        programsList.setValue(programs);
    }
}
