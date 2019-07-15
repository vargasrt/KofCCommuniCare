package com.example.vargasrt.kofccommunicare.adapter;

import android.content.Context;

import com.example.vargasrt.kofccommunicare.model.Messagebox;

import io.realm.RealmResults;

public class RealmMessageboxAdapter extends RealmModelAdapter<Messagebox> {

    public RealmMessageboxAdapter(Context context, RealmResults<Messagebox> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}