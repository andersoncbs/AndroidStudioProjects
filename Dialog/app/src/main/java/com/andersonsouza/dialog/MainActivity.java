package com.andersonsouza.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button botao;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = findViewById(R.id.buttonAbrirDialog);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Título da dialog");
                dialog.setMessage("Mensagem da dialog");

                dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Pressionado não",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Pressionado sim",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setCancelable(false);
                dialog.setIcon(android.R.drawable.ic_input_add);

                dialog.create();
                dialog.show();
            }
        });
    }
}
