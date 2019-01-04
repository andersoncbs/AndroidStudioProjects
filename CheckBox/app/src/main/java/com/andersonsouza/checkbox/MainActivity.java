package com.andersonsouza.checkbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkBoxCachorro;
    private CheckBox checkBoxGato;
    private CheckBox checkBoxPapagaio;

    private Button botaoMostrar;
    private TextView textoExibicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxCachorro = findViewById(R.id.checkBoxCachorro);
        checkBoxGato = findViewById(R.id.checkBoxGato);;
        checkBoxPapagaio = findViewById(R.id.checkBoxPapagaio);;
        botaoMostrar = findViewById(R.id.buttonMostrar);;
        textoExibicao = findViewById(R.id.textViewMostrar);;

        botaoMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultado = "";
                resultado += checkBoxCachorro.getText() + ": " +  String.valueOf(checkBoxCachorro.isChecked()) + "\n";
                resultado += checkBoxGato.getText() + ": " +  String.valueOf(checkBoxGato.isChecked()) + "\n";
                resultado += checkBoxPapagaio.getText() + ": " +  String.valueOf(checkBoxPapagaio.isChecked()) + "\n";

                textoExibicao.setText(resultado);
            }
        });

    }
}


