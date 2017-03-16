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
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.model.FirebaseCommand;
import br.com.fraternityhealth.view.MainActivity;

/**
 * Created by Thiago on 16/03/2017.
 */

public class FirebaseTotalAvailable implements FirebaseCommand {
    private static final String TAG = "FirebaseTotalAvailable";

    TextView mAvailable;
    private Context context;

    public FirebaseTotalAvailable(TextView mAvailable) {
        this.mAvailable = mAvailable;
    }

    public void setAvailable(TextView mAvailable) {
        this.mAvailable = mAvailable;
    }

    @Override
    public void execute(Context context) {
        this.context = context;
        Log.d(TAG, "Ler o total de vagas" );
        // Read a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dashboard");

        myRef.child("available").addValueEventListener(onReadValue);
    }

    ValueEventListener onReadValue = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Log.d(TAG, "Valor recebido =" + dataSnapshot.getKey() );

            if(dataSnapshot.getValue() == null) return;

            switch (dataSnapshot.getKey()){
                case "available" :
                    int total = Integer.parseInt(dataSnapshot.getValue().toString());
                    Log.d(TAG, "Total " + total);
                    mAvailable.setText(AvailableCtrl.print(context, total));
                    break;
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
