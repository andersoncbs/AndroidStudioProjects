package com.andersonsouza.sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText textoNome;
    private Button botaoSalvar;
    private TextView textoExibicao;

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPrefs";
    private static final String ATRIB_NOME = "nome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNome = findViewById(R.id.editTextNome);
        botaoSalvar = findViewById(R.id.buttonSalvar);
        textoExibicao = findViewById(R.id.textViewExibicao);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (textoNome.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Por gentileza, preencha o nome", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString(ATRIB_NOME, textoNome.getText().toString());
                    editor.commit();
                    textoExibicao.setText(textoNome.getText().toString());
                }
            }
        });

        recuperarDadosSalvos();
    }

    private void recuperarDadosSalvos() {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        if (sharedPreferences.contains(ATRIB_NOME)) {
            String nomeUsuario = sharedPreferences.getString(ATRIB_NOME, "Nome não informado.");
            textoExibicao.setText(nomeUsuario);
        } else {
            textoExibicao.setText("Nome não informado.");
        }
    }
}
