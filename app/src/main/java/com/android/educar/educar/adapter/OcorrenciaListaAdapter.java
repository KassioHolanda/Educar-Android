package com.android.educar.educar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.educar.educar.model.modelalterado.Ocorrencia;
import com.android.educar.educar.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class OcorrenciaListaAdapter extends BaseAdapter {
    private Context context;
    private List<Ocorrencia> ocorrencias;
    private Realm realm;
    private TextView data;
    private TextView notificacao;
    private TextView titulo;


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
        return 0;
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

        titulo = row.findViewById(R.id.titulo_id_notificacao);
        titulo.setText(ocorrencias.get(i).getDescricao());
        notificacao = row.findViewById(R.id.notificacao_detalhe_id);
        notificacao.setText(ocorrencias.get(i).getObservacao());
        data = row.findViewById(R.id.data_ocorrencia_detalhe_id);

        try {
            data.setText("" + getparsedDate(ocorrencias.get(i).getDatahora()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    private String getparsedDate(String date) throws Exception {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
        String s1 = date;
        String s2 = null;
        Date d;
        try {
            d = sdf.parse(s1);
            s2 = (new SimpleDateFormat("dd/MM/yyyy")).format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s2;

    }
}
