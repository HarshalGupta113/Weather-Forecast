package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextInputLayout textInputLayout;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Data> dataArrayList;
    TextView cityname,maindegree,DateAndTime;
    ImageView mainImage;
    Button button;
    String url1 = "https://api.openweathermap.org/data/2.5/forecast?q=";
    String url2="&appid=db170164242cd1f9b0959569ab92bdb0&units=metric";

    String[] cities = {"New York", "London", "New Delhi", "Tokoyo", "Mexico", "Mumbai"};
    String cityjson, countryjson, url, datejson, humidityjson, iconjson,iconurl, descripitionjson,
            formatdate, feelslikejson, mintempjson, maxtempjson,mintempjson1,maxtempjson1,maxandmin,
            kilometer,datejson2, iconurl1, iconjson1,tempjson;
    double visibilityjson;
    ConstraintLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        textInputLayout=findViewById(R.id.textField);
        button=findViewById(R.id.SearchButton);
        cityname=findViewById(R.id.cityname);
        maindegree=findViewById(R.id.maindegree);
        DateAndTime=findViewById(R.id.dateAndTime);
        mainImage=findViewById(R.id.mainImage);
        recyclerView=findViewById(R.id.recyclerView2);
        constraintLayout=findViewById(R.id.constraintLayout);

        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String cityname = preferences.getString("CityName", "");
        if (!cityname.isEmpty()){
            Objects.requireNonNull(textInputLayout.getEditText()).setText(cityname);
            constraintLayout.setVisibility(View.VISIBLE);
            dataArrayList=new ArrayList<>();
            url =url1+cityname+url2;
            textInputLayout.clearFocus();
            getDataForApi();
        }else {
            constraintLayout.setVisibility(View.GONE);
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout.setVisibility(View.VISIBLE);
                dataArrayList=new ArrayList<>();
                textInputLayout.clearFocus();
                String editText= Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
                textInputLayout.getEditText().setText(editText);
                url =url1+editText+url2;
                editor.putString("CityName", editText);
                editor.apply();
                getDataForApi();
                if (textInputLayout.getEditText().getText().toString().isEmpty()){
                    textInputLayout.getEditText().setError("Enter a City name");
                }
            }
        });

    }

    private void getDataForApi() {
        StringRequest stringRequest=new StringRequest(0,url,response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("city");
                cityjson = jsonObject1.getString("name");
                countryjson = jsonObject1.getString("country");
                cityname.setText(String.format("%s, %s", cityjson, countryjson));
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    if (i==0)
                    {
                        datejson = jsonObject2.getString("dt_txt");
                        DateFormatter();
                        visibilityjson = jsonObject2.getDouble("visibility");
                        JSONObject jsonObject3 = jsonObject2.getJSONObject("main");
                        tempjson = jsonObject3.getString("temp");
                        maindegree.setText(String.format("%s °C", tempjson));
                        feelslikejson = jsonObject3.getString("feels_like");
                        humidityjson = jsonObject3.getString("humidity");
                        mintempjson = jsonObject3.getString("temp_min");
                        maxtempjson = jsonObject3.getString("temp_max");
                        JSONArray jsonArray1 = jsonObject2.getJSONArray("weather");
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject4 = jsonArray1.getJSONObject(j);
                            descripitionjson = jsonObject4.getString("description");
                            iconjson = jsonObject4.getString("icon");
                            iconurl="https://openweathermap.org/img/wn/"+iconjson+"@2x.png";
                            Glide.with(MainActivity.this).load(iconurl).error(R.drawable.ic_round_error_24).into(mainImage);
                        }
                    }
                    if (i>0){
                        datejson2=jsonObject2.getString("dt_txt");
                        JSONArray jsonArray2=jsonObject2.getJSONArray("weather");
                        for (int j = 0; j < jsonArray2.length(); j++) {
                            JSONObject jsonObject5 = jsonArray2.getJSONObject(j);
                            iconjson1 = jsonObject5.getString("icon");
                            iconurl1="https://openweathermap.org/img/wn/"+iconjson+"@2x.png";
                        }
                        JSONObject jsonObject6=jsonObject2.getJSONObject("main");
                        mintempjson1 = jsonObject6.getString("temp_min");
                        maxtempjson1 = jsonObject6.getString("temp_max");
                        maxandmin=mintempjson1+"/"+maxtempjson1+"°C";
                        dataArrayList.add(new Data(datejson2,iconurl1,maxandmin));
                    }

                }
                LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter=new Adapter(dataArrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.clearOnScrollListeners();

            }catch (JSONException e){

            }

        },error -> textInputLayout.getEditText().setError("Invalid city name"));
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(stringRequest);
    }

    private void DateFormatter() {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat output = new SimpleDateFormat("EEE,dd HH:mm aaa",Locale.US);
        try {
            Date dateformat = input.parse(datejson);
            assert dateformat != null;
            formatdate = output.format(dateformat);
            DateAndTime.setText(formatdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}