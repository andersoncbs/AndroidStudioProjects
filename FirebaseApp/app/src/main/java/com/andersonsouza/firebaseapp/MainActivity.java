package com.andersonsouza.firebaseapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    //criando nó de usuários
    private DatabaseReference usuarioReference = databaseReference.child("usuarios");

    private DatabaseReference produtosReference = databaseReference.child("produtos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //salvando dados
        usuarioReference.child("001").child("nome").setValue("Anderson Souza");

        Usuario usuario1 = new Usuario();
        usuario1.setNome("Jaqueline");
        usuario1.setSobrenome("Souza");
        usuario1.setSexo("F");
        usuario1.setIdade(27);

        usuarioReference.child("003").setValue(usuario1);

        Produto produto = new Produto();
        produto.setDescricao("iPhone");
        produto.setFabricante("Apple");
        produto.setCor("Preto");

        produtosReference.child("001").setValue(produto);

        //recuperando dados, dispara listener qd dado é alterado
        produtosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("### firebase", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
