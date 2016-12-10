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
import android.util.Log;

import com.google.gson.Gson;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.control.ActivityCtrl;
import br.com.fraternityhealth.control.JSONCtrl;
import br.com.fraternityhealth.control.SpecialtyAdapter;
import br.com.fraternityhealth.model.AvailableSpecialty;

public class SpecialtyActivity extends AppCompatActivity {

    private static final String TAG = "SpecialtyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty);

        ActivityCtrl.toolbar(this);

        createList();
    }

    private void createList() {
        String json = JSONCtrl.loadJSONFromAsset(this, "specialties");
        Gson gson = new Gson();
        AvailableSpecialty list = gson.fromJson(json, AvailableSpecialty.class);
        Log.d(TAG, list.getSpecialties().size() + " available ");
        createAdapter(list);
    }

    private void createAdapter(AvailableSpecialty list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        SpecialtyAdapter adapter = new SpecialtyAdapter(this, layoutManager, list.getSpecialties());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.specialties);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
