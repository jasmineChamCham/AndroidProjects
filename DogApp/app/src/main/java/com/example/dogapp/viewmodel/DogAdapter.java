package com.example.dogapp.viewmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.databinding.DogItemBinding;
import com.example.dogapp.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> implements Filterable {

    private static ArrayList<DogBreed> dogBreeds = new ArrayList<>();
    private static ArrayList<DogBreed> dogBreedsCopy = new ArrayList<>();

    public DogAdapter(ArrayList<DogBreed> dogBreedsOutside){
        dogBreeds = dogBreedsOutside;
        dogBreedsCopy = dogBreeds;
    }

    @NonNull
    @Override
    public DogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DogItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.dog_item, parent, false);
        return new DogAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DogAdapter.ViewHolder holder, int position) {
        holder.binding.setDog(dogBreeds.get(position));
        Picasso.get().load(dogBreeds.get(position).getUrl()).into(holder.binding.imageDog);
    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString().toLowerCase();
                List<DogBreed> filteredDogs = new ArrayList<>();
                if(input.isEmpty()){
                    filteredDogs.addAll(dogBreedsCopy);
                } else{
                    for(DogBreed dog : dogBreedsCopy){
                        if(dog.getName().toLowerCase().contains(input)){
                            filteredDogs.add(dog);
                        }
                    }
                }

                FilterResults fs = new FilterResults();
                fs.values = filteredDogs;
                return fs;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dogBreeds = new ArrayList<>();
                dogBreeds.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public DogItemBinding binding;
        public ViewHolder(DogItemBinding itemBinding){
            super(itemBinding.getRoot());
            this.binding = itemBinding;
            binding.imageDog.setOnClickListener(view -> {
                DogBreed dog = dogBreeds.get(getAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putSerializable("dogBreed", dog);
                Navigation.findNavController(view).navigate(R.id.detailFragment, bundle);
            });
        }
    }
}
