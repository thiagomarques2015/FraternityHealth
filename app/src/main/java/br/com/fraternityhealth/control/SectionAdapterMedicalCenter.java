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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.model.AdapterBase;
import br.com.fraternityhealth.model.AdapterListener;
import br.com.fraternityhealth.model.DataModel;
import br.com.fraternityhealth.model.MedicalCenter;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by Thiago on 19/12/2016.
 */
public class SectionAdapterMedicalCenter extends RecyclerView.Adapter<SectionAdapterMedicalCenter.ItemViewHolder> implements
        StickyHeaderAdapter<SectionAdapterMedicalCenter.SectionViewHolder>, AdapterBase {

    private DataModel allData;
    private Context context;
    private AdapterListener adapterListener;

    public SectionAdapterMedicalCenter(Context context, DataModel data) {
        this.allData = data;
        this.context = context;
    }

    @Override
    public long getHeaderId(int position) {
        return allData.getCode(position);
    }

    @Override
    public SectionViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_section, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(SectionAdapterMedicalCenter.SectionViewHolder viewholder, int position) {
        String sectionName = allData.getHeader(position);
        viewholder.sectionTitle.setText(sectionName);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_medical_center, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        MedicalCenter item = (MedicalCenter) allData.getItem(position);
        holder.itemTitle.setText(item.getName());
        holder.itemSubtitle.setText(item.getAddress());
        holder.itemAvailable.setText(AvailableCtrl.print( context, item.getAvailable() ));
    }

    @Override
    public int getItemCount() {
        return allData.totalItem();
    }

    @Override
    public void setListener(AdapterListener listener) {
        this.adapterListener = listener;
    }

    // SectionViewHolder Class for Sections
    public static class SectionViewHolder extends RecyclerView.ViewHolder {

        final TextView sectionTitle;
        public SectionViewHolder(View itemView) {
            super(itemView);
            sectionTitle = (TextView) itemView.findViewById(R.id.sectionTitle);
        }
    }

    // ItemViewHolder Class for Items in each Section
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView itemTitle;
        private final TextView itemSubtitle;
        private final TextView itemAvailable;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.title);
            itemSubtitle = (TextView) itemView.findViewById(R.id.subtitle);
            itemAvailable = (TextView) itemView.findViewById(R.id.available);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(adapterListener != null)
                adapterListener.onItemClick(view, getAdapterPosition());
        }
    }
}
