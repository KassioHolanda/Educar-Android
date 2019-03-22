package com.android.educar.educar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.educar.educar.R;
import com.android.educar.educar.model.Serie;
import com.android.educar.educar.model.SerieTurma;
import com.android.educar.educar.model.Turma;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TurmaAdapter extends BaseAdapter {

    private Turma turma;
    private List<Turma> turmaList;
    private TextView turmaTextView;
    private TextView serie;
    private Realm realm;
    private Context context;

    public TurmaAdapter(Context context, List<Turma> turmas) {
        this.turmaList = turmas;
        this.context = context;
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public int getCount() {
        return turmaList.size();
    }

    @Override
    public Object getItem(int i) {
        return turmaList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return turmaList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout linearLayout;
        View row = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            row = inflater.inflate(R.layout.turma_item, viewGroup, false);
        } else {
            row = view;
        }

        turmaTextView = row.findViewById(R.id.turmaitem_id);
        serie = row.findViewById(R.id.serieitem_id);

        turmaTextView.setText(turmaList.get(i).getDescricao());
        serie.setText(turmaList.get(i).getSerie() + "");

        Long serieId = turmaList.get(i).getSerie();

        if (serieId == null) {
            SerieTurma serieTurma = realm.where(SerieTurma.class).equalTo("turma", turmaList.get(i).getId()).findFirst();
            serie.setText(realm.where(Serie.class).equalTo("id", serieTurma.getSerie()).findFirst().getDescricao());
        } else {
            Serie serieSelecionada = realm.where(Serie.class).equalTo("id", turmaList.get(i).getSerie()).findFirst();
            serie.setText(serieSelecionada.getDescricao());
        }

        return row;
    }
}
