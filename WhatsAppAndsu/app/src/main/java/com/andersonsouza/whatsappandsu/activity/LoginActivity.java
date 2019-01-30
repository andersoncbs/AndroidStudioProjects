package com.andersonsouza.whatsappandsu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;
    private EditText ddd;
    private EditText ddi;

    private Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome = findViewById(R.id.editTextNome);
        ddd = findViewById(R.id.editTextDDD);
        ddi = findViewById(R.id.editTextDDI);
        telefone = findViewById(R.id.editTelefone);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        SimpleMaskFormatter smfDDI = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter smfDDD = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        MaskTextWatcher mtwTelefone = new MaskTextWatcher(this.telefone, smfTelefone);
        MaskTextWatcher mtwDDI = new MaskTextWatcher(this.ddi, smfDDI);
        MaskTextWatcher mtwDDD = new MaskTextWatcher(this.ddd, smfDDD);

        ddi.addTextChangedListener(mtwDDI);
        ddd.addTextChangedListener(mtwDDD);
        telefone.addTextChangedListener(mtwTelefone);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = nome.getText().toString();
                String numTelefone =
                        ddi.getText().toString() +
                        ddd.getText().toString() +
                        telefone.getText().toString();
                String telefoneSemFormatacao = numTelefone
                        .replaceAll("-", "").replaceAll("/+", "");

                Random random = new Random(System.currentTimeMillis());
                Integer numSorteado = random.nextInt(8999) + 1000;
                String token = numSorteado.toString();

                Preferencias preferencias = new Preferencias(getApplicationContext());

                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                HashMap<String, String> dadosUsuario = preferencias.getDadosUsuario();
                Log.i("###", dadosUsuario.get("token"));
            }
        });
    }
}
