package com.andersonsouza.caraoucoroa;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ResultadoActivity extends AppCompatActivity {

    private ImageView imagem;
    private ImageView botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        imagem = findViewById(R.id.imagemMoeda);
        botaoVoltar = findViewById(R.id.botaoVoltar);

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            String opcaoSorteada = extra.getString("opcao");
            if ("cara".equals(opcaoSorteada)) {
                imagem.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moeda_cara));
            } else {
                imagem.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moeda_coroa));
            }

        }

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultadoActivity.this, MainActivity.class));
            }
        });
    }
}

