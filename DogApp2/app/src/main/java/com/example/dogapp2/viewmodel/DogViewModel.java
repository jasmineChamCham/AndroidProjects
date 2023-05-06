package com.example.dogapp2.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogapp2.model.DogBreed;

import java.util.List;

public class DogViewModel extends ViewModel {
    private DogRepository dogRepository;
    private LiveData<List<DogBreed>> dogBreedsLiveData;

    public DogViewModel(){
        dogRepository = new DogRepository();
    }

    public LiveData<List<DogBreed>> getDogBreedsLiveData(){
        if (dogBreedsLiveData == null) {
            dogBreedsLiveData = dogRepository.getAllDogs();
        }
        return dogBreedsLiveData;
    }
}
