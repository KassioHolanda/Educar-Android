package com.android.educar.educar.utils;

import android.app.ProgressDialog;
import android.content.Context;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}