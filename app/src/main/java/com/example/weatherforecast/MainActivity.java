package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String cities[]= {"New York","London","New Delhi","Tokoyo","Mexico"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.cities_listview,cities);
        ListView listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String input=adapterView.getItemAtPosition(i).toString();
                Intent intent=new Intent(MainActivity.this,weatherdata.class);
                intent.putExtra("citiesname",input);
                startActivity(intent);

            }
        });

    }
}