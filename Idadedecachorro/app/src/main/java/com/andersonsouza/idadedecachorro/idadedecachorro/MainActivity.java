package com.andersonsouza.idadedecachorro.idadedecachorro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button botao;
    private TextView textoResultado;
    private EditText caixaTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = findViewById(R.id.buttonDescobrirIdade);
        caixaTexto = findViewById(R.id.editTextIdadeCachorro);
        textoResultado = findViewById(R.id.textoResultadoIdade);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoDigitado = caixaTexto.getText().toString();

                if (!textoDigitado.isEmpty()) {
                    int idadeInformada = Integer.parseInt(textoDigitado);
                    textoResultado.setText("A idade do cachorro em anos humanos é: " + idadeInformada * 7);
                }


            }
        });

    }
}
