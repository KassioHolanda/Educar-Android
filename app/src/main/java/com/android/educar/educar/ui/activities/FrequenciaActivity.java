package com.android.educar.educar.ui.activities;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.educar.educar.R;
import com.android.educar.educar.mb.FrequenciaMB;
import com.android.educar.educar.ui.fragments.FrequenciaFragment;
import com.android.educar.educar.model.PessoaFisica;

import java.util.List;

import io.realm.Realm;

public class FrequenciaActivity extends AppCompatActivity {

    private FrequenciaMB frequenciaMB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequencia);
        setupInit();
    }

    public void setupInit() {
        frequenciaMB = new FrequenciaMB(getApplicationContext());
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragment.beginTransaction();

        Fragment fragment1 = new FrequenciaFragment();

        fragmentTransaction.add(R.id.layout_frequencia_id, fragment1);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        frequenciaMB.salvarFrequencia();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dados_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_infor_aluno:
                alertaInformacao();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void alertaInformacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informações!");
        builder.setMessage("Realize a Frequecia de suas Aulas!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }
}
