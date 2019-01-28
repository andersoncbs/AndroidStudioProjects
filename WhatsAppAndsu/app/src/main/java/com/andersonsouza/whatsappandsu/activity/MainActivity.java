package com.andersonsouza.whatsappandsu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andersonsouza.whatsappandsu.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

//    private DatabaseReference referenciaBD = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        referenciaBD.child("pontos").setValue(100);
    }
}
