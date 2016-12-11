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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.model.AdapterBase;
import br.com.fraternityhealth.model.AdapterListener;

/**
 * Created by Thiago on 09/12/2016.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> implements AdapterBase {

    private ArrayList<String> list;
    private AdapterListener listener;

    public LocationAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String location = list.get(position);
        holder.setName(location);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView vName;

        public ViewHolder(View itemView) {
            super(itemView);

            vName = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        public ViewHolder setName(String name){
            if(vName == null) return this;
            vName.setText(name);
            return this;
        }

        @Override
        public void onClick(View view) {
            if(listener != null)
                listener.onItemClick(view, getAdapterPosition());
        }
    }
}
