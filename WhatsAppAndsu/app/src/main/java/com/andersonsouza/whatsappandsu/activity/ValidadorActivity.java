package com.andersonsouza.whatsappandsu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Map;

public class ValidadorActivity extends AppCompatActivity {

    private EditText editCodigo;
    private Button botaoValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        editCodigo = findViewById(R.id.editCodValidacao);
        botaoValidar = findViewById(R.id.buttonValidar);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editCodigo, smf);

        editCodigo.addTextChangedListener(mtw);

        botaoValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                Map<String, String> mapa = preferencias.getDadosUsuario();

                String tokenGerado = mapa.get("token");
                String tokenDigitado = editCodigo.getText().toString();

                if (tokenDigitado.equals(tokenGerado)) {
                    Toast.makeText(ValidadorActivity.this, "Token validado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ValidadorActivity.this, "Token não validado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
