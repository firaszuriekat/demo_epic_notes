/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.notes.notedetail;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.example.android.testing.notes.R;
import com.example.android.testing.notes.notes.NotesActivity;
import com.example.android.testing.notes.util.EspressoIdlingResource;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Displays note details screen.
 */
@DeepLink({"epichaiku://epichaiku.com/deepLink/{id}","rover://test" })
public class NoteDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "NOTE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested note id
        String noteId = getIntent().getStringExtra(EXTRA_NOTE_ID);



        Intent intent = getIntent();
        if(null==intent )
            return;
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, true)) {
            try {
                String parameter = intent.getData().getQueryParameter("patientid");
                if (parameter != null) {
                    String queryParameter = parameter;
                    // Do something with the query parameter...
                    noteId=queryParameter;
                    getIntent().setData(null)  ;
                    Log.e("NoteDetailActivity", "haiku id received is " + queryParameter);


                    //  finish();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Do something with idString

           // return;
        }


        initFragment(NoteDetailFragment.newInstance(noteId));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initFragment(Fragment detailFragment) {
        // Add the NotesDetailFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, detailFragment);
        transaction.commit();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent notesInternt= new Intent(this, NotesActivity.class);
        startActivity(notesInternt);
        finish();
    }
}
