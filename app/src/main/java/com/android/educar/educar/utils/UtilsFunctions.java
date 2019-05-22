package com.android.educar.educar.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.educar.educar.network.service.APIService;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class UtilsFunctions {

    public static ProgressDialog progressDialog(Context context, String mensagem) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(mensagem);
        return progressDialog;
    }

    public static String getToken(Context context) {
        Preferences preferences = new Preferences(context);
        return preferences.getSavedString("tokenLogado");
    }

    public static String criptografaSenha(String senha)
            throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        String s = hash.toString(16);
        if (s.length() % 2 != 0)
            s = "0" + s;
        return s;
    }

    public static boolean consultarUsuarioLogado(Context context) {
        Preferences preferences = new Preferences(context);
        if (preferences.getSavedBoolean(Messages.USUARIO_LOGADO)) {
            return true;
        }
        return false;
    }

    public static APIError parseError(Response<?> response) {
        APIService apiService = new APIService("");
        Converter<ResponseBody, APIError> converter = apiService.getRetrofit().responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }

    public static boolean isConnect(Context contexto) {
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable())) {
            return true;
        } else {
            return false;
        }
    }

    public static SimpleDateFormat formatoDataPadrao() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return simpleDateFormat;
    }

    public static SimpleDateFormat apenasData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf;
    }

    private static String converterDataEmString(String date) throws Exception {
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

    public static String toTitledCase(String str) {

        String[] words = str.split("\\s");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            sb.append(words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase());
            sb.append(" ");
        }

        return sb.toString();
    }
}

