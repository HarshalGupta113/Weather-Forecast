package com.example.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private ArrayList<Modulclass>arrayList;

    public Adapter(Context applicationContext, ArrayList<Modulclass> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View inflater= layoutInflater.inflate(R.layout.fivedays_weatherdata,null);
        ViewHolder viewHolder =new ViewHolder(inflater);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            Modulclass modulclass=arrayList.get(position);
            holder.city.setText(modulclass.getName());
            holder.date.setText(modulclass.getDate());
            holder.mintemp.setText(modulclass.getMintemperature());
            holder.maxtemp.setText(modulclass.getMaxtemperature());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView city,date,mintemp,maxtemp,textView3,textView4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            city=itemView.findViewById(R.id.citiesname);
            date=itemView.findViewById(R.id.date);
            mintemp=itemView.findViewById(R.id.mintemp);
            maxtemp=itemView.findViewById(R.id.maxtemp);
        }
    }
}
