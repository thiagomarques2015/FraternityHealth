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

import java.util.ArrayList;

import br.com.fraternityhealth.model.ListItem;
import br.com.fraternityhealth.model.Location;
import br.com.fraternityhealth.model.LocationSelected;

/**
 * Created by Thiago on 10/12/2016.
 */

public class LocationCtrl {
    private ListItem listItem;
    private LocationSelected locationSelected;
    private final Context context;
    private ArrayList<Location> list;
    private Location location;
    private int position;

    public LocationCtrl(Context context, Location location) {
        list = new ArrayList<>();
        this.context = context;
        this.location = location;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setLocationSelected(LocationSelected locationSelected) {
        this.locationSelected = locationSelected;
    }

    public void setList(ListItem location) {
        this.listItem = location;
    }

    public ArrayList<String> createList(){
        return listItem.createList(context, list);
    }

    public void selected(){
        locationSelected.selected(context, list, location, position);
    }

    public ArrayList<Location> getList() {
        return list;
    }
}
