package com.example.fitnessapp.ui.trainers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrainersViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<int[]> trainerImg = new MutableLiveData<>();
    private final MutableLiveData<String[]> trainerName = new MutableLiveData<>();
    private final MutableLiveData<String[]> trainerDesc = new MutableLiveData<>();

    public LiveData<int[]> getTrainerImg(){
        return trainerImg;
    }

    public LiveData<String[]> getTrainerName(){
        return trainerName;
    }

    public LiveData <String[]> getTrainerDesc(){
        return trainerDesc;
    }

    public void setTrainerData(int[] trainerImg, String[] trainerName, String[] trainerDesc){
        this.trainerImg.setValue(trainerImg);
        this.trainerName.setValue(trainerName);
        this.trainerDesc.setValue(trainerDesc);
    }
}