package com.andersonsouza.alcoolougasolina.lcoolougasolina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText precoAlcool;
    private EditText precoGasolina;
    private Button botaoVerificar;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        precoAlcool = findViewById(R.id.editTextPrecoAlcool);
        precoGasolina = findViewById(R.id.editTextPrecoGasolina);
        botaoVerificar = findViewById(R.id.buttonVerificar);
        textoResultado = findViewById(R.id.textViewResultado);

        botaoVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoPrecoAlcool = precoAlcool.getText().toString();
                String textoPrecoGasolina = precoGasolina.getText().toString();

                Double valorAlcool = Double.parseDouble(textoPrecoAlcool);
                Double valorGasolina = Double.parseDouble(textoPrecoGasolina);

                double resultado = valorAlcool / valorGasolina;

                if (resultado >= 0.7) {
                    textoResultado.setText("É melhor utilizar Gasolina");
                } else {
                    textoResultado.setText("É melhor utilizar Álcool");
                }
            }
        });
    }
}
