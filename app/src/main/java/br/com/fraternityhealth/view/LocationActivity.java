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

package br.com.fraternityhealth.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.control.CitySelected;
import br.com.fraternityhealth.control.Layout;
import br.com.fraternityhealth.control.LocationAdapter;
import br.com.fraternityhealth.control.LocationCtrl;
import br.com.fraternityhealth.control.StateSelected;
import br.com.fraternityhealth.control.StatesLocal;
import br.com.fraternityhealth.model.AdapterListener;
import br.com.fraternityhealth.model.Location;

public class LocationActivity extends AppCompatActivity implements AdapterListener {

    public static final String TAG = "LocationActivity";
    private LocationCtrl control;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Layout.toolbar(this);

        createList();
    }

    private void createList() {
        location = getExtraLocation();
        control = new LocationCtrl(this, location);

        // Se nao existe localizacao cria os estatos
        if(location == null){
            createStates();
        }else{
            createCities();
        }
    }

    private void createCities() {
        getSupportActionBar().setTitle(R.string.title_activity_location_cities);
        createAdapter(location.getCities());
    }

    private void createStates() {
        control.setList(new StatesLocal());
        ArrayList<String> states = control.createList();
        createAdapter(states);
    }

    private void createAdapter(ArrayList list) {
        if(list.isEmpty()) return;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LocationAdapter adapter = new LocationAdapter(list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.locations);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        control.setPosition(position);
        if(location == null){
            stateSelected();
        }else{
            citySelected();
        }
        control.selected();
    }

    private void citySelected() {
        control.setLocationSelected(new CitySelected());
    }

    private void stateSelected() {
        control.setLocationSelected(new StateSelected());
    }

    public Location getExtraLocation() {
        if(!getIntent().hasExtra("state")) return null;
        Location location = new Location();
        location.setState(getIntent().getStringExtra("state"));
        location.setCities(getIntent().getStringArrayListExtra("cities"));
        return location;
    }
}
