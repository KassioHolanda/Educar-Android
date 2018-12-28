package com.android.educar.educar.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.educar.educar.ui.fragments.FrequenciaFragment;
import com.android.educar.educar.ui.fragments.NotaFragment;
import com.android.educar.educar.ui.fragments.OcorrenciaFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] titutoAbas = {"FREQUÊNCIA", "NOTAS", "OCORRÊNCIAS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new FrequenciaFragment();
                break;
            case 1:
                fragment = new NotaFragment();
                break;
            case 2:
                fragment = new OcorrenciaFragment();
        }

        return fragment;
    }



    @Override
    public int getCount() {
        return titutoAbas.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titutoAbas[position];
    }
}
