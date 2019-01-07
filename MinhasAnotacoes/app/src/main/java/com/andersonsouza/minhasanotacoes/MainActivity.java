package com.andersonsouza.minhasanotacoes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private EditText texto;
    private ImageView botaoSalvar;

    private static final String NOME_ARQUIVO = "anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = findViewById(R.id.editTextAnotacao);
        botaoSalvar = findViewById(R.id.botaoSalvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(MainActivity.this, "Anotação gravada.", Toast.LENGTH_SHORT).show();
            }
        });

        String conteudo = lerArquivo();
        if (conteudo != null) {
            texto.setText(conteudo);
        }
    }

    private void gravarNoArquivo(String texto) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            writer.write(texto);
            writer.close();
        } catch (IOException e) {
            Log.v("MainActovity", e.toString());
        }
    }

    private String lerArquivo() {
        String resultado = "";

        try {
            InputStream arquivo = openFileInput(NOME_ARQUIVO);

            if (arquivo != null) {
                InputStreamReader reader = new InputStreamReader(arquivo);

                BufferedReader bufferedReader = new BufferedReader(reader);

                String linha = "";
                while ((linha = bufferedReader.readLine()) != null) {
                    resultado += linha;
                }

                arquivo.close();
            }


        } catch (IOException e) {
            Log.v("MainActovity", e.toString());
        }

        return resultado;
    }

}
