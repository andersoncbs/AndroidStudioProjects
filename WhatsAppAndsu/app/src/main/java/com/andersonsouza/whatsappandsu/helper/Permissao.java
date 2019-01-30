package com.andersonsouza.whatsappandsu.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    private static final int VERSAO_6_MARSHMALLOW = 23;

    public static boolean validarPermissoes(Activity activity, String[] permissoes, int requestCode) {

        if (Build.VERSION.SDK_INT >= VERSAO_6_MARSHMALLOW) {
            List<String> listaPermissoes = new ArrayList<>();
            for (String permissao: permissoes) {
                Boolean valida = ContextCompat.checkSelfPermission(activity, permissao) ==
                        PackageManager.PERMISSION_DENIED;

                if (!valida) {
                    listaPermissoes.add(permissao);
                }
            }

            if (listaPermissoes.isEmpty()) {
                return true;
            }

            Log.i("###", permissoes[0]);
            Log.i("###", permissoes[1]);
            Log.i("###", listaPermissoes.toString());


            //solicita permissões
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);
            Log.i("###", "requisitado permissões");
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
            Log.i("###", "fim requisitado permissões");
        }

        return true;
    }

}
