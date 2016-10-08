package com.group32.homework05;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CityWeatherActivity extends AppCompatActivity implements IWeatherDataHandler, AdapterView.OnItemClickListener {

    public static String WEATHER_DATA_KEY = "WEATHER_DATA";
    private ProgressDialog progressLoadingData;
    private static String API_KEY = "3e385e29f613b926";

    private String city;
    private String state;

    private ArrayList<Weather> weatherData;
    private WeatherListAdapter weatherAdapter;
    private ListView weatherListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.label_apptitle));

        // Create Progress Dialog
        progressLoadingData = new ProgressDialog(this);
        progressLoadingData.setMessage(getString(R.string.progressDataLodingText));
        progressLoadingData.setCancelable(false);

        // TODO Check intent
        city = getIntent().getStringExtra(MainActivity.CITY_EXTRAS_KEY);
        state = getIntent().getStringExtra(MainActivity.STATE_EXTRAS_KEY);

        // TODO Update to the internet APU when done with the codes
        //String url = "http://api.wunderground.com/api/"+API_KEY+"/hourly/q/"+state+"/"+city+".json";
        String url = "http://webpages.uncc.edu/agencogl/San_Francisco.json";
        progressLoadingData.show();
        new GetHourlyWeatherAsyncTask(this).execute(url);
    }

    @Override
    public void weatherDataUpdated(ArrayList<Weather> weatherData) {
        progressLoadingData.dismiss();

        for (Weather currentWeatherData:weatherData
             ) {
            currentWeatherData.setCity(city);
            currentWeatherData.setState(state);
        }

        this.weatherData = weatherData;

        weatherListView = (ListView) findViewById(R.id.listHourlyWeather);
        weatherListView.setOnItemClickListener(this);
        weatherAdapter = new WeatherListAdapter(this,R.layout.weather_list_row,this.weatherData);
        weatherListView.setAdapter(weatherAdapter);
        weatherAdapter.notifyDataSetChanged();

    }

    @Override
    public String toString() {
        return getString(R.string.testJSON);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(WEATHER_DATA_KEY,weatherAdapter.getItem(position));

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAddToFav:
                Log.d("test","HELLO");
                // Get the string stored in the preferences
                SharedPreferences preferences = getSharedPreferences("PREFS",MODE_PRIVATE);
                String jsonString = preferences.getString("FAVS",null);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE,1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                Favorite newFavorite = new Favorite("10",city,state, dateFormat.format(calendar.getTime()));

                if (jsonString == null) {
                    // If this preference is not set we have to set it

                    ArrayList<Favorite> favoritesList = new ArrayList<>();
                    favoritesList.add(newFavorite);

                    Gson gson = new Gson();
                    jsonString = gson.toJson(favoritesList);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("FAVS",jsonString);
                    editor.commit();
                } else {

                    Gson gson = new Gson();
                    ArrayList<Favorite> favoritesList = gson.fromJson(jsonString,new TypeToken<ArrayList<Favorite>>(){}.getType());

                    favoritesList.add(newFavorite);

                    jsonString = gson.toJson(favoritesList);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("FAVS",jsonString);
                    editor.commit();
                }

        }
        return true;
    }

}
