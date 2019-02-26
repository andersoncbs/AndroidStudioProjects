package com.andersonsouza.whatsappandsu.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.adapter.MensagemAdapter;
import com.andersonsouza.whatsappandsu.config.ConfiguracaoFirebase;
import com.andersonsouza.whatsappandsu.helper.Base64Custom;
import com.andersonsouza.whatsappandsu.helper.Preferencias;
import com.andersonsouza.whatsappandsu.model.Mensagem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toobar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference firebase;

    private ListView listView;
    private List<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagens;

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
        listView = findViewById(R.id.lv_conversas);

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

        //montar listview e adapter
        mensagens = new ArrayList<>();

        /*
        adapter = new ArrayAdapter(ConversaActivity.this,
                android.R.layout.simple_list_item_1,
                mensagens);
        */

        //adapatador customizado
        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);

        listView.setAdapter(adapter);

        firebase = ConfiguracaoFirebase.getFirebase()
                .child("mensagens")
                .child(idUsuarioRementente)
                .child(idUsuarioDest);

        valueEventListenerMensagens = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mensagens.clear();

                for (DataSnapshot dados: dataSnapshot.getChildren()) {
                    Mensagem  mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener(valueEventListenerMensagens);

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

                    //salvando mensagem para o remetente
                    salvarMensagem(idUsuarioRementente, idUsuarioDest, mensagem);

                    //salvando mensagem para o destinatário
                    salvarMensagem(idUsuarioDest, idUsuarioRementente, mensagem);

                    editMensagem.setText("");
                }
            }
        });
    }

    /**
     Estrutura:

     mensagens
     + rementente@mail.com
        + dest1@mail.com
            + mensagem 01
            + mensagem 02
        + dest2@mail.com
            + mensagem 01

      @param mensagem
     */
    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem) {
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("mensagens");

            firebase.child(idRemetente)
                    .child(idDestinatario)
                    .push()
                    .setValue(mensagem);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMensagens);
    }

}
