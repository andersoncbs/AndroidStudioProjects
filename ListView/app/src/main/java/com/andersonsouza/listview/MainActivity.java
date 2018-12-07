package com.andersonsouza.listview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView lista;
    private String[] itens = {"Ivaiporã", "Cândido de Abreu", "Jussara", "Irecê", "Umuarama",
            "Rondon do Pará", "São Bento", "Curitiba", "Ivaiporã", "Cândido de Abreu", "Jussara",
            "Irecê", "Umuarama", "Rondon do Pará", "São Bento", "Curitiba"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.listViewTela);

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens);

        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valor = lista.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), valor, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
