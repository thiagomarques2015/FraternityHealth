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

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.control.DoctorCtrl;
import br.com.fraternityhealth.control.DoctorListLocal;
import br.com.fraternityhealth.control.Layout;
import br.com.fraternityhealth.control.SectionAdapterDoctor;
import br.com.fraternityhealth.control.SectionAdapterMedicalCenter;
import br.com.fraternityhealth.model.AdapterListener;
import br.com.fraternityhealth.model.AppActivity;
import br.com.fraternityhealth.model.AppList;
import br.com.fraternityhealth.model.DataModel;
import br.com.fraternityhealth.model.DataModelDoctor;
import br.com.fraternityhealth.model.MedicalCenter;
import ca.barrenechea.widget.recyclerview.decoration.DividerDecoration;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

public class DoctorActivity extends AppActivity implements AppList<DataModel>, AdapterListener {

    private static final String LABEL = "DoctorActivity";
    private DataModelDoctor allSampleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        getMedicalCenter();
        createList();
    }

    private void getMedicalCenter() {
        MedicalCenter medicalCenter = (MedicalCenter) getIntent().getSerializableExtra("medical_center");
        if(medicalCenter == null) return;
        Log.d(LABEL, "Medical  : " + medicalCenter.getAddress());
    }

    @Override
    public void createList() {
        DoctorCtrl control = new DoctorCtrl(this);
        control.setList(new DoctorListLocal());
        allSampleData = control.createList();
        createAdapter(allSampleData);
    }

    @Override
    public void createAdapter(DataModel allSampleData) {
        RecyclerView mList = (RecyclerView) findViewById(R.id.doctors);
        final DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.default_header_color)
                .build();
        SectionAdapterDoctor adapter = new SectionAdapterDoctor(this, allSampleData);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(manager);
        mList.addItemDecoration(divider);
        StickyHeaderDecoration decor = new StickyHeaderDecoration(adapter);
        mList.addItemDecoration(decor, 1);
        mList.setAdapter(adapter);
        // Enable item click
        adapter.setListener(this);
        adapter.setScheduleListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.schedule :
                startActivity(new Intent(this, ViewDoctorActivity.class));
                break;
        }
    }
}
