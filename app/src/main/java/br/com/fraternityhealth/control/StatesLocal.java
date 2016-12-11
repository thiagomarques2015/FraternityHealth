/*
 * MIT License
 *
 * Copyright (c) 2016 Thiago Marques
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package br.com.fraternityhealth.control;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.fraternityhealth.model.ListItem;
import br.com.fraternityhealth.model.Location;
import br.com.fraternityhealth.view.LocationActivity;

/**
 * Created by Thiago on 10/12/2016.
 */

public class StatesLocal implements ListItem {
    @Override
    public ArrayList<String> createList(Context context, ArrayList<Location> list) {
        String states = Json.loadJSONFromAsset(context, "states");
        ArrayList<String> statesList = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(states);
            JSONObject jStates = json.getJSONObject("states");
            JSONArray stateNames = jStates.names();
            for(int i = 0; i<stateNames.length(); i++){
                Location location = new Location();
                String key = stateNames.getString(i);
                // Adiciona o estado
                location.setState(key);
                statesList.add(key);

                Log.d(LocationActivity.TAG, key);

                JSONObject cities = jStates.getJSONObject(key).getJSONObject("cities");
                JSONArray cityNames = cities.names();
                for(int j = 0; j<cityNames.length(); j++){
                    String city = cityNames.getString(j);
                    // Adiciona as cidades
                    location.addCity(city);
                }

                list.add(location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statesList;
    }
}
