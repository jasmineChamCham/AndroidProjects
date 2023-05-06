package com.example.dogapp2.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapp2.R;
import com.example.dogapp2.model.DogBreed;
import com.example.dogapp2.viewmodel.DogAdapter;
import com.example.dogapp2.viewmodel.DogViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private DogViewModel dogViewModel;
    private RecyclerView rvDogs;
    private ArrayList<DogBreed> dogBreeds;
    private DogAdapter dogAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dogViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(DogViewModel.class);

        rvDogs = view.findViewById(R.id.rv_dogs);
        dogBreeds = new ArrayList<DogBreed>();
        dogAdapter = new DogAdapter(dogBreeds);
        rvDogs.setAdapter(dogAdapter);
        rvDogs.setLayoutManager(new GridLayoutManager(getContext(), 2));

        dogViewModel.getDogBreedsLiveData().observe((LifecycleOwner) this, new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> newDogBreeds) {
                dogBreeds.clear();
                dogBreeds.addAll(newDogBreeds);
                dogAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dogAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}