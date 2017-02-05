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

package br.com.fraternityhealth.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thiago on 19/12/2016.
 */
public abstract class DataModel<T> {
    private ArrayList<String> allItemsInSection;
    private ArrayList<T> allItems;
    private ArrayList<Long> allCodeInSection;


    public DataModel() {
        allItemsInSection = new ArrayList<>();
        allItems = new ArrayList<>();
        allCodeInSection = new ArrayList<>();
    }


    public DataModel addSection(String section){
        allItemsInSection.add(section);
        return this;
    }

    public DataModel addItem(T item){
        allItems.add(item);
        return this;
    }

    public long getCode(int pos){
        return allCodeInSection.get(pos);
    }

    public String getHeader(int pos){
        return allItemsInSection.get(pos);
    }

    public DataModel addCodeSection(long code){
        allCodeInSection.add(code);
        return this;
    }

    public int totalSection(){
        return allItemsInSection.size();
    }

    public int totalItem(){
        return allItems.size();
    }

    public int totalCode(){
        return allCodeInSection.size();
    }

    public T getItem(int position) {
        return allItems.get(position);
    }
}
