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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.control.Layout;
import br.com.fraternityhealth.control.Json;
import br.com.fraternityhealth.control.Preferences;
import br.com.fraternityhealth.control.SpecialtyAdapter;
import br.com.fraternityhealth.control.SpecialtyCtrl;
import br.com.fraternityhealth.control.SpecialtyListLocal;
import br.com.fraternityhealth.model.AdapterListener;
import br.com.fraternityhealth.model.AppList;
import br.com.fraternityhealth.model.AvailableSpecialty;
import br.com.fraternityhealth.model.Specialty;

public class SpecialtyActivity extends AppCompatActivity implements AppList<ArrayList<Specialty>>, AdapterListener {

    private static final String TAG = "SpecialtyActivity";
    private ArrayList<Specialty> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty);

        Layout.toolbar(this);

        createList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String state = Preferences.instance(this).get().getString("state", "");
        String city = Preferences.instance(this).get().getString("city", "");
        Log.d(TAG, "Selected " + state + " - " + city);
        setFilter(state, city);
    }

    private void setFilter(String state, String city) {
        TextView vFilter = (TextView) findViewById(R.id.filter);
        String filter = (state.isEmpty())? "Brasil" : city + ", " + state;
        vFilter.setText(filter);
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle("256 vagas");
        // @TODO do the new filter
    }

    @Override
    public void createList() {
        SpecialtyCtrl control = new SpecialtyCtrl(this);
        control.setList(new SpecialtyListLocal());
        list = control.createList();
        Log.d(TAG, list.size() + " available ");
        createAdapter(list);
    }

    @Override
    public void createAdapter(ArrayList<Specialty> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        SpecialtyAdapter adapter = new SpecialtyAdapter(this, layoutManager, list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.specialties);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
    }

    public void openLocations(View view) {
        startActivity(new Intent(this, LocationActivity.class));
    }

    @Override
    public void onItemClick(View view, int position) {
        Specialty specialty = list.get(position);
        Intent intent = new Intent(this, MedicalCenterActivity.class);
        intent.putExtra("name", specialty.getName());
        startActivity(intent);
    }
}
