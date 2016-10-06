package com.group32.homework05;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ahmet on 05/10/2016.
 */

public class FavoriteListAdapter extends ArrayAdapter<Favorite> {

    private Context context;
    public List<Favorite> favoriteList;
    private int rowResource;

    public FavoriteListAdapter(Context context, int resource, List<Favorite> objects) {
        super(context, resource, objects);
        this.context = context;
        this.favoriteList = objects;
        this.rowResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.rowResource, parent, false);
        }
        Favorite currentFavorite = favoriteList.get(position);

        ((TextView) convertView.findViewById(R.id.textTemperature)).setText(currentFavorite.getTemperature());
        ((TextView) convertView.findViewById(R.id.textLocation)).setText(currentFavorite.getCity());
        ((TextView) convertView.findViewById(R.id.textUpdated)).setText("Updated");

        return convertView;
    }
}
