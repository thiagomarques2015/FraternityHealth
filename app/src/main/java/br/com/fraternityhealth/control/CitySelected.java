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
import android.content.Intent;

import java.util.ArrayList;

import br.com.fraternityhealth.model.Location;
import br.com.fraternityhealth.model.LocationSelected;
import br.com.fraternityhealth.view.SpecialtyActivity;

/**
 * Created by Thiago on 10/12/2016.
 */

public class CitySelected implements LocationSelected {
    @Override
    public void selected(Context context, ArrayList<Location> list, Location location, int position) {
        String state = location.getState();
        String city = location.getCities().get(position);

        Intent intent = new Intent(context, SpecialtyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        Preferences.instance(context)
                .editor()
                .putString("state", state)
                .putString("city", city)
                .apply();

        context.startActivity(intent);
    }
}
