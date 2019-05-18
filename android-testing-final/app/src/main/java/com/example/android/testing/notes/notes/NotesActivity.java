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

package com.example.android.testing.notes.notes;

import com.airbnb.deeplinkdispatch.DeepLink;
import com.airbnb.deeplinkdispatch.DeepLinkHandler;
import com.example.android.testing.notes.R;
import com.example.android.testing.notes.notedetail.NoteDetailActivity;
import com.example.android.testing.notes.statistics.StatisticsActivity;
import com.example.android.testing.notes.util.EspressoIdlingResource;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import static com.example.android.testing.notes.notedetail.NoteDetailActivity.EXTRA_NOTE_ID;
import static com.example.android.testing.notes.util.uploadHelper.ACTION_DETAILS_DEEP_LINK_METHOD;


public class NotesActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private final static String TAG=NotesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notes);



        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if (null == savedInstanceState) {
            initFragment(NotesFragment.newInstance());
        }

        Intent intent = getIntent();
        if(null==intent || null==intent.getData())
            return;
        if (false && intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, true)) {
            String parameter = intent.getData().getQueryParameter("qp");
            if (parameter != null) {
                String queryParameter = parameter;
                // Do something with the query parameter...

                getIntent().setData(null)  ;
                Log.e("NotesActivity", "haiku id received is " + queryParameter);

                Intent deepLinkIntent = new Intent(this, NoteDetailActivity.class);
               // deepLinkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                deepLinkIntent.putExtra(EXTRA_NOTE_ID, queryParameter);
                startActivity(intent);
              //  finish();


            }

            // Do something with idString

            return;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Intent intent2 = getIntent();
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            Bundle parameters = intent2.getExtras();
            String idString = parameters.getString("id");

            Log.e("haiku","haiku id received is "+idString);

            Intent deepLinkIntent = new Intent(this, NoteDetailActivity.class);
            deepLinkIntent.putExtra(EXTRA_NOTE_ID,idString);
            startActivity(intent);

            // Do something with idString

            return;
        }
    }

    private void initFragment(Fragment notesFragment) {
        // Add the NotesFragment to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame, notesFragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.statistics_navigation_menu_item:
                                startActivity(new Intent(NotesActivity.this, StatisticsActivity.class));
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

}
