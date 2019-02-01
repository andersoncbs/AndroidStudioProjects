package com.andersonsouza.whatsappandsu.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenciaBD;

    public static DatabaseReference getFirebase() {
        if (referenciaBD == null) {
            referenciaBD = FirebaseDatabase.getInstance().getReference();
        }

        return referenciaBD;
    }

}
