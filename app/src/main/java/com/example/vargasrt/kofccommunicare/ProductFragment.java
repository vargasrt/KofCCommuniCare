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

public class ProductFragment extends Fragment {


    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.view_pager_product);
        KofCFragmentPageAdapter adapter = new KofCFragmentPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        TabLayout tabLayout = view.findViewById(R.id.tabs_product);
        tabLayout.setupWithViewPager(viewPager);
    }

    private static class KofCFragmentPageAdapter extends FragmentPagerAdapter {

        public KofCFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ProductChildFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Protection";
                case 1:
                    return "Retirement";
                case 2:
                    return "Savings";
                case 3:
                    return "Investment";
            }
            return null;
        }
    }

}
