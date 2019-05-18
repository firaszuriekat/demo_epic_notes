/*
 * Copyright 2015, The Android Open Source Project
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

package com.example.android.testing.notes.data;

import android.support.v4.util.ArrayMap;

import java.util.List;

/**
 * This is the endpoint for your data source. Typically, it would be a SQLite db and/or a server
 * API. In this example, we fake this by creating the data on the fly.
 */
public final class NotesServiceApiEndpoint {

    static {
        DATA = new ArrayMap(2);
        addNote("NurseOne", "https://applink.patienttouch.com?username=nurse1", null);
        addNote("NurseTwo", "https://applink.patienttouch.com?username=n2", null);
        //addNote("3", "UI Testing for Android", null);
       // addNote("4", "UI Testing for Android", null);
    }

    private final static ArrayMap<String, Note> DATA;

    private static void addNote(String title, String description, String imageUrl) {
        Note newNote = new Note(title, description, imageUrl);
        DATA.put(newNote.getId(), newNote);
    }

    private static void addNote(String title, String description, String imageUrl, String id) {
        Note newNote = new Note(title, description, imageUrl,id);
        newNote.setId(id);
        DATA.put(id, newNote);
    }
    public static void addNotes(List<Patient> patients) {
        Note note=null;
        for (Patient p: patients
             ) {
            
             addNote("PSS "+ String.valueOf(p.getPatientId()),p.getSurname(),null,String.valueOf(p.getPatientId()));

        }
    }


    List<Note> getNotes() {
        for (Note note:DATA.values()
             ) {

        }
        return null;
    }

    /**
     * @return the Notes to show when starting the app.
     */
    public static ArrayMap<String, Note> loadPersistedNotes() {
        return DATA;
    }
}
