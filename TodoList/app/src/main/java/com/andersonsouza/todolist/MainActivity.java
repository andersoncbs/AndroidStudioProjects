package com.andersonsouza.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

    private EditText textoTarefa;
    private Button botaoAdicionar;
    private ListView listView;
    private SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoTarefa = findViewById(R.id.editTextTarefa);
        botaoAdicionar = findViewById(R.id.buttonAdicionar);
        listView = findViewById(R.id.listViewId);

        montarBD();

        carregarDadosBD();

        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoDigitado = textoTarefa.getText().toString();

                try {
                    bancoDados.execSQL("INSERT INTO TAREFA (DESC_TAREFA) VALUES ('" + textoDigitado + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void carregarDadosBD() {
        try {
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM TAREFA", null);
            int indexId = cursor.getColumnIndex("ID");
            int indexDesc = cursor.getColumnIndex("DESC_TAREFA");

            cursor.moveToFirst();
            Log.i("###", " TESTE 1");
            while (cursor != null) {
                Log.i("###", " TESTE 2");
                 Log.i("###", " Tarefa: " + cursor.getString(indexDesc));
                cursor.moveToNext();
            }
            Log.i("###", " TESTE 3");
        } catch (Exception e) {
            Log.i("###", " ERRO" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void montarBD() {
        bancoDados = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);

        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS TAREFA (ID INTEGER PRIMARY KEY AUTOINCREMENT, DESC_TAREFA VARCHAR)");
    }

}
