package com.android.educar.educar.app;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.educar.educar.R;
import com.android.educar.educar.adapter.TabAdapter;
import com.android.educar.educar.service.APIService;
import com.android.educar.educar.utils.Preferences;
import com.android.educar.educar.utils.SlidingTabLayout;
import com.android.educar.educar.utils.UtilsFunctions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class AulaActivity extends AppCompatActivity {


    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private Preferences preferences;
    private APIService apiService;
    private UtilsFunctions utilsFunctions;
    private MaterialSearchView materialSearchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);
        binding();
        setupInit();
        settings();
        atualizarAdapter();
        settings();
    }

    public void setupInit() {
        preferences = new Preferences(this);
        apiService = new APIService("");
        utilsFunctions = new UtilsFunctions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        getMenuInflater().inflate(R.menu.menu_dados_alunos, menu);
        getMenuInflater().inflate(R.menu.menu_item_sair, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(menuItem);
        return true;
    }

    public void binding() {
        slidingTabLayout = findViewById(R.id.stl_tabs);
        viewPager = findViewById(R.id.vp_pagina);
        viewPager.setBackgroundColor(ContextCompat.getColor(this, R.color.colorEdit));
        materialSearchView = findViewById(R.id.search_viewaula__id);
    }

    public void atualizarAdapter() {
        slidingTabLayout.setDistributeEvenly(true);
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    public void settings() {
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }
}
