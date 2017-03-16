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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.fraternityhealth.R;
import br.com.fraternityhealth.control.FirebaseCtrl;
import br.com.fraternityhealth.control.FirebaseLoginAnonymously;
import br.com.fraternityhealth.control.FirebaseTotalAvailable;
import br.com.fraternityhealth.model.AppActivity;
import br.com.fraternityhealth.model.FirebaseCommand;


public class MainActivity extends AppActivity<FirebaseCommand> {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseCtrl firebaseCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        mAuth = FirebaseAuth.getInstance();
        firebaseCtrl = new FirebaseCtrl(this);

        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_main);

        doAuth();
        doSignInAnonymously();
    }

    public void doSignInAnonymously(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) return;
        FirebaseLoginAnonymously command = new FirebaseLoginAnonymously(this);
        // Execute login anonymously
        execute(command);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void doAuth() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               checkAuth();
            }
        };
    }

    private void checkAuth() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            getAvailable();
        } else {
            // No user is signed in
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
    }

    private void getAvailable() {
        TextView mAvailable = (TextView) findViewById(R.id.available);
        FirebaseTotalAvailable command = new FirebaseTotalAvailable(mAvailable);
        // Execute query to get total available
        execute(command);
    }

    @Override
    protected void execute(FirebaseCommand command) {
        firebaseCtrl.setCommand(command).execute();
    }

    public void newScheduling(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Aguarde enquanto as vagas são carregadas", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(this, SpecialtyActivity.class));
    }
}
