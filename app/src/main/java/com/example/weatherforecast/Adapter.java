package com.example.weatherforecast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    ArrayList<Data> itemlist;
    View view;

    public Adapter(ArrayList<Data> itemlist) {
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fivedays_weatherdata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Data data=itemlist.get(position);
        SimpleDateFormat input1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat output1 = new SimpleDateFormat("EEE, LLL dd ",Locale.US);
        try {
            Date dateformat1 = input1.parse(data.getDate());
            assert dateformat1 != null;
            holder.dateview.setText(output1.format(dateformat1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Glide.with(view).load(data.getImage()).error(R.drawable.ic_round_error_24).into(holder.imageView);
        holder.tempview.setText(data.getTemp());

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateview, tempview;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateview = itemView.findViewById(R.id.date2);
            imageView=itemView.findViewById(R.id.imageView2);
            tempview = itemView.findViewById(R.id.temp2);

        }
    }
}
