package com.group32.homework05;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    public static String CITY_EXTRAS_KEY = "extras_city";
    public static String STATE_EXTRAS_KEY = "extras_state";

    private ArrayList<Favorite> favorites;
    private FavoriteListAdapter favoriteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView favoriteList = (ListView) findViewById(R.id.listFavorites);
        favoriteList.setOnItemLongClickListener(this);

        SharedPreferences preferences = getSharedPreferences("PREFS",MODE_PRIVATE);
        String jsonFavorites = preferences.getString("FAVS",null);
        if( jsonFavorites != null) {

            Gson gson = new Gson();
            favorites = gson.fromJson(jsonFavorites, new TypeToken<ArrayList<Favorite>>(){}.getType());
            favoriteListAdapter = new FavoriteListAdapter(this, R.layout.favorite_list_row, favorites);
            favoriteList.setAdapter(favoriteListAdapter);
        } else {
            Log.d("test","Could not get Preferences");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences preferences = getSharedPreferences("PREFS",MODE_PRIVATE);
        String jsonFavorites = preferences.getString("FAVS",null);
        if( jsonFavorites != null) {

            Gson gson = new Gson();
            favorites = gson.fromJson(jsonFavorites, new TypeToken<ArrayList<Favorite>>(){}.getType());
            favoriteListAdapter.notifyDataSetChanged();
        } else {
            Log.d("test","Could not get Preferences");
        }
    }

    public void startSearch(View view){
        EditText textCity = (EditText) findViewById(R.id.editTextCity);
        EditText textState = (EditText) findViewById(R.id.editTextState);
        //TODO Check input

        String city = textCity.getText().toString();
        String state = textState.getText().toString();

        Intent intent = new Intent(this,CityWeatherActivity.class);
        intent.putExtra(CITY_EXTRAS_KEY,city);
        intent.putExtra(STATE_EXTRAS_KEY,state);
        startActivity(intent);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        favoriteListAdapter.remove(favoriteListAdapter.getItem(position));
        favoriteListAdapter.notifyDataSetChanged();

        return true;
    }
}
