/*
 Homwork 5
 Async Task
 Akarsh Gupta     - 800969888
 Ahmet Gencoglu   - 800982227
*/
package com.group32.homework05;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ahmet on 04/10/2016.
 */

public class GetHourlyWeatherAsyncTask extends AsyncTask<String, Void, ArrayList<Weather>> {

    private IWeatherDataHandler dataHandler;
    private String errorMessage = null;

    public GetHourlyWeatherAsyncTask(IWeatherDataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }


                @Override
    protected ArrayList<Weather> doInBackground(String... params) {
        URL connectionURL = null;
        try {
            Log.d("test",params[0]);
            connectionURL = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) connectionURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }

            String JSONResponse = stringBuilder.toString();

            // TODO Remove DEBUG CODE
            //JSONResponse = dataHandler.toString();
            return parseJSON(JSONResponse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    private ArrayList<Weather> parseJSON(String jsonResponse) {
        ArrayList<Weather> weatherList = new ArrayList<>();

        try {
            JSONObject rootJSON = new JSONObject(jsonResponse);

            // Check for error
            JSONObject responseJSON = rootJSON.getJSONObject("response");
            if (responseJSON.has("error")){
                errorMessage = responseJSON.getJSONObject("error").getString("description");
                return null;
            } else if (responseJSON.has("results")) {
                errorMessage = "Muliplte cities found";
                return null;
            }
            JSONArray hourlyJSON = rootJSON.getJSONArray("hourly_forecast");

            for(int item = 0 ; item < hourlyJSON.length() ; item ++){
                JSONObject rootWeather = hourlyJSON.getJSONObject(item);

                String epochTime = rootWeather.getJSONObject("FCTTIME").getString("epoch");
                String temperature = rootWeather.getJSONObject("temp").getString("english");
                String dewPoint = rootWeather.getJSONObject("dewpoint").getString("english");
                String clouds = rootWeather.getString("condition");
                String iconURL = rootWeather.getString("icon_url");
                String windSpeed = rootWeather.getJSONObject("wspd").getString("english");

                String windDirection = rootWeather.getJSONObject("wdir").getString("degrees");
                windDirection += rootWeather.getJSONObject("wdir").getString("dir");

                String climateType = rootWeather.getString("wx");
                String humidity = rootWeather.getString("humidity");
                String feelsLike = rootWeather.getJSONObject("feelslike").getString("english");
                String pressure = rootWeather.getJSONObject("mslp").getString("metric");

                weatherList.add(new Weather(epochTime,temperature,dewPoint,clouds,iconURL,
                        windSpeed,windDirection,climateType,humidity,feelsLike,null,null,pressure));


            }
            return weatherList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

        // TODO Remove and replace Debug code
        //Weather debugWeather = new Weather();
        //debugWeather.temperature = "23";


    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weatherData) {
        if (weatherData != null) {
            dataHandler.weatherDataUpdated(weatherData);
        } else if (errorMessage != null) {
            dataHandler.onError(errorMessage);
        }
    }
}

interface IWeatherDataHandler{
    void weatherDataUpdated(ArrayList<Weather> weatherData);
    void onError(String errorMessage);
}
