package com.group32.homework05;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ahmet on 04/10/2016.
 */

public class WeatherListAdapter extends ArrayAdapter<Weather> {

    private Context context;
    private List<Weather> weatherList;
    private int rowResource;

    public WeatherListAdapter(Context context, int resource, List<Weather> objects) {
        super(context, resource, objects);
        this.context = context;
        this.weatherList = objects;
        this.rowResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.rowResource, parent, false);
        }

        Weather currentWeather = weatherList.get(position);

        ((TextView) convertView.findViewById(R.id.textTemperature)).setText(String.format("%s° F",currentWeather.getTemperature()));

        DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        Date currentDate = new Date(Long.decode(currentWeather.getTime())*1000);
        ((TextView) convertView.findViewById(R.id.textHour)).setText(dateFormat.format(currentDate));

        ((TextView) convertView.findViewById(R.id.textWeather)).setText(currentWeather.getClimateType());

        Picasso.with(convertView.getContext()).load(currentWeather.getIconURL()).into((ImageView) convertView.findViewById(R.id.imageWeather));

        return convertView;
    }
}
