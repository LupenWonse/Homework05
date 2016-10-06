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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    public static String CITY_EXTRAS_KEY = "extras_city";
    public static String STATE_EXTRAS_KEY = "extras_state";

    private ArrayList<Favorite> favorites;
    private FavoriteListAdapter favoriteListAdapter;
    private ListView favoriteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoriteListView = (ListView) findViewById(R.id.listFavorites);
        favorites = new ArrayList<>();
        favoriteListAdapter = new FavoriteListAdapter(this,R.layout.favorite_list_row,favorites);
        favoriteListView.setAdapter(favoriteListAdapter);
        favoriteListView.setOnItemLongClickListener(this);
        loadFavoritesList();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadFavoritesList();
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
        favorites.remove(favoriteListAdapter.getItem(position));
        saveFavoritesList();
        loadFavoritesList();
        return true;
    }

    private void saveFavoritesList() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(favorites);

        SharedPreferences.Editor editor = getSharedPreferences("PREFS",0).edit();
        editor.putString("FAVS",jsonString);
        editor.apply();
    }

    private void loadFavoritesList(){
        SharedPreferences preferences = getSharedPreferences("PREFS",MODE_PRIVATE);
        String jsonFavorites = preferences.getString("FAVS",null);

        if( jsonFavorites != null) {
            Gson gson = new Gson();
            this.favorites = gson.fromJson(jsonFavorites, new TypeToken<ArrayList<Favorite>>() {
            }.getType());

            favoriteListAdapter.clear();
            favoriteListAdapter.addAll(favorites);
            favoriteListAdapter.notifyDataSetChanged();
            if (favorites.size() > 0) {
                ((TextView) findViewById(R.id.textFavoritesTitle)).setText(getString(R.string.textLabelFavorites));
            } else {
                ((TextView) findViewById(R.id.textFavoritesTitle)).setText(getString(R.string.textLabelNoFavorites));
            }
        }

    }


}
