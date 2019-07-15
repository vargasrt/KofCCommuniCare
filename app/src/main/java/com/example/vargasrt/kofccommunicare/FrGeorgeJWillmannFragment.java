package com.example.vargasrt.kofccommunicare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FrGeorgeJWillmannFragment extends Fragment {


    public static FrGeorgeJWillmannFragment newInstance() {
        return new FrGeorgeJWillmannFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frgeorgejwillmann, container, false);
    }
}
