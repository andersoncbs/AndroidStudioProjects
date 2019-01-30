package com.andersonsouza.whatsappandsu.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context context;
    private SharedPreferences sharedPreferences;
    private String NOME_ARQUIVO = "whatsappandsu.preferencias";
    private int FILE_MODE = Context.MODE_PRIVATE;
    private SharedPreferences.Editor editor;

    private String CHAVE_NOME = "nome";
    private String CHAVE_FONE = "fone";
    private String CHAVE_TOKEN = "token";

    public Preferencias(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NOME_ARQUIVO, FILE_MODE);
        editor = sharedPreferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome, String telefone, String token) {
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_FONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public HashMap<String, String> getDadosUsuario() {
        HashMap<String, String> dadosUsuario = new HashMap<>();
        dadosUsuario.put(CHAVE_NOME, sharedPreferences.getString(CHAVE_NOME, ""));
        dadosUsuario.put(CHAVE_FONE, sharedPreferences.getString(CHAVE_FONE, ""));
        dadosUsuario.put(CHAVE_TOKEN, sharedPreferences.getString(CHAVE_TOKEN, ""));

        return dadosUsuario;
    }

}
