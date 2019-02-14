package com.andersonsouza.whatsappandsu.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.andersonsouza.whatsappandsu.fragment.ContatosFragment;
import com.andersonsouza.whatsappandsu.fragment.ConversasFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] titulosAbas = {"CONVERSAS", "CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch (i) {
            case 0: fragment = new ConversasFragment();
                break;
            case 1: fragment = new ContatosFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return titulosAbas.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle (int position) {
        return titulosAbas[position];
    }
}
