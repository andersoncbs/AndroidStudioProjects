package com.andersonsouza.whatsappandsu.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenciaBD;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase() {
        if (referenciaBD == null) {
            referenciaBD = FirebaseDatabase.getInstance().getReference();
        }

        return referenciaBD;
    }

    public static FirebaseAuth getFirebaseAutenticacao() {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;
    }

}
