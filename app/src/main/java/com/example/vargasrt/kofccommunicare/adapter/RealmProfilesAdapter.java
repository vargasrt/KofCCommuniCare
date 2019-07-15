package com.example.vargasrt.kofccommunicare.adapter;

import android.content.Context;

import io.realm.RealmResults;
import com.example.vargasrt.kofccommunicare.model.Profile;

public class RealmProfilesAdapter extends RealmModelAdapter<Profile> {

    public RealmProfilesAdapter(Context context, RealmResults<Profile> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}