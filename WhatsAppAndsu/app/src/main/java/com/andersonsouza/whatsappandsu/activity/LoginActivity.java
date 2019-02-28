package com.andersonsouza.whatsappandsu.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.config.ConfiguracaoFirebase;
import com.andersonsouza.whatsappandsu.helper.Base64Custom;
import com.andersonsouza.whatsappandsu.helper.Preferencias;
import com.andersonsouza.whatsappandsu.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editSenha;
    private Button botaoLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private String identificadorUsuarioLogado;

    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        botaoLogar = findViewById(R.id.buttonLogar);

        verificarUsuarioLogado();

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(editEmail.getText().toString());
                usuario.setSenha(editSenha.getText().toString());

                validarLogin();
            }
        });
    }

    private void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        if (autenticacao.getCurrentUser() != null) {
            abrirTelaPrincipal();
        }

    }

    private void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());

                        firebase = ConfiguracaoFirebase.getFirebase()
                                .child("usuarios")
                                .child(identificadorUsuarioLogado);

                        valueEventListenerUsuario =  new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Usuario usuarioRecuperado = dataSnapshot.getValue(Usuario.class);
                                Preferencias preferencias = new Preferencias(LoginActivity.this);
                                preferencias.salvarDados(identificadorUsuarioLogado, usuarioRecuperado.getNome());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };

                        firebase.addListenerForSingleValueEvent(valueEventListenerUsuario);

                        abrirTelaPrincipal();
                    } else {
                        Toast.makeText(LoginActivity.this, "Erro ao fazer login!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

}
