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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.model.AdapterBase;
import br.com.fraternityhealth.model.AdapterListener;
import br.com.fraternityhealth.model.Specialty;

/**
 * Created by Thiago on 09/12/2016.
 */

public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.ViewHolder> implements AdapterBase {

    private ArrayList<Specialty> list;
    private Context context;
    private LinearLayoutManager layoutManager;
    private AdapterListener listener;

    public SpecialtyAdapter(Context context, LinearLayoutManager layoutManager, ArrayList<Specialty> list) {
        this.context = context;
        this.list = list;
        this.layoutManager = layoutManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specialty, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Specialty specialty = list.get(position);

        holder.setName(specialty.getName()).setAvailable(specialty.getAvailable());

        // Recupera o primeiro item da lista visivel
        //int firstVisible = layoutManager.findFirstCompletelyVisibleItemPosition();

        // Anima o item da lista
        //animate(holder, firstVisible, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Animar um item da lista de livros
     * @param viewHolder Esqueleto de um item dalista
     * @param firstVisible posicao do primeiro item da lista visivel
     * @param position posicao atual da lista
     */
    public void animate(RecyclerView.ViewHolder viewHolder, int firstVisible, int position) {
        /**
         * Carrega a animacao para a view da lista {@linkplain R.anim.slide_right_from_left}
         */
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.show_item_animed);
        // Calcula um delay aleatorio para o item da lista
        int range = calcRange(firstVisible, position);
        // Seta a duracao da animacao
        animAnticipateOvershoot.setDuration(range);

        // Seta para nosso item da lista a animacao
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    /**
     * Calcula um tempo para a animacao baseado na posicao atual e o primeiro item da lista
     * @param firstVisible posicao do primeiro item da lista visivel
     * @param position posicao do item atual da lista
     * @return
     */
    private int calcRange(int firstVisible, int position) {
        // Se precisar aumentar o tempo de animacao basta aumentar 200 milisegundos
        int delay = 200;
        // Calcula a range
        int range = (position - firstVisible) * delay;
        // Normaliza a range se ela retornar negativo
        return (range < 0)? range * -1 : range;
    }

    @Override
    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView vAvailable;
        private final TextView vName;

        public ViewHolder(View itemView) {
            super(itemView);

            vName = (TextView) itemView.findViewById(R.id.name);
            vAvailable = (TextView) itemView.findViewById(R.id.available);

            itemView.setOnClickListener(this);
        }

        public ViewHolder setName(String name){
            if(vName == null) return this;
            vName.setText(name);
            return this;
        }

        public ViewHolder setAvailable(Integer available){
            if(vAvailable == null || available == null) return this;
            vAvailable.setText( AvailableCtrl.print(context, available) );
            return this;
        }

        @Override
        public void onClick(View view) {
            if(listener != null)
                listener.onItemClick(view, getAdapterPosition());
        }
    }
}
