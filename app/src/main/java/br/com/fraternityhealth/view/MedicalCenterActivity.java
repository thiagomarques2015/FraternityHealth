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
import android.widget.TextView;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.control.Layout;
import br.com.fraternityhealth.control.RecyclerViewSectionAdapter;
import br.com.fraternityhealth.model.DataModel;
import br.com.fraternityhealth.model.MedicalCenter;
import ca.barrenechea.widget.recyclerview.decoration.DividerDecoration;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

public class MedicalCenterActivity extends AppCompatActivity {

    DataModel allSampleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_center);
        Layout.toolbar(this);

        setHeader();
        createList();
    }

    private void setHeader() {
        String title = getIntent().getStringExtra("name");
        TextView vName = (TextView) findViewById(R.id.name);
        vName.setText(title);
    }

    private void createList() {
        populateSampleData();
        createAdapter(allSampleData);
    }

    private void createAdapter(DataModel allSampleData) {
        RecyclerView mList = (RecyclerView) findViewById(R.id.medical);
        final DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.default_header_color)
                .build();
        RecyclerViewSectionAdapter adapter = new RecyclerViewSectionAdapter(this, allSampleData);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(manager);
        mList.addItemDecoration(divider);
        StickyHeaderDecoration decor = new StickyHeaderDecoration(adapter);
        mList.addItemDecoration(decor, 1);
        mList.setAdapter(adapter);
    }

    private void populateSampleData() {

        allSampleData = new DataModel();

        for (int i = 1; i <= 10; i++) {

            for (int j = 1; j <= 5; j++) {
                MedicalCenter medicalCenter = MedicalCenter.newInstance();
                medicalCenter.setName("Item " + i);
                medicalCenter.setAddress("Av. Nove de Julho");
                medicalCenter.setAvailable(j + i);
                allSampleData.addItem(medicalCenter);
                allSampleData.addSection("Segunda-feira, 19 de dezembro de 2016");
                allSampleData.addCodeSection(i);
            }

        }
    }

}
