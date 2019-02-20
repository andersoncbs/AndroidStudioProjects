package com.andersonsouza.whatsappandsu.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;

import com.andersonsouza.whatsappandsu.R;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toobar;

    private String nomeUsuarioDest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toobar = findViewById(R.id.tb_conversa);

        Bundle bundleExtra = getIntent().getExtras();

        if (bundleExtra != null) {
            nomeUsuarioDest = bundleExtra.getString("nome");
        }

        toobar.setTitle(nomeUsuarioDest);
        toobar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toobar);

    }

}
