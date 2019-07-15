package com.example.vargasrt.kofccommunicare;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vargasrt.kofccommunicare.adapter.ProfilesAdapter;
import com.example.vargasrt.kofccommunicare.adapter.RealmProfilesAdapter;
import com.example.vargasrt.kofccommunicare.model.Profile;
import com.example.vargasrt.kofccommunicare.realm.RealmController;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class MyProfilesFragment extends Fragment {

    private String imageSenderProfile;
    private static MyProfilesFragment instance = null;
    private ProfilesAdapter adapter;
    private Realm realm;
    private RecyclerView recycler;

    public static MyProfilesFragment newInstance() {
        return new MyProfilesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myprofiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        instance = this;

        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);

        //get realm instance
        this.realm = RealmController.with(this).getRealm();
        setupRecycler();

        if (!Prefs.with(getContext()).getPreLoad()) {
            setRealmData();
        }
        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        //setRealmAdapter(RealmController.with(this).getBooks());
        setRealmAdapter(RealmController.with(this).getProfiles());

        //Toast.makeText(getContext(), "Press card item for edit, long press to remove item", Toast.LENGTH_LONG).show();
    }

    public static MyProfilesFragment getInstance() {
        return instance;
    }

    public void setRealmAdapter(RealmResults<Profile> profiles) {
        //RealmBooksAdapter realmAdapter = new RealmBooksAdapter(this.getApplicationContext(), books, true);
        RealmProfilesAdapter realmAdapter = new RealmProfilesAdapter(getContext().getApplicationContext(), profiles, true);
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
        adapter = new ProfilesAdapter(getContext());
        recycler.setAdapter(adapter);
    }

    private void setRealmData() {

        ArrayList<Profile> profiles = new ArrayList<>();

        String imageUrl = getURLForResource(R.drawable.ic_profile_face_48dp);

        Profile profile = new Profile();
        profile.setId(1 + System.currentTimeMillis());
        profile.setLastname(null);
        profile.setFirstname(null);
        profile.setMiddlename(null);
        profile.setImageUrl(imageUrl);
        profile.setRegistered(true);
        profiles.add(profile);

        for (Profile p : profiles) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(p);
            realm.commitTransaction();
        }

        //Prefs.with(this).setPreLoad(true);
        Prefs.with(getContext()).setPreLoad(true);

    }

    public String getImageSenderProfile() {
        return imageSenderProfile;
    }

    public void setImageSenderProfile(String imageSenderProfile) {
        this.imageSenderProfile = imageSenderProfile;
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}

