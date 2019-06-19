package com.example.personasmercandiser;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> FRAGMENT_LIST = new ArrayList<>();
    private final List<String> FRAGMENT_LIST_TITLES = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return FRAGMENT_LIST.get(i);
    }

    @Override
    public int getCount() {
        return FRAGMENT_LIST_TITLES.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FRAGMENT_LIST_TITLES.get(position);
    }

    public void AddFragment(Fragment fragment, String title) {
        FRAGMENT_LIST.add(fragment);
        FRAGMENT_LIST_TITLES.add(title);

    }

}
