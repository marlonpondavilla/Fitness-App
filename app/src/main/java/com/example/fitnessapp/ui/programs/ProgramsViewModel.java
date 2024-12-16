package com.example.fitnessapp.ui.programs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProgramsViewModel extends ViewModel {

    private final MutableLiveData<int[]> programImgs = new MutableLiveData<>();
    private final MutableLiveData<String[]> programTitles = new MutableLiveData<>();
    private final MutableLiveData<String[]> programDescs = new MutableLiveData<>();

    public LiveData<int[]> getProgramImgs() {
        return programImgs;
    }

    public LiveData<String[]> getProgramTitles() {
        return programTitles;
    }

    public LiveData<String[]> getProgramDescs() {
        return programDescs;
    }

    public void setProgramData(int[] programImgs, String[] programTitles, String[] programDescs) {
        this.programImgs.setValue(programImgs);
        this.programTitles.setValue(programTitles);
        this.programDescs.setValue(programDescs);
    }
}
