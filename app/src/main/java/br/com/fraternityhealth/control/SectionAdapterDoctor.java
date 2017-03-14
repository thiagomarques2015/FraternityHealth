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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.model.AdapterBase;
import br.com.fraternityhealth.model.AdapterListener;
import br.com.fraternityhealth.model.DataModel;
import br.com.fraternityhealth.model.Doctor;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by Thiago on 19/12/2016.
 */
public class SectionAdapterDoctor extends RecyclerView.Adapter<SectionAdapterDoctor.ItemViewHolder> implements
        StickyHeaderAdapter<SectionAdapterDoctor.SectionViewHolder>, AdapterBase {

    private AdapterListener listener;
    private DataModel allData;
    private Context context;
    private AdapterListener adapterListener;

    public SectionAdapterDoctor(Context context, DataModel data) {
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
    public void onBindHeaderViewHolder(SectionAdapterDoctor.SectionViewHolder viewholder, int position) {
        String sectionName = allData.getHeader(position);
        viewholder.sectionTitle.setText(sectionName);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_doctor, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Doctor item = (Doctor) allData.getItem(position);
        holder.itemTitle.setText(item.getName());
        holder.itemDescription.setText(item.getDescription());
        holder.itemSubtitle.setText(item.getCrm());

        // Ativa as horas do agendamento
        holder.setSchedule(item.getSchedule());
    }

    @Override
    public int getItemCount() {
        return allData.totalItem();
    }

    public void setScheduleListener(AdapterListener listener){
        this.listener = listener;
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
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterListener {

        final TextView itemTitle;
        private final TextView itemSubtitle;
        private final TextView itemDescription;
        private final RecyclerView scheduleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.title);
            itemSubtitle = (TextView) itemView.findViewById(R.id.subtitle);
            itemDescription = (TextView) itemView.findViewById(R.id.description);
            scheduleView = (RecyclerView) itemView.findViewById(R.id.schedule);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(adapterListener != null)
                adapterListener.onItemClick(view, getAdapterPosition());
        }

        public void setSchedule(ArrayList<String> schedule) {
            if(scheduleView == null) return;
            ScheduleAdapter adapter = new ScheduleAdapter(schedule);
            scheduleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            scheduleView.setAdapter(adapter);
            adapter.setListener(this);
        }

        @Override
        public void onItemClick(View view, int position) {
            if(listener != null)
                listener.onItemClick(view, position);
        }
    }
}
