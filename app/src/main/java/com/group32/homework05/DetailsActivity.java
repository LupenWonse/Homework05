package com.group32.homework05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Weather weather = null;
        // Try to get the intent data
        if( getIntent() != null && getIntent().getSerializableExtra(CityWeatherActivity.WEATHER_DATA_KEY) != null){
            weather = (Weather) getIntent().getSerializableExtra(CityWeatherActivity.WEATHER_DATA_KEY);
        }

        ((TextView) findViewById(R.id.textFeelsLike)).setText(weather.getFeelsLike());
        ((TextView) findViewById(R.id.textDewpoint)).setText(weather.getDewpoint());
        ((TextView) findViewById(R.id.textClouds)).setText(weather.getClouds());
        ((TextView) findViewById(R.id.textHumidity)).setText(weather.getHumidity());
        ((TextView) findViewById(R.id.textPressure)).setText(weather.getPressure());
        ((TextView) findViewById(R.id.textWinds)).setText(weather.getWind());
        ((TextView) findViewById(R.id.textTemperature)).setText(weather.getTemperature());
        ((TextView) findViewById(R.id.textClimate)).setText(weather.getClimateType());


    }
}
