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

import br.com.fraternityhealth.model.DataModel;
import br.com.fraternityhealth.model.DataModelDoctor;
import br.com.fraternityhealth.model.DataModelMedicalCenter;
import br.com.fraternityhealth.model.Doctor;
import br.com.fraternityhealth.model.ListItem;
import br.com.fraternityhealth.model.MedicalCenter;

/**
 * Created by Thiago on 05/03/2017.
 */

public class DoctorListLocal implements ListItem<DataModelDoctor> {

    @Override
    public DataModelDoctor createList(Context context) {

        DataModel allSampleData = DataModelDoctor.newInstance();

        for (int i = 1; i <= 10; i++) {

            for (int j = 1; j <= 5; j++) {
                Doctor doctor = Doctor.newInstance();
                doctor.setName("Tatiane Oliveira da Silva " + i);
                doctor.setDescription("Não atende menores de 14 anos");
                doctor.setCrm("CRM-AM 163-986");
                for(int k=0; k<=5; k++)
                    doctor.addSchedule(String.format("%s%s:%s%s", i, k, i, k));
                //noinspection unchecked
                allSampleData.addItem(doctor);
                allSampleData.addSection("Segunda-feira, 19 de dezembro de 2016");
                allSampleData.addCodeSection(i);
            }

        }

        return (DataModelDoctor) allSampleData;
    }
}
