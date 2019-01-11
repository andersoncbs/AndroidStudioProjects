package com.andersonsouza.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private EditText textoTarefa;
    private Button botaoAdicionar;
    private ListView listView;
    private SQLiteDatabase bancoDados;
    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoTarefa = findViewById(R.id.editTextTarefa);
        botaoAdicionar = findViewById(R.id.buttonAdicionar);
        listView = findViewById(R.id.listViewTarefas);

        montarBD();

        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoDigitado = textoTarefa.getText().toString();

                try {
                    salvarTarefa(textoDigitado);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removerTarefa(ids.get(position));
                return true;
            }
        });

        carregarDadosBD();
    }

    private void carregarDadosBD() {
        itens = new ArrayList<>();
        ids = new ArrayList<>();

        itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_2,
                android.R.id.text2,
                itens);

        listView.setAdapter(itensAdaptador);

        try {
            Cursor cursor = bancoDados.rawQuery("SELECT CODIGO, DESC_TAREFA FROM TAREFA ORDER BY CODIGO DESC", null);
            int indexDesc = cursor.getColumnIndex("DESC_TAREFA");
            int indexId = cursor.getColumnIndex("CODIGO");

            while (cursor.moveToNext()) {
                itens.add(cursor.getString(indexDesc));
                ids.add(Integer.parseInt(cursor.getString(indexId)));
            }
        } catch (Exception e) {
            Log.e("###", e.getMessage());
            e.printStackTrace();
        }
    }

    private void montarBD() {
        bancoDados = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS TAREFA (CODIGO INTEGER PRIMARY KEY AUTOINCREMENT, DESC_TAREFA VARCHAR)");
    }

    private void salvarTarefa(String textoDigitado) {
        try {
            if (textoDigitado.equals("")) {
                Toast.makeText(MainActivity.this, "Digite uma tarefa", Toast.LENGTH_SHORT).show();
            } else {
                bancoDados.execSQL("INSERT INTO TAREFA (DESC_TAREFA) VALUES ('" + textoDigitado + "')");
                carregarDadosBD();
                textoTarefa.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void removerTarefa(Integer id) {
        try {
            bancoDados.execSQL("DELETE FROM TAREFA WHERE CODIGO =" + id);
            carregarDadosBD();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
