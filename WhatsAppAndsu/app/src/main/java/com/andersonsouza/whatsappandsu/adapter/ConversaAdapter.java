package com.andersonsouza.whatsappandsu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andersonsouza.whatsappandsu.R;
import com.andersonsouza.whatsappandsu.model.Conversa;

import java.util.List;

public class ConversaAdapter extends ArrayAdapter<Conversa> {

    private List<Conversa> conversas;
    private Context context;

    public ConversaAdapter(Context context, List<Conversa> objects) {
        super(context, 0, objects);
        this.conversas = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (conversas != null && !conversas.isEmpty()) {
            //inicializar obj p/ montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //montar view a partir do xml
            view = inflater.inflate(R.layout.lista_conversas, parent, false);

            //recupera elemento p/ exibição
            TextView nome = view.findViewById(R.id.tv_titulo);
            TextView ultimaMensagem = view.findViewById(R.id.tv_subtitulo);

            Conversa conversa = conversas.get(position);
            conversa.setNome(conversa.getNome());
            nome.setText(conversa.getNome());
            ultimaMensagem.setText(conversa.getMensagem());
        }

        return view;
    }
}
