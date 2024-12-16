package com.example.fitnessapp.ui.videoclasses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VideoClassesViewModel extends ViewModel {

    private MutableLiveData<String[]> videoClassTitles = new MutableLiveData<>();
    private MutableLiveData<String[]> videoClassDescriptions = new MutableLiveData<>();
    private MutableLiveData<String[]> videoClassPrices = new MutableLiveData<>();

    // Getter methods for LiveData
    public LiveData<String[]> getVideoClassTitles() {
        return videoClassTitles;
    }

    public LiveData<String[]> getVideoClassDescs() {
        return videoClassDescriptions;
    }

    public LiveData<String[]> getVideoClassPrices() {
        return videoClassPrices;
    }

    // Method to set data
    public void setVideoClassData(String[] titles, String[] descriptions, String[] prices) {
        videoClassTitles.setValue(titles);
        videoClassDescriptions.setValue(descriptions);
        videoClassPrices.setValue(prices);
    }
}
