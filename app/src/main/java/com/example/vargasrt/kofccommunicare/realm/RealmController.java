package com.example.vargasrt.kofccommunicare.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import io.realm.Realm;
import io.realm.RealmResults;

import com.example.vargasrt.kofccommunicare.model.Messagebox;
import com.example.vargasrt.kofccommunicare.model.Profile;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

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

