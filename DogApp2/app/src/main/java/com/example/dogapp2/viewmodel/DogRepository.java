package com.example.dogapp2.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dogapp2.model.DogApi;
import com.example.dogapp2.model.DogBreed;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DogRepository {
    private DogApiService apiService;
    private MutableLiveData<List<DogBreed>> allDogs;

    public DogRepository(){
        apiService = new DogApiService();
    }

    public LiveData<List<DogBreed>> getAllDogs()
    {
        if (allDogs == null) {
            allDogs = new MutableLiveData<>();
        }
        apiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> dogBreeds1) {
                        allDogs.postValue(dogBreeds1);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "Fail" + e.getMessage());
                    }
                });
        return allDogs;
    }
}
