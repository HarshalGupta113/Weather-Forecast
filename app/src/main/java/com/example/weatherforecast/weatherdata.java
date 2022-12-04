package com.example.weatherforecast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class weatherdata extends AppCompatActivity {
    RecyclerView recyclerView;
    String input;
    String url;
    RequestQueue queue;
    ArrayList<Modulclass> arrayList;
    RecyclerView.LayoutManager layoutManager;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherdata);

        arrayList = new ArrayList<Modulclass>();
        input= getIntent().getExtras().get("citiesname").toString().trim();
        url= "https://api.openweathermap.org/data/2.5/forecast?q="+input+"&appid=db170164242cd1f9b0959569ab92bdb0";
        recyclerView=findViewById(R.id.Recycleview);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("list");
                    if (jsonArray.length()>0){
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            JSONObject jsonObject2=jsonObject1.getJSONObject("main");
                            String datajson=jsonObject1.getString("dt_txt");
                            String mintempjson=jsonObject2.getString("temp_min");
                            String maxtempjson=jsonObject2.getString("temp_max");
                            arrayList.add(new Modulclass(input,datajson,mintempjson,maxtempjson));
                        }
                        adapter=new Adapter(getApplicationContext(), arrayList);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    Log.e("error", String.valueOf(e));
                    e.printStackTrace();
                    Toast.makeText(weatherdata.this, ""+e, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(weatherdata.this, ""+error, Toast.LENGTH_SHORT).show();
                Log.d("error", String.valueOf(error));

            }
        });
        queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(this,arrayList);
        recyclerView.setAdapter(adapter);


    }


}