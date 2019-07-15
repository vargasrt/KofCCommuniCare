package com.example.vargasrt.kofccommunicare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShieldChildFragment extends Fragment {

    private static final String ARGUMENT_POSITION = "argument_position";

    public static ShieldChildFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_POSITION, position);
        ShieldChildFragment fragment = new ShieldChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shield_child, container, false);
    }


    @SuppressLint("SetTextI18n")
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvShield = view.findViewById(R.id.tv_shield);
        int position = getArguments().getInt(ARGUMENT_POSITION, -1);
        //tvShield.setText(position == 0 ? R.string.KCFAPI_QualityPolicy: R.string.KCFAPI_Mission);
    }
}