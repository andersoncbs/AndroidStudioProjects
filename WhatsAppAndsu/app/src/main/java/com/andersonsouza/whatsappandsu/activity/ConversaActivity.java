package com.andersonsouza.whatsappandsu.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.config.ConfiguracaoFirebase;
import com.andersonsouza.whatsappandsu.helper.Base64Custom;
import com.andersonsouza.whatsappandsu.helper.Preferencias;
import com.andersonsouza.whatsappandsu.model.Mensagem;
import com.google.firebase.database.DatabaseReference;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toobar;
    private EditText editMensagem;
    private ImageButton btMensagem;

    private DatabaseReference firebase;

    //dados destinatário
    private String nomeUsuarioDest;
    private String idUsuarioDest;

    //dados remetente
    private String idUsuarioRementente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toobar = findViewById(R.id.tb_conversa);
        editMensagem = findViewById(R.id.edit_mensagem);
        btMensagem = findViewById(R.id.ibt_enviar);

        //usuário logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRementente = preferencias.getIdentificador();

        Bundle bundleExtra = getIntent().getExtras();

        if (bundleExtra != null) {
            nomeUsuarioDest = bundleExtra.getString("nome");
            String emailDest = bundleExtra.getString("email");
            idUsuarioDest = Base64Custom.codificarBase64(emailDest);
        }

        toobar.setTitle(nomeUsuarioDest);
        toobar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toobar);

        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoMensagem = editMensagem.getText().toString();

                if (textoMensagem.isEmpty()) {
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem!", Toast.LENGTH_SHORT).show();
                } else {
                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRementente);
                    mensagem.setIdDestinatario(idUsuarioDest);
                    mensagem.setMensagem(textoMensagem);

                    salvarMensagem(mensagem);
                    editMensagem.setText("");
                }
            }
        });
    }

    /**
      Estrutura

     mensagens
     + rementente@mail.com
        + dest1@mail.com
            + mensagem 01
            + mensagem 02
        + dest2@mail.com
            + mensagem 01

      @param mensagem
     */
    private boolean salvarMensagem(Mensagem mensagem) {
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("mensagens");

            firebase.child(mensagem.getIdUsuario())
                    .child(mensagem.getIdDestinatario())
                    .push()
                    .setValue(mensagem);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
