package com.andersonsouza.whatsappandsu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.adapter.TabAdapter;
import com.andersonsouza.whatsappandsu.config.ConfiguracaoFirebase;
import com.andersonsouza.whatsappandsu.helper.Base64Custom;
import com.andersonsouza.whatsappandsu.helper.Preferencias;
import com.andersonsouza.whatsappandsu.helper.SlidingTabLayout;
import com.andersonsouza.whatsappandsu.model.Contato;
import com.andersonsouza.whatsappandsu.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button botaoSair;
    private FirebaseAuth autenticacao;
    private Toolbar toobar;
    private String identificadorContato;
    private DatabaseReference firebase;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toobar = findViewById(R.id.toolbar);
        toobar.setTitle("Whats");
        setSupportActionBar(toobar);

        slidingTabLayout = findViewById(R.id.stl_tabs);
        viewPager = findViewById(R.id.vp_pagina);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSair:
                deslogarUsuario();
                return true;
            case R.id.itemConfiguracoes:
                return true;
            case R.id.itemAdicionarUsuario:
                abrirCadastroUsuario();
                return true;
            default: return  super.onOptionsItemSelected(item);
        }

    }

    private void abrirCadastroUsuario() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Novo contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailContato = editText.getText().toString();
                if (emailContato.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha o e-mail", Toast.LENGTH_SHORT).show();
                } else {
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    //recuperar instância Firebase
                    firebase = ConfiguracaoFirebase.getFirebase();
                    firebase = firebase.child("usuarios").child(identificadorContato);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            /*
                                + contatos
                                    + andersoncbs@gmail.com (user logado)
                                        + email@mail.com (contato a ser adicionado)
                                            - identificador
                                            - nome
                                            - email
                                        + email2@mail.com (outro contato a ser adicionado)

                             */
                            if (dataSnapshot.getValue() != null) {
                                //dados do usuário a ser adicionado
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);


                                //usuário logado
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUserLogado = preferencias.getIdentificador();

                                firebase = ConfiguracaoFirebase.getFirebase();
                                firebase = firebase
                                        .child("contatos")
                                        .child(identificadorUserLogado)
                                        .child(identificadorContato);

                                Contato contato = new Contato();
                                contato.setIdentificador(identificadorContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());

                                firebase.setValue(contato);
                            } else {
                                Toast.makeText(MainActivity.this, "Usuário não possui cadastro.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void deslogarUsuario() {
        autenticacao.signOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
