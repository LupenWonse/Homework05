/*
 Homwork 5
 MainActivity
 Akarsh Gupta     - 800969888
 Ahmet Gencoglu   - 800982227
*/
package com.group32.homework05;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    public static String CITY_EXTRAS_KEY = "extras_city";
    public static String STATE_EXTRAS_KEY = "extras_state";
    public static String FAVORITES_PREF_KEY = "FAVS";

    private ArrayList<Favorite> favorites;
    private FavoriteListAdapter favoriteListAdapter;
    private ListView favoriteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the favorites list with adapter
        favoriteListView = (ListView) findViewById(R.id.listFavorites);
        favorites = new ArrayList<>();
        favoriteListAdapter = new FavoriteListAdapter(this,R.layout.favorite_list_row,favorites);
        favoriteListView.setAdapter(favoriteListAdapter);
        favoriteListView.setOnItemLongClickListener(this);
        // Update the favorites list from the Shared Preferences
        loadFavoritesList();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // When the activity is restarted we need to update the favorites list
        loadFavoritesList();
    }



    public void startSearch(View view){
        // User clicks on submit
        EditText textCity = (EditText) findViewById(R.id.editTextCity);
        EditText textState = (EditText) findViewById(R.id.editTextState);

        // Check input
        boolean isValidate = false;
        String city = textCity.getText().toString();
        String state = textState.getText().toString();

        if(city!=null && !city.isEmpty() && state!=null && !state.isEmpty())
            isValidate = true;
        if(isValidate) {
            Intent intent = new Intent(this, CityWeatherActivity.class);
            intent.putExtra(CITY_EXTRAS_KEY, city);
            intent.putExtra(STATE_EXTRAS_KEY, state);
            startActivity(intent);
        }
        else
        {
            if (city.isEmpty() || city==null)
            {
                textCity.setError(getString(R.string.errorNoCity));
            }
            if (state.isEmpty() || state==null)
            {
                textState.setError(getString(R.string.errorNoState));
            }
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // Remove favorite on long click
        favorites.remove(favoriteListAdapter.getItem(position));
        saveFavoritesList();
        loadFavoritesList();
        Toast.makeText(MainActivity.this,"City Deleted",Toast.LENGTH_LONG).show();
        return true;
    }

    private void saveFavoritesList() {
        // Put the favorites list into JSON using the GSON library and store in the
        // SharedPreferences
        Gson gson = new Gson();
        String jsonString = gson.toJson(favorites);

        SharedPreferences.Editor editor = getSharedPreferences("PREFS",0).edit();
        editor.putString(MainActivity.FAVORITES_PREF_KEY,jsonString);
        editor.apply();
    }

    private void loadFavoritesList(){
        // Load the favorites list from the SharedPreferences
        SharedPreferences preferences = getSharedPreferences("PREFS",MODE_PRIVATE);
        String jsonFavorites = preferences.getString(MainActivity.FAVORITES_PREF_KEY,null);

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
