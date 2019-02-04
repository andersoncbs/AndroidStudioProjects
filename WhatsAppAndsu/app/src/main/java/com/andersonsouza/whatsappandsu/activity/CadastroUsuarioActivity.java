package com.andersonsouza.whatsappandsu.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.config.ConfiguracaoFirebase;
import com.andersonsouza.whatsappandsu.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private Button botaoCadastrar;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        botaoCadastrar  = findViewById(R.id.btCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();

                usuario.setNome(editNome.getText().toString());
                usuario.setEmail(editEmail.getText().toString());
                usuario.setSenha(editSenha.getText().toString());

                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        final FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
          usuario.getEmail(),
          usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroUsuarioActivity.this, "Usuário cadastrado.", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = task.getResult().getUser();
                            usuario.setId(user.getUid());
                            usuario.salvar();

                            autenticacao.signOut();
                            finish();
                        } else {
                            String mensagemErro = "";
                            try {
                                throw  task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                mensagemErro = "Digite uma senha mais forte, contendo letras e números!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                mensagemErro = "E-mail inválido!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                mensagemErro = "Usuário já existente!";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroUsuarioActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

}
