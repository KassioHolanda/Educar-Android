package com.android.educar.educar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.educar.educar.model.Ocorrencia;
import com.android.educar.educar.R;
import com.android.educar.educar.utils.UtilsFunctions;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.Realm;

public class OcorrenciaListaAdapter extends BaseAdapter {
    private Context context;
    private List<Ocorrencia> ocorrencias;
    private Realm realm;
    private TextView data;
    private TextView notificacao;


    public OcorrenciaListaAdapter(Context context, List<Ocorrencia> ocorrencias) {
        this.context = context;
        this.ocorrencias = ocorrencias;
        configRealm();
    }

    public void configRealm() {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public int getCount() {
        return ocorrencias.size();
    }

    @Override
    public Object getItem(int i) {
        return ocorrencias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ocorrencias.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout linearLayout;
        View row = null;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            row = layoutInflater.inflate(R.layout.dialog_ocorrencia_detalhe, viewGroup, false);
        } else {
            row = view;
        }

        notificacao = row.findViewById(R.id.notificacao_detalhe_id);
        notificacao.setText(ocorrencias.get(i).getDescricao());
        data = row.findViewById(R.id.data_ocorrencia_detalhe_id);
        data.setText("" + ocorrencias.get(i).getDatahora());

        return row;
    }
}
