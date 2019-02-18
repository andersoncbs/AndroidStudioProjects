package com.andersonsouza.whatsappandsu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.model.Contato;

import java.util.List;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private List<Contato> contatos;
    private Context context;

    public ContatoAdapter(Context context, List<Contato> objects) {
        super(context, 0, objects);
        this.contatos = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = null;

        if (contatos != null && !contatos.isEmpty()) {
            //inicializar obj p/ montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //montar view a partir do xml
            view = inflater.inflate(R.layout.lista_contato, parent, false);

            //recupera elemento p/ exibição
            TextView nomeContato = view.findViewById(R.id.tv_nome);
            TextView emailContato = view.findViewById(R.id.tv_email);

            Contato contato = contatos.get(position);
            nomeContato.setText(contato.getNome());
            emailContato.setText(contato.getEmail());
        }

        return view;
    }
}
