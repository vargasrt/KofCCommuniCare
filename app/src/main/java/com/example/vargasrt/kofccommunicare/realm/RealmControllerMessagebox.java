package com.example.vargasrt.kofccommunicare.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.vargasrt.kofccommunicare.SMSServices;
import com.example.vargasrt.kofccommunicare.model.Messagebox;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmControllerMessagebox {

    private static RealmControllerMessagebox instance;
    private final Realm realm;

    public RealmControllerMessagebox(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmControllerMessagebox with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmControllerMessagebox(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmControllerMessagebox with(SMSServices activity) {

        if (instance == null) {
            instance = new RealmControllerMessagebox(activity.getApplication());
        }
        return instance;
    }

    public static RealmControllerMessagebox with(Application application) {

        if (instance == null) {
            instance = new RealmControllerMessagebox(application);
        }
        return instance;
    }

    public static RealmControllerMessagebox getInstance() {

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
        realm.clear(Messagebox.class);
        realm.commitTransaction();
    }

    //find all objects in the Profile.class
    public RealmResults<Messagebox> getMessageboxes() {

        return realm.where(Messagebox.class).findAll();
    }



    //query a single item with the given id
    public Messagebox getMessagebox(String msgid) {

        return realm.where(Messagebox.class).equalTo("msgid", msgid).findFirst();
    }

    //check if Profile.class is empty
    public boolean hasMessageboxes() {

        return !realm.allObjects(Messagebox.class).isEmpty();
    }

    //query example
    public RealmResults<Messagebox> queryedALLWMessageboxes() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "ALLW")
                .or()
                .contains("msgparam1", "SRP")
                .findAll();
    }

    //query example
    public RealmResults<Messagebox> queryedDEPTMessageboxes() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "UND")
                .or()
                .contains("msgparam1", "FBG")
                .or()
                .contains("msgparam1", "FMAS")
                .or()
                .contains("msgparam1", "SERVICE")
                .or()
                .contains("msgparam1", "FGJWF")
                .or()
                .contains("msgparam1", "MIS")
                .findAll();
    }

    //query example
    public RealmResults<Messagebox> queryedBCINQMessageboxes() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "BCINQ")
                .findAll();
    }

    public int queryedBCINQMessageboxesCount() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "BCINQ")
                .findAll().size() - 1;
    }

    public RealmResults<Messagebox> queryedVERSEMessageboxes() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "VERSE")
                .findAll();
    }

    public int queryedVERSEMessageboxesCount() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "VERSE")
                .findAll().size() - 1;
    }

    public RealmResults<Messagebox> queryedNEWSMessageboxes() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "NEWS")
                .findAll();
    }

    public int queryedNEWSMessageboxesCount() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "NEWS")
                .findAll().size() - 1;
    }

    public RealmResults<Messagebox> queryedNOTICEMessageboxes() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "NOTICE")
                .findAll();
    }

    public int queryedNOTICEMessageboxesCount() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "NOTICE")
                .findAll().size() - 1;
    }

    public RealmResults<Messagebox> queryedBCINFOMessageboxes() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "BCINFO")
                .findAll();
    }

    public int queryedBCINFOMessageboxesCount() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "BCINFO")
                .findAll().size() - 1;
    }

    public int queryedALLWMessageboxesCount() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "ALLW")
                .or()
                .contains("msgparam1", "SRP")
                .findAll().size() - 1;
    }

    public int queryedDEPTMessageboxesCount() {

        return realm.where(Messagebox.class)
                .contains("msgparam1", "UND")
                .or()
                .contains("msgparam1", "FBG")
                .or()
                .contains("msgparam1", "FMAS")
                .or()
                .contains("msgparam1", "SERVICE")
                .or()
                .contains("msgparam1", "FGJWF")
                .findAll().size() - 1;
    }
}

