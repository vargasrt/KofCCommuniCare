package com.example.vargasrt.kofccommunicare;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vargasrt.kofccommunicare.adapter.MessagesAdapter;
import com.example.vargasrt.kofccommunicare.adapter.ProfilesAdapter;
import com.example.vargasrt.kofccommunicare.adapter.RealmMessageboxAdapter;
import com.example.vargasrt.kofccommunicare.adapter.RealmProfilesAdapter;
import com.example.vargasrt.kofccommunicare.model.Messagebox;
import com.example.vargasrt.kofccommunicare.model.Profile;
import com.example.vargasrt.kofccommunicare.realm.RealmController;
import com.example.vargasrt.kofccommunicare.realm.RealmControllerMessagebox;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;


public class MyBCINQFragment extends Fragment {

    private MessagesAdapter adapter;
    private Realm realm;
    private RecyclerView recycler;

    public static MyBCINQFragment newInstance() {
        return new MyBCINQFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mybcinq, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_view_bcinq);

        //get realm instance
        this.realm = RealmControllerMessagebox.with(this).getRealm();
        setupRecycler();

        //if (!Prefs.with(getContext()).getPreLoad()) {
            setRealmData();
        //}

        // refresh the realm instance
        RealmControllerMessagebox.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        //setRealmAdapter(RealmController.with(this).getBooks());
        //setRealmAdapter(RealmController.with(this).getProfiles());
        setRealmAdapter(RealmControllerMessagebox.with(this).getMessageboxes());

        //Toast.makeText(getContext(), "Press card item for edit, long press to remove item", Toast.LENGTH_LONG).show();
    }


    public void setRealmAdapter(RealmResults<Messagebox> messageboxes) {
        //RealmBooksAdapter realmAdapter = new RealmBooksAdapter(this.getApplicationContext(), books, true);
        RealmMessageboxAdapter realmAdapter = new RealmMessageboxAdapter(getContext().getApplicationContext(), messageboxes, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        //adapter = new ProfilesAdapter(this);
        adapter = new MessagesAdapter(getContext());
        recycler.setAdapter(adapter);
    }

    private void setRealmData() {

        //ArrayList<Profile> profiles = new ArrayList<>();
        ArrayList<Messagebox> messageboxes = new ArrayList<>();

        /*
        String imageUrl = getURLForResource(R.drawable.ic_profile_face_48dp);

        Profile profile = new Profile();
        profile.setId(1 + System.currentTimeMillis());
        profile.setLastname("Lastname");
        profile.setFirstname("Firstname");
        profile.setMiddlename("Middlename");
        profile.setImageUrl(imageUrl);
        profiles.add(profile);

        for (Profile p : profiles) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(p);
            realm.commitTransaction();
        }
        String  currentDateTimeString = DateFormat.getDateTimeInstance()
                .format(new Date());
        */

        Messagebox messagebox = new Messagebox();
        messagebox.setMsgid( 1 + System.currentTimeMillis());
        messagebox.setMsgtimestamp(DateFormat.getDateTimeInstance().format(new Date()).toString());
        messagebox.setMsgbound("OUTBOX");
        messagebox.setMsgparam1("BCINQ");
        messagebox.setMsgparam2("2055553");
        messagebox.setMsgparam3("04291970");
        messageboxes.add(messagebox);

        for (Messagebox m : messageboxes) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(m);
            realm.commitTransaction();
        }

        //Prefs.with(this).setPreLoad(true);
        //Prefs.with(getContext()).setPreLoad(true);

    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}

