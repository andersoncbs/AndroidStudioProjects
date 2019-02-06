package com.andersonsouza.whatsappandsu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button botaoSair;
    private FirebaseAuth autenticacao;
    private Toolbar toobar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoSair = findViewById(R.id.buttonSair);

        botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        toobar = findViewById(R.id.toolbar);
        toobar.setTitle("Whats");
        setSupportActionBar(toobar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }
}
