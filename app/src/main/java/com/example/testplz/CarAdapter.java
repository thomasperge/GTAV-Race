package com.example.testplz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CarAdapter extends ArrayAdapter<Car> {

    public CarAdapter(Context context, List<Car> carList) {
        super(context, 0, carList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.car_spinner_item, parent, false);
        }

        ImageView carImage = convertView.findViewById(R.id.carImage);
        TextView carName = convertView.findViewById(R.id.carName);
        TextView carCount = convertView.findViewById(R.id.carCount);

        Car car = getItem(position);
        if (car != null) {
            carName.setText(car.getName());
            carImage.setImageDrawable(car.getImage()); // Définit l'image du ImageView avec le Drawable de la voiture
            carCount.setText("x" + car.getCount());

        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}

