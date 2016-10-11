/*
 Homwork 5
 Details Activty
 Akarsh Gupta     - 800969888
 Ahmet Gencoglu   - 800982227
*/
package com.group32.homework05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Weather weather = null;
        // Try to get the intent data
        if( getIntent() != null && getIntent().getSerializableExtra(CityWeatherActivity.WEATHER_DATA_KEY) != null) {
            weather = (Weather) getIntent().getSerializableExtra(CityWeatherActivity.WEATHER_DATA_KEY);
            // Update UI with the weather data
            ((TextView) findViewById(R.id.textFeelsLike)).setText(String.format("%s Fahrenheit",weather.getFeelsLike()));
            ((TextView) findViewById(R.id.textDewpoint)).setText(String.format("%s Fahrenheit",weather.getDewpoint()));
            ((TextView) findViewById(R.id.textClouds)).setText(weather.getClouds());
            ((TextView) findViewById(R.id.textHumidity)).setText(String.format("%s%%",weather.getHumidity()));
            ((TextView) findViewById(R.id.textPressure)).setText(String.format("%s hpa",weather.getPressure()));
            ((TextView) findViewById(R.id.textWinds)).setText(String.format("%s mph, %s",weather.getWindSpeed(),weather.getWindDirection()));
            ((TextView) findViewById(R.id.textTemperature)).setText(String.format("%s° F",weather.getTemperature()));
            ((TextView) findViewById(R.id.textClimate)).setText(weather.getClimateType());
            ((TextView) findViewById(R.id.textMaxTemperature)).setText(String.format("%s Fahrenheit",weather.getMaximumTemperature()));
            ((TextView) findViewById(R.id.textMinTemperature)).setText(String.format("%s Fahrenheit",weather.getMinimumTemperature()));
            ((TextView) findViewById(R.id.textCurrentLocation)).setText(weather.getLocation() + " (" + weather.getFormattedTime()+")");

            // Load the image with Picasso library
            Picasso.with(this).load(weather.getIconURL()).into((ImageView)findViewById(R.id.imageLargeWeather));
        }
        else {
            // Write error message to console and quit
            // This should never happen
            Log.e("weatherApp","No data recieved in Detail Activity");
            finish();
        }
}}
