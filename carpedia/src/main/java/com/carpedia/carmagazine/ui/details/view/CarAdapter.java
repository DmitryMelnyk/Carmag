package com.carpedia.carmagazine.ui.details.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.carpedia.carmagazine.R;
import com.carpedia.carmagazine.databinding.ItemCarBinding;
import com.carpedia.carmagazine.model.Car;

import java.util.List;

/**
 * Created by d264 on 12/28/17.
 */

class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private final List<Car> mCars;
    public CarAdapter(@NonNull List<Car> cars) {
        mCars = cars;
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCarBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_car, parent, false);
        return new CarViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        holder.binding.setCar(mCars.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCars == null ? 0 : mCars.size() ;
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        final ItemCarBinding binding;

        public CarViewHolder(ItemCarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
