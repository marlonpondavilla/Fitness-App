package com.example.fitnessapp.ui.programs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProgramsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ProgramsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("hello");
    }

    public LiveData<String> getText() {
        return mText;
    }
}