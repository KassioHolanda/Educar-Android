package com.android.educar.educar.ui.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.network.chamadas.ChamadasDePublicacao;

import java.util.ArrayList;
import java.util.List;

public class SincronizacaoActivity extends AppCompatActivity {

    private ListView menuSincronizacao;
    private List<String> menus;

    private ChamadasDePublicacao chamadasDePublicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizacao);
        binding();
        setupInit();
        onClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recuperarListaDeMenus();
    }

    public void setupInit() {
        chamadasDePublicacao = new ChamadasDePublicacao(getApplicationContext());
        this.menus = new ArrayList<>();
        menus.add("Frequência");
        menus.add("Notas");
        menus.add("Notificação");
    }

    public void binding() {
        menuSincronizacao = findViewById(R.id.menu_sincronizacao_id);
    }

    public void recuperarListaDeMenus() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, menus);
        menuSincronizacao.setAdapter(stringArrayAdapter);
    }

    public void onClick() {
        menuSincronizacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (menuSincronizacao.getItemAtPosition(i).equals("Frequência")) {
                    Snackbar.make(findViewById(android.R.id.content), "Frequência", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();

                } else if (menuSincronizacao.getItemAtPosition(i).equals("Notas")) {
                    Snackbar.make(findViewById(android.R.id.content), "Notas", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();

                } else if (menuSincronizacao.getItemAtPosition(i).equals("Notificação")) {
                    Snackbar.make(findViewById(android.R.id.content), "Notificação", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chamadasDePublicacao.verificarOcorrenciasParaPublicar();
                        }
                    }).show();

                }
            }
        });
    }
}
