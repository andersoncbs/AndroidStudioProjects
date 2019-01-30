package com.andersonsouza.whatsappandsu.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.helper.Permissao;
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

    private String[] permissoesNecessarias = new String[] {
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validarPermissoes(this, permissoesNecessarias, 1);
//        alertaValidacaoPermissao();

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

    public boolean enviarSMS(String telefone, String mensagem) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int resultado: grantResults) {
            if (resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    public void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar o app é preciso aceitar as permissões");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
