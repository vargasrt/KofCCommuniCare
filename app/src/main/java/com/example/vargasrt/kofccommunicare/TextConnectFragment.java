package com.example.vargasrt.kofccommunicare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vargasrt.kofccommunicare.realm.RealmControllerMessagebox;

public class TextConnectFragment extends Fragment {

    public static TextConnectFragment newInstance() {
        return new TextConnectFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_textconnect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = view.findViewById(R.id.view_pager_textconnect);
        KofCFragmentPageAdapter adapter = new KofCFragmentPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        TabLayout tabLayout = view.findViewById(R.id.tabs_textconnect);
        tabLayout.setupWithViewPager(viewPager);
    }

    private static class KofCFragmentPageAdapter extends FragmentPagerAdapter {

        public KofCFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           return TextConnectChildFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "BC Inquiry";
                case 1:
                    return "Allowance";
                case 2:
                    return "Department";
            }
            return null;
        }
    }
}
