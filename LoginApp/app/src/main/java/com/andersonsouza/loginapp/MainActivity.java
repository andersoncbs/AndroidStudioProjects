package com.andersonsouza.loginapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(MainActivity.this);

        firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
/*
        //cadastro
        firebaseAuth.createUserWithEmailAndPassword("andersoncbs@gmail.com", "and123")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("###", "Sucesso!");
                        } else {
                            Log.i("###", "Erro!");
                        }
                    }
                });
                */
    }
}
