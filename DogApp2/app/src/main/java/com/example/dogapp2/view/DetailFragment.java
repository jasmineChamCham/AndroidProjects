package com.example.dogapp2.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapp2.R;
import com.example.dogapp2.databinding.FragmentDetailBinding;
import com.example.dogapp2.model.DogBreed;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {
    private DogBreed dogBreed;
    private FragmentDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dogBreed = (DogBreed) getArguments().getSerializable("dogBreed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_detail, null, false);
        View viewRoot = binding.getRoot();
        binding.setDog(dogBreed);
        if (dogBreed != null) {
            Log.d("url of dog", dogBreed.getUrl());
            Picasso.get().load(dogBreed.getUrl()).into(binding.imageDog);
            // Inflate the layout for this fragment
        }

        return viewRoot;
    }
}