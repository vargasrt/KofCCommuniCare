package com.example.vargasrt.kofccommunicare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vargasrt.kofccommunicare.adapter.MessagesAdapter;
import com.example.vargasrt.kofccommunicare.adapter.RealmMessageboxAdapter;
import com.example.vargasrt.kofccommunicare.model.Messagebox;
import com.example.vargasrt.kofccommunicare.realm.RealmControllerMessagebox;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainChildFragment extends Fragment {

    private static final String ARGUMENT_POSITION = "argument_position";
    private Realm realm;
    private RecyclerView recycler;
    private MessagesAdapter adapter;

    public static MainChildFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_POSITION, position);
        MainChildFragment fragment = new MainChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_main_child, container, false);
        return inflater.inflate(R.layout.fragment_main_child, container, false);
    }


    @SuppressLint("SetTextI18n")
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        TextView tvMain = view.findViewById(R.id.tv_main);

        recycler = (RecyclerView) view.findViewById(R.id.recycler_view_announcement);

        //get realm instance
        this.realm = RealmControllerMessagebox.with(this).getRealm();
        setupRecycler();

        int position = getArguments().getInt(ARGUMENT_POSITION, -1);
        //tvMain.setText(position == 0 ? R.string.Daily_Verse : R.string.life_is_a_very_funny_proposition_after_all);
        switch (position){
            case 0:
                setRealmAdapter(RealmControllerMessagebox.with(this).queryedVERSEMessageboxes());
//                tvMain.setText(R.string.DailyVerse);
                return;
            case 1:
//                tvMain.setText(R.string.NewsEvents);
                setRealmAdapter(RealmControllerMessagebox.with(this).queryedNEWSMessageboxes());
                return;
            case 2:
//                tvMain.setText(R.string.Notices);
                setRealmAdapter(RealmControllerMessagebox.with(this).queryedNOTICEMessageboxes());
                return;
            case 3:
//                tvMain.setText(R.string.BCInfo);
                setRealmAdapter(RealmControllerMessagebox.with(this).queryedBCINFOMessageboxes());
                return;
        }
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        recycler.setLayoutManager(layoutManager);
        // create an empty adapter and add it to the recycler view
        //adapter = new ProfilesAdapter(this);
        //adapter = new MessagesAdapter(getContext());
        adapter = new MessagesAdapter(getContext());
        recycler.setAdapter(adapter);
    }

    public void setRealmAdapter(RealmResults<Messagebox> messageboxes) {
        //RealmBooksAdapter realmAdapter = new RealmBooksAdapter(this.getApplicationContext(), books, true);
        RealmMessageboxAdapter realmAdapter = new RealmMessageboxAdapter(getContext().getApplicationContext(), messageboxes, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

}