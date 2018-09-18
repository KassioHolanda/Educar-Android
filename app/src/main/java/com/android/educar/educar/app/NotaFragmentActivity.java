package com.android.educar.educar.app;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.educar.educar.R;
import com.android.educar.educar.fragments.NotaFragment;

public class NotaFragmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_nota);
        binding();
        setupInit();
    }

    public void setupInit() {
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragment.beginTransaction();

        Fragment fragment1 = new NotaFragment();

        fragmentTransaction.add(R.id.id_layout_nota, fragment1);
        fragmentTransaction.commit();
    }

    public void binding() {

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
        builder.setMessage("Selecione um Aluno e Insira a Nota do Semestre. A ultima nota será informada na lista!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }
}
