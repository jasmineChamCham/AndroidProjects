package com.example.dogapp2.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp2.R;
import com.example.dogapp2.databinding.DogItemBinding;
import com.example.dogapp2.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> implements Filterable {
    private ArrayList<DogBreed> dogBreeds;
    private ArrayList<DogBreed> dogBreedsCopy;

    public DogAdapter(ArrayList<DogBreed> dogBreeds){
        this.dogBreeds = dogBreeds;
        this.dogBreedsCopy = dogBreeds;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchText = constraint.toString().toLowerCase();
                ArrayList<DogBreed> filteredDogBreeds = new ArrayList<DogBreed>();
                if(searchText.isEmpty())
                {
                    filteredDogBreeds.addAll(dogBreedsCopy);
                }
                else
                {
                    for(DogBreed dog : dogBreedsCopy)
                    {
                        if(dog.getName().toLowerCase().contains(searchText))
                        {
                            filteredDogBreeds.add(dog);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredDogBreeds;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dogBreeds = (ArrayList<DogBreed>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    @NonNull
    @Override
    public DogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DogItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.dog_item, parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DogAdapter.ViewHolder holder, int position) {

        holder.binding.setDog(dogBreeds.get(position));
        if(dogBreeds.get(position).isLiked())
        {
            holder.binding.ivHeart.setImageResource(R.drawable.clickedheart);
        }
        else
        {
            holder.binding.ivHeart.setImageResource(R.drawable.heart);
        }
        Picasso.get().load(dogBreeds.get(position).getUrl()).into(holder.binding.imageDog);
    }

    @Override
    public int getItemCount() {
        return this.dogBreeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public DogItemBinding binding;
        private int[] images = {R.drawable.heart, R.drawable.clickedheart};
        private int currentIndex = 0;

        public ViewHolder(DogItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            binding.imageDog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DogBreed dog = dogBreeds.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dogBreed", dog);
                    Navigation.findNavController(view).navigate(R.id.detailFragment, bundle);
                }
            });

            binding.ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex = (currentIndex + 1) % images.length;
                    binding.ivHeart.setImageResource(images[currentIndex]);
                    if(currentIndex == 0)
                    {
                        dogBreeds.get(getAdapterPosition()).setLiked(false);
                    }
                    else if(currentIndex == 1)
                    {
                        dogBreeds.get(getAdapterPosition()).setLiked(true);
                    }
                }
            });

            itemView.setOnTouchListener(new OnSwipeTouchListener() {
                @Override
                public void onSwipeLeft() {
                    Log.d("DEBUG", "Swipe left");
                    if(binding.layout1.getVisibility() == View.GONE)
                    {
                        binding.layout1.setVisibility(View.VISIBLE);
                        binding.layout2.setVisibility(View.GONE);
                    }
                    else
                    {
                        binding.layout1.setVisibility(View.GONE);
                        binding.layout2.setVisibility(View.VISIBLE);
                    }
                    super.onSwipeLeft();
                }

                @Override
                public void onSwipeRight() {
                    Log.d("DEBUG", "Swipe left");
                    if(binding.layout2.getVisibility() == View.GONE)
                    {
                        binding.layout2.setVisibility(View.VISIBLE);
                        binding.layout1.setVisibility(View.GONE);
                    }
                    else
                    {
                        binding.layout2.setVisibility(View.GONE);
                        binding.layout1.setVisibility(View.VISIBLE);
                    }
                    super.onSwipeLeft();
                }
            }) ;


        }
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 50;
            private static final int SWIPE_VELOCITY_THRESHOLD = 50;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onClick();
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }

        public void onClick() {
        }
    }
}