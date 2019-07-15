package com.example.vargasrt.kofccommunicare.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.vargasrt.kofccommunicare.model.Profile;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmControllerProfiles {

    private static RealmControllerProfiles instance;
    private final Realm realm;

    public RealmControllerProfiles(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmControllerProfiles with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmControllerProfiles(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmControllerProfiles with(Activity activity) {

        if (instance == null) {
            instance = new RealmControllerProfiles(activity.getApplication());
        }
        return instance;
    }

    public static RealmControllerProfiles with(Application application) {

        if (instance == null) {
            instance = new RealmControllerProfiles(application);
        }
        return instance;
    }

    public static RealmControllerProfiles getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Profile.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(Profile.class);
        realm.commitTransaction();
    }

    //find all objects in the Profile.class
    public RealmResults<Profile> getProfiles() {

        return realm.where(Profile.class).findAll();
    }



    //query a single item with the given id
    public Profile getProfile(String id) {

        return realm.where(Profile.class).equalTo("id", id).findFirst();
    }

    //check if Profile.class is empty
    public boolean hasProfiles() {

        return !realm.allObjects(Profile.class).isEmpty();
    }

    //query example
    public RealmResults<Profile> queryedProfies() {

        return realm.where(Profile.class)
                .contains("middlename", "Middlename 0")
                .or()
                .contains("lastname", "Lastname")
                .findAll();

    }
}

