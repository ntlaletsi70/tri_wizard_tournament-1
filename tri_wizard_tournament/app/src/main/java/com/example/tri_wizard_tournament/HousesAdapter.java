package com.example.tri_wizard_tournament;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HousesAdapter extends ArrayAdapter<Object> {
    private final List<Object> houses;
    private final Context context;

    public HousesAdapter(List<Object> houses, Context context) {
        super(context, R.layout.custom_house, houses);
        this.houses = houses;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_house, parent, false);

        TextView tvHouseName = rowView.findViewById(R.id.tvHouseName);
        tvHouseName.setText(houses.get(position).toString());
        return rowView;
    }
}
