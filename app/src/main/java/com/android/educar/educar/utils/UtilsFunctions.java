package com.android.educar.educar.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.educar.educar.service.APIService;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

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
}